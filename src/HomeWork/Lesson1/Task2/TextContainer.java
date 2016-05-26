package HomeWork.Lesson1.Task2;


import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SaveTo(saveLocation = "c:\\test\\ololo.txt")
class TextContainer {
    private String text = "ololoshe4ki";

    @Saver
    public boolean saveText(String text, String saveLocation) {
        try {
            File file = new File(saveLocation);
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(text);
            printWriter.flush();
            printWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Class<?> textContainerClass = TextContainer.class;
        SaveTo saveTo = textContainerClass.getAnnotation(SaveTo.class);
        try {
            Field field = textContainerClass.getDeclaredField("text");
            Method[] methods = textContainerClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Saver.class)) {
                    boolean result = (boolean) method.invoke(textContainerClass.newInstance(),
                            (String) field.get(textContainerClass.newInstance()), saveTo.saveLocation());
                    System.out.println("Is saved = " + result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
