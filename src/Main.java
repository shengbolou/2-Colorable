import java.io.*;
import java.util.*;


public class Main {

	//The array of color code of each vertex
	public static int color[];
	//the HashMap to store the path
	public static HashMap<Integer,Integer> path = new HashMap<Integer,Integer>();

	/*
	* This method is the algorithm to check whether a graph is 2 colorable from the given start vertex
	*/
	public static int[] is_2_Colorable(ArrayList<ArrayList<Integer>> input, int vertex){

		//color the start vertex
		color[vertex] = 1;

		//using breadth first search
		Queue<Integer> queue= new LinkedList<Integer>();
		queue.add(0);

		while(!queue.isEmpty()){
			//remove the vertex
			int tmp = queue.remove();
			int size = input.get(tmp).size();
			//going over each adjacent vertex
			for (int i = 0; i < size; i++) {
				//if it's not colored, color it with a different color
				if(color[input.get(tmp).get(i)]== -1){
					color[input.get(tmp).get(i)] = 1-color[tmp];
					//add each adjacent vertex to the queue
					queue.add(input.get(tmp).get(i));

					//add edge to HashMap
					path.put(input.get(tmp).get(i),tmp);
				}
				//if the color of adjacent vertex is the same as current vertex, means it's not two colorable, return the indexs of problem vertices
				if(color[tmp] == color[input.get(tmp).get(i)]){
						int[] toreturn = {tmp,input.get(tmp).get(i)};
						return toreturn;
				}
			}
		}

		//if everything is fine, means it's two colorable, return null
		return null;

	}

	/*
	* This method is for actually coloring the graph
	*/
	public static int[] Coloring(ArrayList<ArrayList<Integer>> input,int vertex_num){

		for (int i=0; i<vertex_num; i++) {
			if (color[i]==-1) {
				int[] result = is_2_Colorable(input,i);
				if(result != null)
					return result;
			}
			else
				continue;
		}
		return null;

	}

	/*
	* The main method
	*/
	public static void main(String [] args){
		try {
			Scanner scanner = new Scanner(new File("data/"+args[0]));
			int vertex_num = scanner.nextInt();

			//create an array of int represent the color code of each vertex
			color = new int[vertex_num];

			//initialize each vertex with color code -1 which means it's not colored
			for(int i=0; i<vertex_num;i++){
				color[i] = -1;
			}

			//create adjacent list
			ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
			//initialize adjacent list
			for(int i=0; i<vertex_num;i++){
				list.add(new ArrayList<>());
			}
			//if there is edge between a and b, then add a to b and b to a
			while (scanner.hasNextInt()) {
				int a = scanner.nextInt();
				int b = scanner.nextInt();
				list.get(a-1).add(b-1);
				list.get(b-1).add(a-1);
			}

			//call the coloring function
			int[] result = Coloring(list,vertex_num);

			//if the result is null, means the given graph is 2-colorable
			if(result==null){
				PrintWriter writer  = new PrintWriter("result/color_code_"+args[0]+".txt");
				System.out.println("This graph is 2 colorable, color code will be generated to result folder");
				for (int i=0; i<vertex_num; i++) {
					writer.println("Vertex "+i+": "+color[i]);
				}
				//close the witer
				writer.close();
			}
			//else find the path
			else{
				//create a writer
				PrintWriter writer = new PrintWriter("result/odd_cycle_"+args[0]+".txt");
				System.out.println("This graph is not 2 colorable, the odd cycle will be generated to result folder");

				int x = result[0];
				int y = result[1];
				writer.println(x+"--"+y);
				while(x != y){
					int x1 = path.get(x);
					int y1 = path.get(y);
					writer.println(x1+"--"+x);
					writer.println(y1+"--"+y);
					x = x1;
					y = y1;
				}
				//close the writer
				writer.close();
			}

			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
