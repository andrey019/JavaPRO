package HomeWork.Lesson1.Tester;

public class SomeClass {
	public static long job(long...ls) {
		long res = 0;
		for (long l : ls)
			res += l;
		
		return res;
	}
	
	@Test
	public static boolean selfTest() {
		boolean res = job(1, 5, 3, 4) == 10;
		System.out.println("SomeClass: " + res);
		return res;
	}
}
