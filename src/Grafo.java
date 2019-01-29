
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import org.apache.commons.collections15.Transformer;

public class Grafo {
    public static void main( String[] args ) throws IOException
    {
    		Forest<String, String> g = new DelegateForest<String, String>();
    	    //SparseMultigraph g = new SparseMultigraph();
    		
    		int nodi_P=0,nodi_C=0,nodi_L=0,nodi_D=0,archi_PC=0,archi_CL=0,archi_LD=0;
			Color L0 = Color.BLUE, L1 = Color.RED, L2 =Color.GREEN, L3 = Color.YELLOW;

			HashMap<String,Color> vertice_colore = new HashMap<>();

    	    int num_events= 2000;
    	    g.addVertex("P"); //Livello 0
			vertice_colore.put("P",L0);
    	    nodi_P++;


    	    ArrayList<String> contesti = Jung.test.ReadCSV.getContesti();
    	    nodi_C=contesti.size();
    	    for (int i = 0; i < nodi_C; i++) {
    	    	g.addEdge("PC:"+(++archi_PC), new Pair<String>("P", contesti.get(i)));
    	    	vertice_colore.put(contesti.get(i),L1);
	        }      //Livello 0-1

    	    HashMap<String,String[]> luoghi = Jung.test.ReadCSV.getPlacesNew();
    	    //alcuni luoghi hanno lo stesso nome, al momento vengono accorpati (19 luoghi, 852 in totale 833 usati)	
    	    Object[] nomi= luoghi.keySet().toArray();
    	    nodi_L=nomi.length;
    	    nodi_D= Jung.test.ReadCSV.getNumCategorie(luoghi);

    	    for (int i = 0; i < nomi.length; i++) {
    	    	String posto= nomi[i].toString();
    	    	vertice_colore.put(posto,L2);
    	    	for (int y = 0; y < luoghi.get(posto).length; y++) {
    	    		g.addEdge("LD:"+(++archi_LD), new Pair<String>(posto, luoghi.get(posto)[y]));
    	    		vertice_colore.put(luoghi.get(posto)[y], L3);
    	    	}
    	    }//Livello 2-3
    	   	final int numero_luoghi = nodi_L;

    	    //TODOBETTER 1-2, bisogna pensare collegamenti tra luoghi e contesti reali, per adesso è random la scelta.
    	    //Qualcosa come gruppare i luoghi per categorie e scegliere in base al contesto un random tra un sottogruppo di categorie.
	    	int p=0;
    	    for (int i = 0; i < num_events; i++) {
    	    	if (p==contesti.size()) p=0;
    	    	int rand_L = (int)(Math.random() * luoghi.size());
    	    	g.addEdge("CL:"+(++archi_CL), new Pair<String>(contesti.get(p), nomi[rand_L].toString()));
    	    	p++;
	        }
	    
    	    
    	    //alcuni dati sul Grafo a scopo di verifica
    	    //Livello 0 - nodi Persona, al momento caso singolo =1;
    	    //Livello 1 - nodi Contesto, dati dal file contesti.csv generato in base a quelli forniti dal professore, volendo sono modificabili
    	    //Livello 2 - nodi Luogo, estratti dal file business_torino.csv
    	    //Livello 3 - nodi Descrizione, estratti dal file business_torino.csv
    	    //Archi 0-1 PC - archi da Persona a Contesti, livello full connected
    	    //Archi 1-2 CL - archi da Contesti a Luoghi, al momento generati casualmente in base al valore num_events, ma da sostituire con le azioni passate degli utenti
    	    //Archi 2-3 LD - archi da Luoghi a Descrizioni, livello statico estratti dal file business_torino.csv
    	    System.out.println("Dettagli Grafo");	
    		System.out.println("Livello 0 - Nodi P: "+ nodi_P);	
    		System.out.println("Livello 1 - Nodi C: "+ nodi_C);	
    		System.out.println("Livello 2 - Nodi L: "+ nodi_L);	
    		System.out.println("Livello 3 - Nodi D: "+ nodi_D);	
    		System.out.println("Archi 0-1 PC: "+ archi_PC);	
    		System.out.println("Archi 1-2 CL: "+ archi_CL);	
    		System.out.println("Archi 2-3 LD: "+ archi_LD);	
    		System.out.println("---");
    	    
    	    //il pagerank è ancora quello normale finchè non capisco come creare il parametro per poter usare quello con prior
    	    PageRank ranker = new PageRank(g , 0.3);
    	    ranker.evaluate();
    	    
    	    
    		System.out.println("Tolerance = " + ranker.getTolerance());	
    		System.out.println("Dump factor = " + (1.00d - ranker.getAlpha() ) );	
    		System.out.println("Max iterations = " + ranker.getMaxIterations() );	
    		
    		for (Object v : g.getVertices()) {
    			//System.out.println("Score = " + ranker.getVertexScore(v));
    		}

			Transformer<String, Paint> t_ColorVertex = new Transformer<String, Paint>() {
				public Paint transform(String s) {
					Paint color = vertice_colore.get(s);
					return color;
				}
			};
    	   
    		DAGLayout l = new DAGLayout(g);
    	    VisualizationImageServer vs =
    	      new VisualizationImageServer(
    	        l, new Dimension(1200, 800));
    		vs.getRenderContext().setVertexFillPaintTransformer(t_ColorVertex);
    		/*
    		FRLayout l = new FRLayout(g);
    		
    	    VisualizationImageServer vs =
    	      new VisualizationImageServer(
    	        l, new Dimension(1500, 1300));
    	       */
    	   
    	    JFrame frame = new JFrame();
    	    frame.getContentPane().add(vs);
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.pack();
    	    frame.setVisible(true);
    	 
    	    
    }
}
