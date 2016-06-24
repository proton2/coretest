package Threads;

public class TestDeadLock {
	
	public TestDeadLock(){}
	
	private Object res1 = new Object();
	private Object res2 = new Object();
	
	private void method1(){
		System.out.println("method1");
		synchronized(res1){
			System.out.println("res1 locked");
			synchronized(res2){
				System.out.println("res2 locked");
			}
			System.out.println("res2 released");
		}
		System.out.println("res1 released");
	}
	
	private void method2(){
		System.out.println("method2");
		synchronized(res2){
			System.out.println("res2 locked");
			synchronized(res1){
				System.out.println("res1 locked");
			}
			System.out.println("res1 released");
		}
		System.out.println("res2 released");
	}
	
	public void execute(){
		Thread t1 = new Thread(new Runnable(){
					public void run(){
						method1();
					}
				}, "t1"
			);
		
		Thread t2 = new Thread(new Runnable(){
					public void run(){
						method2();
					}
				}, "t2"
			);
		
		t1.start();
		t2.start();
	}
}