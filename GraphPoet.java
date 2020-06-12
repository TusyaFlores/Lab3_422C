/* EE422C Assignment #3 submission by 
 * Tatiana Flores
 * TH27979
 */

package assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GraphPoet {
    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */   
 
    public static int max_depth=4;
    public static final boolean debug=false;
    public GraphPoet(File corpus) throws IOException {
    	BufferedReader br=new BufferedReader(new FileReader(corpus));
    	String line=br.readLine();
    	//Read in the input file line by line
    	while (line!=null){
                GraphNode last_node=null;
    		//When reading a line set the last word to null as this starts new parsing and we don't like the two lines
			//Split on non word characters so we eliminate special characters !.* ...
			 for(String word: line.split("\\W+")){
				 GraphNode this_node=GraphNode.find(word.toLowerCase());
				 	//If there is previous(last) word
			 		if(last_node!=null){
			 			//Add a connection between the last word and this one. 
			 			last_node.connect(this_node);			 		
			 		}
			 		//Save this word as the last word.
		 		last_node=this_node;
		 	}
			 //Read in the next line to start the loop again
		 	line=br.readLine();
		}
    	//Close the file we're done training
		br.close();
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {
    	BufferedReader br=new BufferedReader(new FileReader(input));
    	//Store the final output
    	String output="";
    	GraphNode last_node=null;
    	//start reading
    	String line=br.readLine();
    	//While there is a valid line
    	while (line!=null){
    		//A line starts new parsing. forget hte lat word
			last_node=null;
			//Split on things that are not word, but then add them back(Iss to hard)
			//For each word in sentance
			for(String word: line.split("\\W+")){
 				//If there was a previous(last) word
				GraphNode this_node=GraphNode.find(word.toLowerCase());
				if(last_node!=null){
	 				//Find the best word to go between the last word and this one.					
					String middle=null;
					//String middle=last_node.get_best_connection(this_node);
					//if(debug){System.out.println("OLD:"+last_node.word+"=>"+word+" via :"+middle);}
			   	     	Path  p= last_node.find_shortest_path(this_node,max_depth);
					if(p!=null){
                                        	if(debug){System.out.println("NEW:"+last_node.word+"=>"+word+" via :"+p.word);}
						middle=p.word;
					}

	 				//If we found a middle word add it to the output
	 				if(middle!=null){output+=middle;}
	 			}
				//Add the wrod we just read to the output with a space after
				//
	 			output+=word+" ";
	 			//Remember current wrod as last word
	 			last_node=this_node;		 			
	 		}
			//Read next line
	 		line=br.readLine();
	 		//Add a break to the output
	 		if(line!=null){
	 			output+="\n";
	 		}
		}
    	//close input stream
		br.close();
		return(output);
    }
}
