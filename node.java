public class node
{
	int val;
	char letter;
	node parent;
	node left;
	node right;

	node(char letter, int val)
	{
		this.letter = letter;
		this.val = val;
	}

	public void add_left(node new_left)
	{
		left = new_left;
	}

	public void add_right(node new_right)
	{
		right = new_right;
	}
	public static void main(String[] args) {
		int[] start_arr = {1, 2, 3, 4};
	}
}