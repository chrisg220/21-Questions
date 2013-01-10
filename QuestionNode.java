// Christopher Gonzales
// 11/27/2012
//
// The QuestionNode class is used to store the information for both question and object nodes in
// our data structure.  If a question node is created then the left and right fields will
// each store a reference to other nodes. If an answer node is created, then both the left and 
// right fields are set to null.

public class QuestionNode {
	public String type;
	public String text;
	public QuestionNode left;
	public QuestionNode right; 
	
	// post: constructs an empty node
	public QuestionNode() {
		this(null, null, null, null);
	}
	
	// post: constructs a node with given data
	public QuestionNode(String type, String text) {
		this(type, text, null, null);
	}
	
	// post: constructs a question with the given data and links
	public QuestionNode(String type, String text, QuestionNode left, QuestionNode right) {
		this.type = type;
		this.text = text;
		this.left = left;
		this.right = right;
	}
}
