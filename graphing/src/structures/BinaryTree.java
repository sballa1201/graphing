package structures;

import exceptions.StackOverflowException;

public class BinaryTree {
	
	//attributes
	public BinaryTree left;
	public BinaryTree right;
	private String value;
	
	//methods
	//constructor - create a null tree
	public BinaryTree() {
		this.left = null;
		this.right = null;
		this.value = null;
	}
	
	//constructor - create leaf tree i.e. no children
	public BinaryTree(String value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}
	
	//constructor - create a tree with the children defined
	public BinaryTree(String value, BinaryTree left, BinaryTree right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	//return the value of the current node
	public String getValue() {
		return value;
	}

	//set the value of the current node
	public void setValue(String value) {
		this.value = value;
	}
	
	//count the number of nodes beneath this tree + this tree
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
	
	//perform post-order depth-first traversal
	public Stack<String> traverse() throws StackOverflowException {
		Stack<String> order = new Stack<String>(this.countNodes());
		order = traverseHelper(this,order);
		return order;
	}
	
	//the actual recursive traversal algorithm
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
