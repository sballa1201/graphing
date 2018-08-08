package structures;

import exceptions.StackOverflowException;

public class BinaryTree {
	
	public BinaryTree left;
	public BinaryTree right;
	private String value;
	
	public BinaryTree() {
		this.left = null;
		this.right = null;
	}
	
	public BinaryTree(String value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}
	
	public BinaryTree(String value, BinaryTree left, BinaryTree right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int countNodes() {
		int number = 0; 
		if(this.left != null) {
			number = number + this.left.countNodes();
		}
		if(this.right != null) {
			number = number + this.right.countNodes();
		}
		return number + 1;
	}
	
	public Stack<String> traverse() throws StackOverflowException {
		Stack<String> order = new Stack<String>(this.countNodes());
		order = traverseHelper(this,order);
		return order;
	}
	
	private static Stack<String> traverseHelper(BinaryTree tree, Stack<String> order) throws StackOverflowException {
		if(tree == null) {
			return order;
		} else {
			order = traverseHelper(tree.left, order);
			order = traverseHelper(tree.right, order);
			order.push(tree.value);
			return order;
		}
		
	}
	
	public static void main(String[] args) throws StackOverflowException {
		BinaryTree t = new BinaryTree();
		t.setValue("+");
		t.left = new BinaryTree("6");
		t.right = new BinaryTree("3");
		//t.right = new BinaryTree();
		System.out.println(t.traverse());
		
	}
	
}
