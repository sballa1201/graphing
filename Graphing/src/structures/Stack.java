package structures;


import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;

public class Stack<T> {

	private int maxHeight;
	private int height = 0;
	private int pointer = -1;
	private T[] stack;
	
	public Stack(int maxHeight) {
		this.maxHeight = maxHeight;
		this.stack = (T[]) new Object[this.maxHeight];		
	}
	
	public void push(T push) throws StackOverflowException {
		if(height == maxHeight) {
			throw new StackOverflowException("Stack has reached its max height");
		} else {
			this.pointer++;
			this.height++;
			this.stack[this.pointer] = push;
		}
	}
	
	public T pop() throws StackUnderflowException {
		if(this.pointer < 0) {
			throw new StackUnderflowException("Stack has no items for you to pop");
		} else {
			T pop = this.stack[this.pointer];
			this.pointer--;
			this.height--;
			return pop;
		}
	}
	
	@Override
	public String toString() {
		String out = "";
		for(T i : stack) {
			out = out + i.toString() + ", ";
		}
		return out;
	}

}
