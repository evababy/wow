public class MyNative {
	static {
		System.loadLibrary("MyNative");
	}

	public native static void HelloWord();

	public native static String cToJava();
}
