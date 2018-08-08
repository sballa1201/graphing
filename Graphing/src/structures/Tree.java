package structures;

public class Tree {
	
	public Tree left;
	public Tree right;
	private String value;
	
	public Tree() {
		this.left = null;
		this.right = null;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
