/* EE422C Assignment #3 submission by 
 * Tatiana Flores
 * TH27979
 */
package assignment3;

import java.util.Map;
import java.util.HashMap;


/*
 * GraphNode are the words and their interconnections.
 * 
 */

public class GraphNode {
	public static final boolean debug=false;
	//word is the word this node represents
	public final String word;
	//next_words is a map of all other nodes this connects to with the weight stored on it.
	private Map<GraphNode, Integer> next_words=new HashMap<GraphNode,Integer>();
	// all_words is a list of all words found.
	//Is a static map so anyone can access it
	private static Map<String,GraphNode> all_words=new HashMap<String,GraphNode>();
/**
 *Creates a new node and adds to all_words
 * @param word. The word this graph_node represents
 */
	private GraphNode(String word){
		this.word=word.toLowerCase();
		//Add this words to the list of all words.
		all_words.put(word,this);
	}
	/**
	 *Finds and returns a GraphNode for the string provided. Creates one if it can't find it.
	 *
	 * @param word. Word to find and return a node for
	 * @return GraphNode representing the word
	 */
	public static GraphNode find(String word){
		//Always look for lower cases
		word=word.toLowerCase();
		//If we already have this word just return the node for it
		if(all_words.containsKey(word)){
			return(all_words.get(word));
		}
		//Otherwise create a new node and return it.
		//It registers itself.
		return(new GraphNode(word));
	}
	/**
	 *Connects this GraphNode to another if the connection exists just increment the weight
	 *
	 *
	 * @param next_word The String of the next word
	 */
	public void connect(String word){
		connect(GraphNode.find(word));
		
	}
	
	/**
	 *Connects this GraphNode to another if the connection exists just increment the weight
	 *
	 *
	 * @param next_word The Node of the next word
	 */
	public void connect(GraphNode to_node){
		//Get the node for the next word
		//If we already have an edge then increment its weight
		if(next_words.containsKey(to_node)){
			//Find the node, add one to it's weight and put it back into the map
			next_words.put(to_node,next_words.get(to_node)+1);
		}
		else {
			//If we could not find the word then store it with a weight of 1
			next_words.put(to_node, 1);
		}
		if(debug){System.out.println("Added Connection "+word+" "+to_node.word);}
	}

	/**
	 *Find the best string to connect from the current node to the next string
	 *
	 *
	 * @param next_word String The next word to find connections between.
	 */
	public String get_best_connection(String to){
		//Just find the node and return the best_connection
		return(get_best_connection(find(to)));
	};
	
		/**
		 *Find the best string to connect from the current node to the next node
		 *
		 *
		 * @param next_word Node The next word to find connections between.
		 * 
		 * Returns a string with the best middle word
		 */
	
	public String get_best_connection(GraphNode to_node){
		//The max(best) weight we've found so far
		Integer maxweight=0;
		//The bestword so far (associated with the maxweight) 
		String  bestword=null;
		//Look for all possible middle word
		for(GraphNode middle: next_words.keySet()){
			//If the middle word contains the to_node then it's a valid middle word and it's weight is the edges added together			
			if(middle.next_words.containsKey(to_node)){
				//Calculate it's weight but adding the two verti together 
				Integer weight=next_words.get(middle)+middle.next_words.get(to_node);
				//If this weight is better then the last best(max) weight then save this word
				if(weight>maxweight){
					maxweight=weight;
					bestword=middle.word;
				}	
			}
		}
		//Return the best word I have found(or null)
		return(bestword);
	}
/**
*                  *Find the best pat to connect from the current node to the next node with a limited depth
*                                   *
*                                                    *
*                                                                     * @param next_word Node The next word to find connections between.
*                                                                                      *
*                                                                                                       * Returns a string with the best middle word
**/


	public Path  find_shortest_path(GraphNode to_node,int depth){
		//If direct connection that return  empty string with the weight
		if(next_words.containsKey(to_node)){
			if(debug){System.out.println(word+" connected"+to_node.word + " weight "+next_words.get(to_node));}
			return new Path("",next_words.get(to_node));
		}
		//If we can recurse then do so
		if(depth>0){
			//For each node check if they have a path
			Path best=null;
			for(GraphNode middle: next_words.keySet()){
				Path result=middle.find_shortest_path(to_node,depth-1);
				if(result!=null){result.add(middle.word,1/next_words.get(middle));}
				if(result!=null && (best==null || result.better(best))){
					best=result;
				}
			}
			if(debug){System.out.println("Got Path from "+word+" to "+to_node.word+" via: "+best);}
			return(best);
		}
		return(null);
	}	
}
