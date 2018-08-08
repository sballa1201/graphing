package structures;

public class BinaryTree {
	
	public BinaryTree left;
	public BinaryTree right;
	private String value;
	
	public BinaryTree() {
		this.left = null;
		this.right = null;
	}
	
	public BinaryTree(BinaryTree left, BinaryTree right) {
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
	
	public static void main(String[] args) {
		BinaryTree t = new BinaryTree();
		t.left = new BinaryTree();
		//t.right = new BinaryTree();
		System.out.println(t.countNodes());
		
	}
	
}
