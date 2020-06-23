// create a tree using node class

import java.util.*;
import java.lang.Math;
public class tree
{
	ArrayList <node> nodes = new ArrayList<node>();
	ArrayList <node> trasversal_list = new ArrayList<node>();
	int[] placeholder = {1}; //used for assign_nodes(children) placeholder value
	node root;

	boolean nodes_assigned = false;
	char[] node_letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
	 'O', 'P', 'Q'};

	public tree(int[] g_nodes)
	{
		//root = new node(g_nodes[0]);
		assign_nodes(g_nodes, false);
	}

	public boolean check_done(int index)
	{
		if (index > nodes.size() / 2)
			return true;
		else
			return false;
	}

	public void update_root()
	{
		root = nodes.get(0);
		System.out.println("This is the root: " + root.letter + "\n");
	}
	//Update for when two single nodes are swapped
	public void single_node_assign(node n)
	{
		int node_index = nodes.indexOf(n);
		try
		{
			n.left = nodes.get(node_index + node_index + 1);
		}
		catch(Exception e)
		{
			n.left = null;
		}
		try
		{
			n.right = nodes.get(node_index + node_index + 2);
		}
		catch (Exception e)
		{
			n.right = null;
		}

	}
	//Assigns all nodes values and their children upon instantiation
	public void assign_nodes(int[] g_nodes, boolean assign_only_children)
	{
		if (!assign_only_children)
		{
			//Create base nodes
			for (int i=0; i<g_nodes.length; i++)
			{
				char node_letter = node_letters[i];
				int node_value = g_nodes[i];
				nodes.add(new node(node_letter, g_nodes[i]));
			}
		}

		//Bind children to nodes
		int space = 0;
		int curr_index;
		boolean empty = false;

		node curr_node;
		node left_child;
		node right_child;

		for (int i=0; i<nodes.size(); i++)
		{

			curr_node = nodes.get(i);
			curr_index = nodes.indexOf(curr_node);

			//bind children to parents and parents to their children
			//left child
			try
			{
				left_child = nodes.get(curr_index + space + 1);
				if(left_child.val < 0)
					left_child = null;
				
				curr_node.left = left_child;
				curr_node.left.parent = curr_node;
			}
			catch(Exception e)
			{
				curr_node.left = null;
			}

			//right child
			try
			{
				right_child = nodes.get(curr_index + space + 2);
				if(right_child.val < 0)
					right_child = null;
				
				curr_node.right = right_child;
				curr_node.right.parent = curr_node;
			}
			catch(Exception e)
			{
				curr_node.right = null;
			}
			space += 1;

			//check if all children have been assigned
			nodes_assigned = check_done(curr_index + 2);
			if (nodes_assigned)
			{
				break;
			}
				
			else
			{
				continue;
			}
				

		}
	}

	public void add_node(char g_letter, int g_number)
	{
		node new_node = new node(g_letter, g_number);
		System.out.println(new_node.letter + " node has been added.");
		nodes.add(new_node);
		assign_nodes(placeholder, true);
		show_tree();
	}
	public void delete_node(char n_letter)
	{
		node remove_me = null;
		for (node n: nodes)
		{
			if (n.letter == n_letter)
				remove_me = n;   
		}
		nodes.remove(remove_me);
		System.out.println(remove_me.letter + " node has been removed");
		assign_nodes(placeholder, true);
		show_tree();
	}

	public void swap_node(char n1_letter, char n2_letter)
	{
		node n1 = null;
		node n2 = null;

		int n1_index = 0;
		int n2_index = 0;
		for (node n: nodes)
		{
			if (n.letter == n1_letter)
			{
				n1 = n;
				n1_index = nodes.indexOf(n1);
			}

			if (n.letter == n2_letter)
			{
				n2 = n;
				n2_index = nodes.indexOf(n2);
			}
		}

		if (n1 == null)
			System.out.println("No node has the letter: " + n1_letter + " swap has failed.");
		if (n2 == null)
			System.out.println("No node has the letter: " + n2_letter + " swap has failed.");


		if (n1 != null && n2 != null)
		{
			//Clear children before swap
			n1.left = null;
			n1.right = null;

			n2.left = null;
			n2.right = null;

			//Swap
			nodes.set(n1_index, n2);
			nodes.set(n2_index, n1);
		}

		node new_n1 = nodes.get(n1_index);
		node new_n2 = nodes.get(n2_index);
		assign_nodes(placeholder, true);
		

		System.out.println(new_n1.letter + " has been swapped with " + new_n2.letter);
		show_tree();

	}
	public void get_node(int node_index)
	{
		node found_node = null;
		String report_node = "";
		System.out.println("Getting requested node...");
		//Find node
		try
		{
			found_node = nodes.get(node_index);
			report_node = found_node.letter + "(" + found_node.val + ")";
			System.out.println("\nThis is the chosen node: " + report_node);
		}
		catch(NullPointerException e)
		{
			System.out.println("Failure. no node found!");
		}

		if (found_node != null)
		{
		//Find left child
			if(found_node.left != null)
			{
				String report_left_node = found_node.left.letter + "(" + found_node.left.val + ")";
				System.out.println("This is the left child of the node: " + report_left_node);
			}
			else
				System.out.println("No Left Child");

			//Find right child
			if (found_node.right != null)
			{
				String report_right_node = found_node.right.letter + "(" + found_node.right.val + ")";
				System.out.println("This is the right child of the node: " + report_right_node);
			}
			else
				System.out.println("No Right Child");
			System.out.println("\n");
		}
	}
	
	//Used to update string spacing buffers and space between nodes in showtree()
	public String update_buffer(int range, int current_layer)
	{
		String buffer_space;
		if (current_layer >= 4)
			buffer_space = "     ";
		else
			buffer_space = "    ";

		buffer_space = buffer_space.repeat(range);
		return buffer_space;
	}
	public String update_node_space(int range, int current_layer)
	{
		String node_space;
		if(current_layer >= 5)
			node_space = "";
		else
			node_space = " ";
			node_space = node_space.repeat(range * 2);
		return node_space;
	}

	//(left,root,right)
	public void show_trasversal_order(String trasversal_type)
	{
		System.out.printf(trasversal_type + " (");
		for(node n: trasversal_list)
		{
			System.out.print(n.letter);
		}
		System.out.printf(")\n");
	}
	public void inorder(node starter)
	{	
		if (trasversal_list.size() < nodes.size() - 1)
		{
			//trasverse the left side
			node curr_node = starter;
			while (curr_node.left != null && (!trasversal_list.contains(curr_node.left)))
			{
				curr_node = curr_node.left;
			}
			trasversal_list.add(curr_node);

			//add left child parent
			curr_node = curr_node.parent;
			while(trasversal_list.contains(curr_node))
			{
				curr_node = curr_node.parent;
			}
			trasversal_list.add(curr_node);

			if (curr_node.right != null &&  (!trasversal_list.contains(curr_node.right)))
			{
				curr_node = curr_node.right;
				inorder(curr_node);
			}
			else
				if(trasversal_list.size() == nodes.size())
					show_trasversal_order("inOrder");
				else
					inorder(curr_node);
		}
		else
		{
			trasversal_list.add(starter);
			show_trasversal_order("inOrder");
		}
			
	
		//trasversal_list.add(curr_node);
		
	}

	public void show_tree()
	{
		int nodes_this_level = 1;
		int counter = 0;
		int current_layer = 0; //determines how much buffer space we need.

		int increment = 0; //determines layers of tree using 2^increment
		String buffer_space = ""; //4 spaces for start of each line.
		String node_space = " "; //space between each layer of nodes in the tree when drawn.
		

		//Find how many layers there are using 2^increment
		while(Math.pow(2, increment) < nodes.size())
		{
			increment += 1;
		}
		int total_increment = increment; //Total amount of tree layers we have
		String show_string = update_buffer(increment, current_layer);

		//logic
		node curr_node;
		for (int i=0; i<nodes.size(); i++)
		{
			curr_node = nodes.get(i);
			show_string = show_string + curr_node.letter; //+ "(" + curr_node.val + ")";
			counter += 1;

			//check when all nodes on layer are added
			if (counter == nodes_this_level)
			{
				//System.out.println(nodes_this_level);
				increment -= 1;
				current_layer += 1;
				buffer_space = update_buffer(increment, current_layer);
				show_string = show_string + "\n\n" + buffer_space;
				if (nodes_this_level == 1)
					nodes_this_level = 1 * 2;
				else
					nodes_this_level = nodes_this_level * 2;

				counter = 0;
				current_layer += 1;


			}
			else
			{
				//Adjust space in between pair set nodes
				if (i%2 == 0)
				{
					if (current_layer >= 5)
						node_space = "      ";
				}

				//Adjust space between layers
				else
					node_space = update_node_space(increment, current_layer);

				show_string = show_string + node_space;
			}
		}
		update_root();
		System.out.println(show_string);
		
		

	}
	public static void main(String[] args) 
	{
		// optimized length is 15 due to spacing control in console
						//Use -1 in a space to make the tree not create that node
						//A  B  C  D  E  F  G  H  I  J  K
		int[] node_arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		tree my_tree = new tree(node_arr);
		System.out.println("----This is the original tree----");
		my_tree.show_tree();
		my_tree.get_node(7);
		my_tree.delete_node('G');
		my_tree.add_node('Z', 5);
		my_tree.swap_node('A', 'I');
		my_tree.inorder(my_tree.root);
		
	}
}
//fix delete, add, swap so that the inorder updates after they are finished

/*
                 INDICIES
			        0
			    1      2  
		    3     4  5     6
		  7  8  9



      			
*/