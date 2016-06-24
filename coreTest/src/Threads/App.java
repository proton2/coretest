package Threads;

public class App {
	public static void main(String[] args) {
		TestDeadLock t = new TestDeadLock();
		t.execute();
	}
}
