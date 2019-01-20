package Jung.test;

import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.scoring.PageRankWithPriors;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
 

public class Example 
{
    public static void main( String[] args )
    {
    		Forest g = new DelegateForest();
    	    //SparseMultigraph g = new SparseMultigraph();
    	
    		int num_p=50;
    	    int num_e=5;
    	    int num_l=30;
    	    int num_events= 600;
    	    g.addVertex("P");
    	   
    	    
    	    
    	    for (int i = 0; i < num_p; i++) {
        	    g.addEdge("P:"+i, new Pair("P", "P"+i));
	        }
    	    
    	    /*
    	    g.addVertex("E");
    	    for (int i = 0; i < num_e; i++) {
        	    g.addEdge("E:"+i, new Pair("E", "E"+i));
	        }

    	    g.addVertex("L");
    	    
    	    for (int i = 0; i < num_l; i++) {
        	    g.addEdge("L:"+i, new Pair("L", "L"+i));
	        }
    	    */
	    	int p=0;
    	    for (int i = 0; i < num_events; i++) {
    	    	if (p==num_p) p=0;
    	    	int rand_E = (int)(Math.random() * num_e);
    	    	int rand_L = (int)(Math.random() * num_l);
    	    	g.addEdge("PE:"+i, new Pair("P"+p,"E"+rand_E));
    	    	g.addEdge("LE:"+i, new Pair("E"+rand_E, "L"+rand_L));
    	    	p++;
	        }
    	    
    	    
    	    PageRank ranker = new PageRank(g , 0.3);
    	    ranker.evaluate();
    	    
    	    
    		System.out.println("Tolerance = " + ranker.getTolerance());	
    		System.out.println("Dump factor = " + (1.00d - ranker.getAlpha() ) );	
    		System.out.println("Max iterations = " + ranker.getMaxIterations() );	
    		
    		for (Object v : g.getVertices()) {
    			System.out.println("Score = " + ranker.getVertexScore(v));	
    }
    	    
    		
    		DAGLayout l = new DAGLayout(g);
    		l.done();
    	    VisualizationImageServer vs =
    	      new VisualizationImageServer(
    	        l, new Dimension(1500, 1300));
    	   
    	    JFrame frame = new JFrame();
    	    frame.getContentPane().add(vs);
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.pack();
    	    frame.setVisible(true);

    	}
}
