package HomeWork.Lesson2.Task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.RandomAccessFile;

class Main {
    public static void main(String[] args) {
        try {
            File file = new File("person.json");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            byte[] jsonByteArray = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(jsonByteArray);
            String jsonString = new String(jsonByteArray);
            Gson gson = new GsonBuilder().create();
            Person person = gson.fromJson(jsonString, Person.class);
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
