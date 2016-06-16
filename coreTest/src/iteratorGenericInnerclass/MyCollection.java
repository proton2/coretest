package iteratorGenericInnerclass;

import java.util.Iterator;

public class MyCollection <T extends Number> {
	T [] arr;

	public MyCollection() {}
	
	public MyCollection(T[] arr) {
		super();
		this.arr = arr;
	}

	private class IteratorForward implements Iterator<T>{
		int pos = 0;
		
		@Override
		public boolean hasNext() {
			return pos < arr.length;
		}
	
		@Override
		public T next() {
			return arr[pos++];
		}
	}
	
	public Iterator<T> getForward (){
		return new IteratorForward();
	}
	
	public Iterator<T> getBackward (){
		class IteratorBackward implements Iterator<T>{
			int pos = arr.length - 1;
			
			@Override
			public boolean hasNext() {
				return pos > -1;
			}
		
			@Override
			public T next() {
				return arr[pos--];
			}
		}
		return new IteratorBackward();
	}
	
	public Iterator<T> getTwoBackward (){
		return new Iterator<T>(){
			int pos = arr.length - 1;
			
			@Override
			public boolean hasNext() {
				return pos > -1;
			}
		
			@Override
			public T next() {
				T t = arr[pos];
				pos-=2;
				return t;
			}
		};
	}
	
	public T[] getArr() {
		return arr;
	}

	public void setArr(T[] arr) {
		this.arr = arr;
	}
}
