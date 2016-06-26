package Threads;

class A {
	synchronized void foo(B b){
		
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e){
			System.out.println("поток прерван");
		}
		
		b.last();
	}
	
	synchronized void last(){
		System.out.println("внутри A.last");
	}
}

class B {
	synchronized void bar(A a){
		
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e){
			System.out.println("поток прерван");
		}
		
		a.last();
	}
	
	synchronized void last(){
		System.out.println("внутри B.last");
	}
}

public class DeadLock01 {
	public void execute()
	{
		A a = new A();
		B b = new B();
		
		System.out.println("запуск");
		
		Thread t1 = new Thread(new Runnable(){
				public void run(){
					a.foo(b);
				}
			}, "t1"
		);

		Thread t2 = new Thread(new Runnable(){
				public void run(){
					b.bar(a);
				}
			}, "t2"
		);

		t1.start();
		t2.start();
		System.out.println("завершено");
	}
}