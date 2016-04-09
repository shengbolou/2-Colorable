import java.io.*;
import java.util.*;


public class Main {

	public static int color[];
	/*
	* This method is the actual algorithm to check whether a graph is 2 colorable
	*/
	public static int[] is_2_Colorable(ArrayList<ArrayList<Integer>> input, int vertex_num){

		//create an array of int represent the color code of each vertex
		color = new int[vertex_num];

		//initialize each vertex with color code -1 which means it's not colored
		for(int i=0; i<vertex_num;i++){
			color[i] = -1;
		}

		//color the first vertex
		color[0] = 1;

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
				}
				//if the color of adjacent vertex is the same as current vertex, means it's not two colorable,return the index of problem vertex
				if(color[tmp] == color[input.get(tmp).get(i)]){
						int[] toreturn = {tmp,i};

						return toreturn;
				}
			}
		}

		//if everything is fine, means it's two colorable, return -1
		return null;

	}
	public static void main(String [] args){
		try {
			Scanner scanner = new Scanner(new File("../data/largegraph1"));
			int vertex_num = scanner.nextInt();

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
			int[] result = is_2_Colorable(list,vertex_num);

			if(result==null){
				System.out.println("This graph is 2 colorable");
				for (int i=0; i<vertex_num; i++) {
					System.out.println("Vertex "+i+": "+color[i]);
				}
			}
			else{
				System.out.println("This graph is not 2 colorable");
				System.out.println("vertex " + result[0] +" and vertex "+ list.get(result[0]).get(result[1]) + " are adjacent vertices but they have the same color "+ color[result[0]]);
			}


			scanner.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
