package HomeWork.Lesson3.HTTPServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {
    private String path;
    private static ConcurrentHashMap<String, byte[]> map = new ConcurrentHashMap<String, byte[]>();
    private static CacheLifeController cacheLifeController;

    public FileManager(String path) {
        // "c:\folder\" --> "c:\folder"
        if (path.endsWith("/") || path.endsWith("\\"))
            path = path.substring(0, path.length() - 1);

        this.path = path;
        this.cacheLifeController = new CacheLifeController(map);
        this.cacheLifeController.start();
    }

    public byte[] get(String url) {
        try {
            byte[] buf = map.get(url);
            if (buf != null) // in cache
                return buf;

            // "c:\folder" + "/index.html" -> "c:/folder/index.html"
            String fullPath = path.replace('\\', '/') + url;

            RandomAccessFile f = new RandomAccessFile(fullPath, "r");
            try {
                buf = new byte[(int) f.length()];
                f.read(buf, 0, buf.length);
            } finally {
                f.close();
            }

            map.put(url, buf); // put to cache
            return buf;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String save(HashMap<String, byte[]> files) {
        if (files.isEmpty()) {
            return null;
        }
        String archiveName = "archive.zip";
        File file = new File(path + "\\" + archiveName);
        for (int i = 1; file.exists(); i++) {
            archiveName = "archive" + "[" + i + "]" + ".zip";
            file = new File(path + "\\" + archiveName);
        }

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                zipOutputStream.putNextEntry(new ZipEntry(entry.getKey()));
                zipOutputStream.write(entry.getValue());
                zipOutputStream.closeEntry();
            }
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return archiveName;
    }
}
