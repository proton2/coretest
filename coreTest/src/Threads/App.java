package Threads;

public class App {
	public static void main(String[] args) throws InterruptedException {
		MonitorLock t = new MonitorLock();
		t.execute();
	}
}
