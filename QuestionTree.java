// Christopher Gonzales
// 11/27/2012
//
// This program creates a game where the user is asked to think of an object and will then be asked
// a series of yes/no questions until the computer makes its best guess of the object. If it is 
// correct, it wins. Otherwise it will become more intelligent by storing the user's new object 
// next to the incorrectly guessed object, distinguishing the two with a given yes/no question. 
// This program is able to read in other trees, as well as store the current tree to an output 
// file, all in preorder form.
//

import java.io.*;
import java.util.*;

public class QuestionTree {
	private QuestionNode overallRoot;
	private Scanner console;
	
	// post: constructs a question tree that stores one "computer" object
	public QuestionTree() {
		console = new Scanner(System.in);
		overallRoot = new QuestionNode("A:","computer");
	}
	
	// pre : given file is legal and in "standard format" containing a nonempty sequence of line 
	//		 pairs representing nodes. Nodes should appear in preorder form. 
	// post: replaces the current tree by reading another tree from the given file. 
	public void read(Scanner input) {
		if (!input.hasNext())
			throw new IllegalArgumentException();
		overallRoot = readHelper(input);
	}
	
	// pre : given file is legal and in "standard format" containing a nonempty sequence of line 
	//		 pairs representing nodes. Nodes should appear in preorder form. 
	// post: returns either a question or object with the given data derived from the line pairs. 
	private QuestionNode readHelper(Scanner input) {
		String type = input.nextLine().trim();
		String text = input.nextLine().trim();
		QuestionNode root = new QuestionNode(type, text);
		if (type.equals("A:")) {
			return root;
		} else {
			root.left = readHelper(input);
			root.right = readHelper(input);
		}
		return root;
	}
	
	// pre : output file name is legal
	// post: prints the current tree in preorder fashion to an output file.
	public void write(PrintStream output) {
		writeHelper(output, overallRoot);
	}
	
	// pre : output file name is legal
	// post: prints the current tree in preorder fashion to an output file.
	private void writeHelper(PrintStream output, QuestionNode root) {
		if (root != null) {
			output.println(root.type);
			output.println(root.text);
			if (root.type.equals("Q:")) {
				writeHelper(output, root.left);
				writeHelper(output, root.right);	
			}
		}
	}
	
	// post: asks a series of yes/no questions until the program either correctly guesses the 
	//       user's object, or until the program discovers a new object, in which case it will
	//       then insert it into the tree and replace the current tree with the new tree.
	public void askQuestions() {
		overallRoot = askQuestionsHelper(overallRoot);
	}
	
	// post: uses user's answers as input until it either correctly guesses the user's object,
	//       or it will insert a new object the into the tree and replace the current tree with
	//		 the new tree.
	private QuestionNode askQuestionsHelper(QuestionNode root) {
		if (root != null) {
			if (root.type.equals("A:")) {
				if (yesTo("Would your object happen to be " + root.text + "?")) 
					System.out.println("Great, I got it right!");
				else {
					System.out.print("What is the name of your object? ");
					String objectName = console.nextLine();
					System.out.println("Please give me a yes/no question that");
					System.out.println("distinguishes between your object");
					System.out.print("and mine--> ");
					String question = console.nextLine();
					QuestionNode objectNew = new QuestionNode("A:", objectName);
					if (yesTo("And what is the answer for your object?")) 
						root =  new QuestionNode("Q:", question, objectNew, root);
					else 
						root = new QuestionNode("Q:", question, root, objectNew);
				}
			} else { 
				if (yesTo(root.text)) 
					root.left = askQuestionsHelper(root.left);
				else 
					root.right = askQuestionsHelper(root.right);
			}
		}
		return root;
	}
	
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
