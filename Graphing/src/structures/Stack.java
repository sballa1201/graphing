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
			String out = "";
			for(T i : stack) {
				out = out + i.toString() + ", ";
			}
			return out.substring(0, out.length() - 2);
		}
	}
	
	
	public static void main(String[] args) throws StackOverflowException, StackUnderflowException {
		Stack<Integer> stack = new Stack<Integer>(3);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		System.out.println(stack);
		stack.reverse();
		System.out.println(stack);
		System.out.println(new Stack(3));
		
	}

}
