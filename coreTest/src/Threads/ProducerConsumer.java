package Threads;

class Querry {
	private int n;
	boolean valueSet = false;
	
	synchronized public int get() throws InterruptedException{
		while(!valueSet){
			wait();
		}
		System.out.println(n + " получено");
		valueSet = false;
		notify();
		return n;
	}

	synchronized public void put (int b) throws InterruptedException{
		while(valueSet){
			wait();
		}
		valueSet = true;
		notify();
		this.n = b;
		
		System.out.println(b + " отправлено");
	}
	
}

class Producer implements Runnable{
	private Querry q;
	
	public Producer (Querry q1){
		this.q = q1;
		new Thread(this, "Поставщик").start();
	}
	
	@Override
	public void run(){
		int i=0;	
		while (true){
			try {
				q.put(i++);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable{
	private Querry q;
	
	public Consumer(Querry q1){
		this.q = q1;
		new Thread(this, "Потребитель").start();
	}
	
	@Override
	public void run(){
		while(true){
			try {
				q.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class ProducerConsumer {
	public void execute(){
		Querry q = new Querry();
		new Producer(q);
		new Consumer(q);
		System.out.println("Для остановки потока нажмите Ctrl-C");
	}
}