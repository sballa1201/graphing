package structures;


import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;

public class Stack<T> {

	private int maxHeight;
	private int height = 0;
	private int pointer = -1;
	private T[] stack;
	
	@SuppressWarnings("unchecked")
	public Stack(int maxHeight) {
		this.maxHeight = maxHeight;
		this.stack = (T[]) new Object[this.maxHeight];		
	}
	
	@SuppressWarnings("unchecked")
	public Stack(Stack<T> copy){
		
		this.pointer = copy.pointer;
		this.height = copy.height;
		this.maxHeight = copy.maxHeight;
		this.stack = (T[]) new Object[this.maxHeight];
		for(int i=0; i<this.height; i++) {
			this.stack[i] = copy.stack[i];
		}
	}
	
	public void push(T push) throws StackOverflowException {
		if(height == maxHeight) {
			throw new StackOverflowException();
		} else {
			this.pointer++;
			this.height++;
			this.stack[this.pointer] = push;
		}
	}
	
	public T pop() throws StackUnderflowException {
		if(this.pointer < 0) {
			throw new StackUnderflowException();
		} else {
			T pop = this.stack[this.pointer];
			this.pointer--;
			this.height--;
			return pop;
		}
	}
	
	public void reverse() throws StackOverflowException, StackUnderflowException {
		Stack<T> reverse = new Stack<T>(this.height);
		for(int i=this.height; i>0 ;i--) {
			reverse.push(this.pop());
		}
		
		this.stack = reverse.stack;
		this.pointer = reverse.pointer;
		this.height = reverse.height;
		
		
	}

	public boolean isEmpty() {
		if(this.height == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getHeight() {
		return height;
	}

	
	@Override
	public String toString() {
		if(this.isEmpty()) {
			return "Stack is Empty";
		} else {
			String out = "[" + this.stack[this.pointer] + "]";
			for(int i=this.pointer-1; i>=0; i--) {
				out = out + ", " + this.stack[i].toString() ;
			}
			return out;
		}
	}
	
	
	public static void main(String[] args) throws StackOverflowException, StackUnderflowException {
		Stack<String> s1 = new Stack<String>(4);
		s1.push("1");
		s1.push("2");
		s1.push("3");
		Stack<String> s2 = new Stack<String>(s1);
		s2.push("4");
		System.out.println(s1);
		System.out.println(s2);
		
	}

}
