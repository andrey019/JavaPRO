package HomeWork.Lesson1.Task1;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface TestAnnotation {
    int a();
    int b();
}
