package avengers;
import java.util.*; 

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }

        String mindstoneNeighborNeuronsInputFile = args[0];
        String mindstoneNeighborNeuronsOutputFile = args[1];

	    // Setting the input file.
        StdIn.setFile(mindstoneNeighborNeuronsInputFile);

        int num_vertex = StdIn.readInt(); 

         
        String [] vertex_names = new String [num_vertex]; 

        for (int i = 0; i < num_vertex; i++){
            vertex_names[i] = StdIn.readString(); 
        }

        // ArrayList <ArrayList<String>> neural_network = new ArrayList <ArrayList<String>> (num_vertex);
        String [][] neural_network = new String[num_vertex][num_vertex]; 

        for (int i  = 0; i < num_vertex; i++){
            neural_network[i][0] = vertex_names[i]; 
        }

        int num_edges = StdIn.readInt(); 

        for (int i = 0; i < num_edges; i++){

            String starting_vertex = StdIn.readString(); 
            String ending_vertex = StdIn.readString(); 

            int starting_index = 0; 
            
            for (int c  = 0 ; c < vertex_names.length; c++){
                if (starting_vertex.equals(vertex_names[c])){
                    starting_index = c; 
                }
            }

            int open_index = first_available_entry(neural_network[starting_index]); 
            neural_network[starting_index][open_index] = ending_vertex; 
        }

    	// WRITE YOUR CODE HERE


        int [] network_size = new int[num_vertex]; 
        int mind_stone_index = 0; 

        for (int r = 0; r < neural_network.length; r++){
            for (int c = 0; c < neural_network[r].length; c++){
                if (neural_network[r][c] != null)
                    network_size[r]++;  
            }
        }

        for (int i = 0; i < network_size.length; i++){
            if (network_size[i] == 1){
                mind_stone_index = i; 
            }
        }

        String mind_stone_id = vertex_names[mind_stone_index]; 


        ArrayList <String> mindstone_neighbors = new ArrayList<String>(); 
        for (int r = 0; r < neural_network.length; r++){
            for (int c = 1; c < neural_network[r].length; c++){
                if (neural_network[r][c] != null){
                    if (neural_network[r][c].equals(mind_stone_id)){
                        mindstone_neighbors.add(neural_network[r][0]); 
                    }
                }
                     
            }
        }

        StdOut.setFile(mindstoneNeighborNeuronsOutputFile);

        for (int i = 0 ; i <mindstone_neighbors.size(); i++){
            StdOut.println((mindstone_neighbors.get(i))); 
        }

        
    }


    private static int first_available_entry(String [] arr){
        int index = 0; 

        for (int i = 0; i < arr.length; i++){
            if (arr[i] == null){
                index = i;  
                break; 
            }
        }

        return index; 
    }
}
