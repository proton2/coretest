package Threads;

class PrintRunnable implements Runnable{
	private String msg;
    private long sleepMillis;
    
    public PrintRunnable(String msg, long sleepMillis) {
        this.msg = msg;
        this.sleepMillis = sleepMillis;
    }

	public void run(){
		for (int k = 0; k < 10; k++) {
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(msg);
        }
	}
}

class Runner implements Runnable{
	@Override
	public void run() {
		Thread A = new Thread(new PrintRunnable("A *", 100));
		Thread B = new Thread(new PrintRunnable("* B", 99));
		A.start();
		B.start();
		try {
			A.join();
			B.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread C = new Thread(new PrintRunnable("C", 100));
		C.start();
	}
}

public class ManageThreads {
	public void execute(){
		Thread threadRunner = new Thread(new Runner(), "threadRunner");
		System.out.println("Starting threadRunner");
		threadRunner.start();
		System.out.println("Finished threadRunner");
	}
}