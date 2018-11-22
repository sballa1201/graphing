package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;

public class Stack<T> {

	// attributes
	// pointer variables
	private int maxHeight;
	private int height = 0;
	private int pointer = -1;
	// stack
	private T[] stack;

	// methods
	// constructor
	@SuppressWarnings("unchecked")
	public Stack(int maxHeight) {
		this.maxHeight = maxHeight; // set this max height
		// create an array of this size of type "T"
		this.stack = (T[]) new Object[this.maxHeight];
	}

	// copy constructor
	public Stack(Stack<T> copy) {
		// assign the new stacks' attributes' values the original stacks' attributes
		this.pointer = copy.pointer;
		this.height = copy.height;
		this.maxHeight = copy.maxHeight;
		this.stack = copy.stack;
	}

	// push an item on to the stack
	public void push(T push) throws StackOverflowException {
		if (height == maxHeight) { // check that the stack isn't full
			throw new StackOverflowException();
		} else {
			// make the element one above the top, the new value
			this.stack[this.pointer + 1] = push;
			this.pointer++; // increment pointer variables
			this.height++;
		}
	}

	// pop an item off the stack
	public T pop() throws StackUnderflowException {
		if (this.isEmpty()) { // check that the stack isn't full
			throw new StackUnderflowException();
		} else {
			// get the value of the top element
			// no need to make it null, it is a waste of an instruction
			// it also releases no memory since we are using
			// an array to store our stack which is a static structure
			T pop = this.stack[this.pointer];
			this.pointer--; // decrement pointer variables
			this.height--;
			return pop; // return the popped value
		}
	}

	// check if stack is empty, return true if so
	public boolean isEmpty() {
		return this.height == 0 ? true : false;
	}

	// return the height of the stack
	public int getHeight() {
		return height;
	}

	// allow for a visualization of the stack by listing it
	// with the top element being encapsulated in square brackets
	@Override
	public String toString() {
		if (this.isEmpty()) { // if empty notify the user
			return "Stack is Empty";
		} else {
			// highlight the top element
			String out = "[" + this.stack[this.pointer] + "]";
			// loop through the rest of the stack backwards
			// concatenating each element to a string
			for (int i = this.pointer - 1; i >= 0; i--) {
				out = out + ", " + this.stack[i].toString();
			}
			return out; // return this string
		}
	}

}
