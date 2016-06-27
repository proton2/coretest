package Threads;

public class App {
	public static void main(String[] args) {
		ProducerConsumer t = new ProducerConsumer();
		t.execute();
	}
}
