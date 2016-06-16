package iteratorGenericInnerclass;

import java.util.Iterator;

public class App {

	public static void main(String[] args) {

		Integer[] arr = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
		MyCollection <Integer> collection = new MyCollection<>(arr);
		
		Iterator <Integer> forward = collection.getForward();
		while(forward.hasNext()){
			Integer v = (Integer) forward.next();
			System.out.print(v.intValue() + " ");
		}
		
		System.out.println(" ");
		
		Iterator <Integer> backward = collection.getBackward();
		while(backward.hasNext()){
			Integer v = (Integer) backward.next();
			System.out.print(v.intValue() + " ");
		}
		
		System.out.println(" ");
		
		Iterator <Integer> backtwo = collection.getTwoBackward();
		while(backtwo.hasNext()){
			Integer v = (Integer) backtwo.next();
			System.out.print(v.intValue() + " ");
		}
	}
}
