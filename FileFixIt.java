import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class FileFixIt {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int T = in.nextInt(); // get no of cases
		in.nextLine(); // get extra return character
		
		StringBuilder output = new StringBuilder();
		
		for (int i = 1; i <= T; ++i){ // main loop for cases
			// get and split main input
			String input = in.nextLine();
			String[] inputArr = input.split(" ");
			int N = Integer.parseInt(inputArr[0]); // get no of existing paths
			int M = Integer.parseInt(inputArr[1]); // get no of new paths to be created
			
			String[] existingPathsArr = new String[N];
			String[] newPathsArr = new String[M];
			for (int j = 0; j < N; ++j){
				existingPathsArr[j] = in.nextLine(); // get each existing path
			}
			for (int j = 0; j < M; ++j){
				newPathsArr[j] = in.nextLine(); // get each new path to be created
			}
			
			// Start of Processing
			// create tree of existing paths
			TreeNode tree = (new FileFixIt()).new TreeNode("root"); 
			for (int j = 0; j < N; ++j){
				String[] foldersArr = existingPathsArr[j].split("/");
				TreeNode child = tree;
				for (int k = 1, len = foldersArr.length; k < len; ++k){
					child = child.addChild(foldersArr[k]);
				}
			}
			// perform checking of directories that need to be created
			for (int j = 0; j < M; ++j){
				String[] foldersArr = newPathsArr[j].split("/");
				TreeNode child = tree;
				for (int k = 1, len = foldersArr.length; k < len; ++k){
					child = child.addChild(foldersArr[k], tree);
				}
			}
			
			output.append(String.format("Case #%d: %d\n", i, tree.getNewCount()));			
			// End of Processing
		}
		in.close();
		System.out.print(output);
	}
	
	public class TreeNode {
	    String data;
	    TreeNode parent;
	    List<TreeNode> children;
	    int newCounter;

	    public TreeNode(String data) {
	        this.data = data;
	        this.children = new LinkedList<TreeNode>();
	        this.newCounter = 0;
	    }

	    public TreeNode addChild(String child) {
	    	return addChild(child, null);
	    }
	    public TreeNode addChild(String child, TreeNode nodeWithCounter) {
	    	TreeNode childNode;
	    	if ((childNode = this.getChild(child)) != null) {
	    		return childNode;
	    	}
	    	if (nodeWithCounter != null)
	    		nodeWithCounter.newCounter++;
	        childNode = new TreeNode(child);
	        childNode.parent = this;
	        this.children.add(childNode);
	        return childNode;
	    }
	    
	    public TreeNode getChild(String child) {
	    	for (int i = 0; i < this.children.size(); i++){
	    		TreeNode node = this.children.get(i);
	    		if (node.data.equals(child))
	    			return node;
	    	}
	    	return null;
	    }
	    
	    public int getNewCount(){
	    	return this.newCounter;
	    }
	}
}