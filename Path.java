/* EE422C Assignment #3 submission by 
 * Tatiana Flores
 * TH27979
 */
package assignment3;

import java.util.Map;
import java.util.HashMap;


//Utility class to store stuff
public class Path {
	String word;
	//Weight is the path weight as defined in exercise
	int weight;
	//Depth is how long the path is. Needed to always prefer shorter paths
	int depth;
	
   public Path( String s, int w ){
	word=s;
	weight=w;	
	depth=0;
	}
   public Path (Path p){word=p.word;weight=p.weight;depth=p.depth;}
   public Path add (String s,int w){
	word=s+" "+word;
	this.weight+=w;
	this.depth++;
	return(this);
	}
   public Path add (Path p){
	word=p.word+" "+word;
	weight+=p.weight;
	depth++;
	return(this);
	}
    public boolean better(Path p){
	if(depth!=p.depth){return(depth<p.depth);}
	return(weight>p.weight);
	}
    public String toString(){
	return(word);
	}
}
