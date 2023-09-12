package avengers;
import java.util.*; 
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];

	    // Setting the input file.
        StdIn.setFile(locateTitanInputFile);

        int num_vertex = StdIn.readInt(); 

        double [] functionality = new double [num_vertex]; 
         
        for (int i = 0; i < num_vertex; i++){
            int index = StdIn.readInt(); 
            double functionality_val = StdIn.readDouble(); 

            functionality[index] = functionality_val; 
        }

        int [][] energy_cost = new int[num_vertex][num_vertex]; 

        for (int r = 0; r < num_vertex; r++){
            for (int c = 0; c < num_vertex; c++){
                energy_cost[r][c] = StdIn.readInt(); 
            }
        }

        // Populating and printing new weighted adjacency matrix

        int [][] weighted_cost = new int[num_vertex][num_vertex]; 
        for (int r = 0; r < energy_cost.length; r++){
            for (int c = 0; c < energy_cost.length; c++){
                double vertex_1_func = functionality[r]; 
                double vertex_2_func = functionality[c];
                
                double weighted_energy_cost_val = energy_cost[r][c] / (vertex_1_func * vertex_2_func); 

                weighted_cost[r][c] = ((int) weighted_energy_cost_val); 

            }
        }

        int infinity = Integer.MAX_VALUE; 

        int [] min_path = new int [num_vertex]; 
        boolean [] visited_vertex = new boolean [num_vertex]; 

        for (int i = 0; i < min_path.length; i++){
            min_path[i] = infinity; 
        }

        min_path[0] = 0; 
        // Need to disregard the first path when finding the minimum distance to titan

        // Initializing all the visited vertexes as false, when we visit them, it will become true
        for (int i = 0; i < visited_vertex.length; i++){
            visited_vertex[i] = false; 
        }

        for (int r = 0; r < num_vertex - 1; r++){
         
            int min_dist_ind = get_min_distance(num_vertex, min_path, visited_vertex); 
            visited_vertex[min_dist_ind] = true; 

            for (int c = 0; c < num_vertex; c++){
                if (visited_vertex[c] == false && weighted_cost[min_dist_ind][c] != 0){
                    if (min_path[min_dist_ind] < infinity){
                        if (min_path[c] > min_path[min_dist_ind] + weighted_cost[min_dist_ind][c]){
                            min_path[c] = min_path[min_dist_ind] + weighted_cost[min_dist_ind][c]; 
                        }
                    }
                }
            }
        }

        int path_to_titan = min_path[num_vertex - 1];  
        StdOut.setFile(locateTitanOutputFile);
        StdOut.print(path_to_titan);

    }

    private static int get_min_distance(int n_vertex, int [] distances, boolean [] disregard_vertex){

        int min_val = Integer.MAX_VALUE; 
        int min_ind = 0; 

        boolean visited_all_index = true; 

        for (int i = 0; i < n_vertex; i++){
            if (disregard_vertex[i] != true && distances[i] <= min_val){ 
                min_val = distances[i]; 
                visited_all_index = false; 
                min_ind = i; 

            }
        }

        // Need to reset and check in the case that it's false and all vertexes haven't been visited
        visited_all_index = true; 

        for (int x = 0;  x < disregard_vertex.length; x++){
            if (disregard_vertex[x] == false){
                visited_all_index = false; 
            }
        }

        return min_ind; 

    }




}
