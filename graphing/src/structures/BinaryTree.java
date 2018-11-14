package structures;

import exceptions.StackOverflowException;

public class BinaryTree {
	
	//attributes
		//children
		public BinaryTree left;
		public BinaryTree right;
		//node value
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
		//set the number of nodes counted to 0
		int count = 0;
		
		//recursive case, counts the number of nodes in its children
		//if they are not null
		if(this.left != null) {
			count = count + this.left.countNodes();
		}
		if(this.right != null) {
			count = count + this.right.countNodes();
		}
		//return the count of the children
		//but increment it to count it self
		return count + 1;
	}
	
	//post-order depth-first traversal wrapper function
	public Stack<String> traverse() throws StackOverflowException {
		//create a stack of max height
		//which is the number of nodes in the entire tree
		//this is to save memory
		Stack<String> order = new Stack<String>(this.countNodes());
		//traverse the tree
		order = traverseHelper(this,order);
		return order;
	}
	
	//the actual recursive traversal function
	private static Stack<String> traverseHelper(BinaryTree tree, Stack<String> order) throws StackOverflowException {
		//base case, if the tree is null just return the unmodified stack
		if(tree == null) {
			return order;
		} else {	//recursive case search its children if not null
			//since this is post-order, the order is left, right then root
			//traverse left
			order = traverseHelper(tree.left, order);
			//traverse right
			order = traverseHelper(tree.right, order);
			//finally push the root value into the stack
			order.push(tree.value);
			return order;
		}
	}
	
	public static void main(String[] args) throws StackOverflowException {
		BinaryTree root;
		BinaryTree left = new BinaryTree("C", new BinaryTree("A"), new BinaryTree("B"));
		BinaryTree right = new BinaryTree("F", new BinaryTree("D"), new BinaryTree("E"));
		root = new BinaryTree("G",left,right);
		System.out.println(root.traverse());
	}
	
}
