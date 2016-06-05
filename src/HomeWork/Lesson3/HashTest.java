package HomeWork.Lesson3;

import com.google.common.primitives.Bytes;

import java.io.File;
import java.io.RandomAccessFile;

class HashTest {
    public static void main(String[] args) {
        try {
            File file = new File("c:\\test\\vid.mp4");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            byte[] fileArray = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(fileArray);
            randomAccessFile.close();
            String string = new String(fileArray);
            file = new File("c:\\test\\vid1.mp4");
            file.createNewFile();
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.write(string.getBytes());
            randomAccessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Bytes.indexOf()

    }
}
