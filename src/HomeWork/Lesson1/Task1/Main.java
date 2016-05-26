package HomeWork.Lesson1.Task1;

import java.lang.reflect.Method;

class Main {
    static class TestingClass {
        @TestAnnotation(a = 2, b = 5)
        public static int test(int a, int b) {
            return a + b;
        }
    }

    public static void main(String[] args) {
        Class<?> classWrap = TestingClass.class;
        Method[] methods = classWrap.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(TestAnnotation.class)) {
                TestAnnotation testAnnotation = method.getDeclaredAnnotation(TestAnnotation.class);
                try {
                    int result = (int) method.invoke(null, testAnnotation.a(), testAnnotation.b());
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
