package HomeWork.Lesson1.Task3;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;

class FieldSerializer {
    private String text1 = null;
    @Save
    private String text2 = null;
    private int number1 = 0;
    @Save
    private int number2 = 0;

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    @Override
    public String toString() {
        return "text1 = " + text1 + ", text2 = " + text2 + ", number1 = " + number1 + ", number2 = " + number2;
    }

    public static boolean serializeObjectFields(Object object, String saveTo) {
        try {
            Class<?> objectClass = object.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            HashMap<String, Object> fieldsToSave = new HashMap<>();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    fieldsToSave.put(field.getName(), field.get(object));
                }
            }
            return saveToFile(fieldsToSave, saveTo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean saveToFile(HashMap<String, Object> fieldsToSave, String saveTo) {
        if (fieldsToSave.isEmpty() || fieldsToSave == null) {
            return false;
        }
        try {
            File file = new File(saveTo);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(fieldsToSave);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deserializeObjectFields(Object object, String recoverFrom) {
        try {
            HashMap<String, Object> recoveredFields = recoverFromFile(recoverFrom);
            if (recoveredFields == null){
                return false;
            }
            Class<?> objectClass = object.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                if (recoveredFields.containsKey(field.getName())) {
                    field.set(object, recoveredFields.get(field.getName()));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static HashMap<String, Object> recoverFromFile(String recoverFrom) {
        try {
            File file = new File(recoverFrom);
            if (!file.exists()) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            HashMap<String, Object> recoveredFields = (HashMap<String, Object>) objectInputStream.readObject();
            objectInputStream.close();
            return recoveredFields;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String path = "c:\\test\\serialReflect.srf";
        FieldSerializer fieldSerializer = new FieldSerializer();
        fieldSerializer.setNumber1(111);
        fieldSerializer.setNumber2(222);
        fieldSerializer.setText1("xxx");
        fieldSerializer.setText2("zzz");
        System.out.println(fieldSerializer);

        serializeObjectFields(fieldSerializer, path);
        fieldSerializer = new FieldSerializer();
        deserializeObjectFields(fieldSerializer, path);
        System.out.println(fieldSerializer);
    }
}
