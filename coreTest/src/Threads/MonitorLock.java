package Threads;

public class MonitorLock {
	public void execute() throws InterruptedException{
		final Object ref = new Object();
		new Thread(new Runnable(){
			public void run(){
				System.out.println("A");
				synchronized(ref){
					System.out.println("B");
					long counter = 10_000_000_000L;
					while (counter-- > 0);
					System.out.println("C");
				}
			}
		}).start();
		
		Thread.sleep(1000);
		
		System.out.println(0);
		synchronized(ref){
			System.out.println(1);
		}
	}
}
