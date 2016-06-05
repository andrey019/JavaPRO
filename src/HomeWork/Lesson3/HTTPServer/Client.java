package HomeWork.Lesson3.HTTPServer;

import com.google.common.primitives.Bytes;
import java.lang.Exception;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Client implements Runnable {
    private Socket socket;
    private FileManager fm;
    
    public Client(Socket socket, String path) {
        this.socket = socket;
        fm = new FileManager(path);
    }

    private void returnStatusCode(int code, OutputStream os) throws IOException {
        String msg = null;

        switch (code) {
            case 400:
                msg = "HTTP/1.1 400 Bad Request";
                break;
            case 404:
                msg = "HTTP/1.1 404 Not Found";
                break;
            case 500:
                msg = "HTTP/1.1 500 Internal Server Error";
                break;
        }

        byte[] resp = msg.concat("\r\n\r\n").getBytes();
        os.write(resp);
    }
    
    private byte[] getBinaryHeaders(List<String> headers) {
        StringBuilder res = new StringBuilder();

        for (String s : headers) 
            res.append(s);
            
        return res.toString().getBytes();
    }
    
    private void process(String request, OutputStream os, byte[] fullPost) throws IOException {
        System.out.println(request);
        System.out.println("---------------------------------------------");

        int idx = request.indexOf("\r\n");
        request = request.substring(0, idx);

        String[] parts = request.split(" ");
        if (parts.length != 3) {
            returnStatusCode(400, os);
            return;
        }

        String method = parts[0], url = parts[1], version = parts[2];
        
        if (( ! version.equalsIgnoreCase("HTTP/1.0")) && ( ! version.equalsIgnoreCase("HTTP/1.1"))) {
            returnStatusCode(400, os);
            return;
        }
        if ( ! method.equalsIgnoreCase("GET") && ! method.equalsIgnoreCase("POST") ) {
            returnStatusCode(400, os);
            return;
        }

        if ( method.equalsIgnoreCase("GET")) {
            getProcessing(url, os);
        } else if (method.equalsIgnoreCase("POST")) {
            postProcessing(os, fullPost);
        }

    }

    private void getProcessing(String url, OutputStream os) throws IOException {
        if ("/".equals(url)) {
            url = "/index.html";
        }

        List<String> headers = new ArrayList<String>();
        headers.add("HTTP/1.1 200 OK\r\n");

        byte[] content = fm.get(url);
        if (content == null) {
            returnStatusCode(404, os);
            return;
        }

        ProcessorsList pl = new ProcessorsList();
        pl.add(new Compressor(6));
        pl.add(new Chunker(30)); // comment
        content = pl.process(content, headers);

        if (content == null) {
            returnStatusCode(500, os);
            return;
        }

        // uncomment next line
        headers.add("Content-Length: " + content.length + "\r\n");
        headers.add("Connection: close\r\n\r\n");

        os.write(getBinaryHeaders(headers));
        os.write(content);
        System.out.println(headers);
        System.out.println("---------------------------------------------");
    }

    private void postProcessing(OutputStream os, byte[] fullPost) throws IOException {
        String responseFile = processFiles(fullPost);
        String redirectLocation = socket.getLocalAddress().toString() + ":"
                + socket.getLocalPort() + "/" + responseFile;

        StringBuilder redirectResponse = new StringBuilder();
        redirectResponse.append("HTTP/1.1 303 See Other\r\n");
        redirectResponse.append("Location: http:/" + redirectLocation + "\r\n\r\n");
        os.write(redirectResponse.toString().getBytes());
        System.out.println(redirectResponse);
        System.out.println("---------------------------------------------");
    }

    private String processFiles(byte[] fullPost) {
        int boundaryStart = Bytes.indexOf(fullPost, "boundary=".getBytes()) + "boundary=".length();
        fullPost = Arrays.copyOfRange(fullPost, boundaryStart, fullPost.length);
        int boundaryEnd = Bytes.indexOf(fullPost, "\r\n".getBytes());
        byte[] boundaryBytes = Arrays.copyOfRange(fullPost, 0, boundaryEnd);
        boundaryBytes = Bytes.concat("\r\n--".getBytes(), boundaryBytes);
        byte[] rnBytes = "\r\n\r\n".getBytes();
        byte[] fileNameBytes = "filename=\"".getBytes();
        byte[] fileNameEndBytes = "\"\r\n".getBytes();

        int fileNameStart = 0;
        int fileNameEnd = 0;
        String fileName = null;
        int fileStart = 0;
        int fileEnd = 0;
        HashMap<String, byte[]> files = new HashMap<>();

        fileNameStart = Bytes.indexOf(fullPost, fileNameBytes) + fileNameBytes.length;
        fullPost = Arrays.copyOfRange(fullPost, fileNameStart, fullPost.length);
        fileNameEnd = Bytes.indexOf(fullPost,fileNameEndBytes);
        while (fileNameEnd != -1) {
            fileName = new String(Arrays.copyOfRange(fullPost, 0, fileNameEnd));
            fullPost = Arrays.copyOfRange(fullPost, fileNameEnd, fullPost.length);
            if (fileName.length() > 1) {
                fileStart = Bytes.indexOf(fullPost, rnBytes) + rnBytes.length;
                fullPost = Arrays.copyOfRange(fullPost, fileStart, fullPost.length);
                fileEnd = Bytes.indexOf(fullPost, boundaryBytes);
                files.put(fileName, Arrays.copyOfRange(fullPost, 0, fileEnd));
            }
            fileNameStart = Bytes.indexOf(fullPost, fileNameBytes) + fileNameBytes.length;
            fullPost = Arrays.copyOfRange(fullPost, fileNameStart, fullPost.length);
            fileNameEnd = Bytes.indexOf(fullPost,fileNameEndBytes);
        }

        String zipName = fm.save(files);
        if (zipName == null) {
            return "ziperror.html";
        } else {
            return zipName;
        }
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            byte[] buf, temp;
            int len;
            boolean notComplete = true;
            try {
                do {
                    len = is.available();
                    buf = new byte[len];

                    if (is.read(buf) > 0)
                        bs.write(buf);

                    temp = bs.toByteArray();

                    if (temp.length > 100) {
                        if ((temp[temp.length - 4] == (byte)13) && (temp[temp.length - 3] == (byte)10) &&
                                (temp[temp.length - 2] == (byte)13) && (temp[temp.length - 1] == (byte)10)) {

                            String request = new String(temp, 0, temp.length - 4);
                            process(request, os, null);
                            notComplete = false;
                        } else {
                            int indexStart;
                            int indexEnd;
                            String header;
                            if (temp.length > 500) {
                                header = new String(Arrays.copyOf(temp, 500));
                            } else {
                                header = new String(temp);
                            }
                            indexStart = header.indexOf("Content-Length: ") + "Content-Length: ".length();
                            indexEnd = header.indexOf("\r\n", indexStart);
                            int contentLength = Integer.valueOf(header.substring(indexStart, indexEnd));
                            header = header.substring(0, header.indexOf("\r\n\r\n"));
                            if (temp.length - header.getBytes().length - 4 == contentLength) {
                                process(header, os, temp);
                                notComplete = false;
                            }
                        }
                    }
                } while ( notComplete );
            } finally {
                socket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}