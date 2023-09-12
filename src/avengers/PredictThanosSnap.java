package avengers;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        
    	// WRITE YOUR CODE HERE

        String predictThanosSnapInputFile = args[0];
        String predictThanosSnapOutputFile = args[1];

	    // Setting the input file.
        StdIn.setFile(predictThanosSnapInputFile);

        long seed = StdIn.readLong(); 

        int num_vertex = StdIn.readInt(); 

        int [][] social_network = new int[num_vertex][num_vertex]; 

        for (int r = 0; r < num_vertex; r++){
            for (int c = 0; c < num_vertex; c++){
                social_network[r][c] = StdIn.readInt(); 
            }
        }

        // Snap 
        StdRandom.setSeed(seed);

        

        // full-3 rows/cols represent the vertexes that have been deleted 
        // if there's isn't a full 3 row and it also doesn't have any 1s, then it's disconnected from everything -- so disconnected graph
        // otherwise perform dfs to find out if the graph is connected
        for (int r = 0; r < num_vertex; r++){
            if (StdRandom.uniform() <= 0.5){
                for (int c = 0; c < num_vertex; c++){
                    social_network[r][c] = 9;
                    social_network[c][r] = 9;  
                }
                 
            }

            
        }


        // Checking if there are rows with 9s that also don't have 1s
        boolean [] all_9s = new boolean [num_vertex]; 
        
        // Checking which rows don't have all 9s
        for (int r = 0; r < num_vertex; r++){
            for (int c = 0; c < num_vertex; c++){
                all_9s[r] = check_all_9(social_network[r]); 
                
            }
        }



        int connected_boolean_size = 0; 
        
        // Gives us how many rows are false (not all 9s) --> all booleans in this array need to be true for it to be connected
        for (int i = 0; i < all_9s.length; i++){
            if (all_9s[i] == false){
                connected_boolean_size++; 
            }
        }

        boolean [] connected_graph = new boolean[connected_boolean_size]; 

        int boolean_size_counter = 0; 


        // Checking if the rows that don't have all 9s have any 1s
        // if there are no 1s: disconnected
        // if there are no 0s: connected
        for (int r = 0; r < num_vertex; r++){
            if (all_9s[r] == false){
                connected_graph[boolean_size_counter] = check_for_1(social_network[r]);
                boolean_size_counter++;  
            }             
        }

        

        boolean all_connected_true = true; 
        for (int i = 0; i < connected_graph.length; i++){
            if (connected_graph[i] == false){
                all_connected_true = false; 
            }
        }


        StdOut.setFile(predictThanosSnapOutputFile);
        StdOut.print(all_connected_true); 

    }




    private static boolean check_all_9 (int [] arr){
        boolean all_9s = true; 
        for (int i = 0; i < arr.length; i++){
            if (arr[i] != 9){
                all_9s = false; 
            }
        }

        return all_9s; 
    }

    private static boolean check_for_1 (int [] arr){
        boolean found_1 = false; 
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == 1){
                found_1 = true; 
            }
        }

        return found_1; 
    }
}
