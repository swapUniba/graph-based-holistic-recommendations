package jung;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.PageRankWithPriors;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public class Grafo {

	private DelegateForest<String, String> graph;

	public Grafo(String data) throws IOException {
		FromFile.SetData(data);

		//-----------INIT GRAPH-------------------
		graph = new DelegateForest<String, String>();
		//SparseMultigraph g = new SparseMultigraph();

		int nodi_P=0,nodi_C=0,nodi_L=0,nodi_D=0,archi_PC=0,archi_CL=0,archi_LD=0;

		int num_events= 200;
		graph.addVertex("P_"); //Livello 0
		nodi_P++;

		ArrayList<String> contesti = FromFile.getContesti();
		nodi_C=contesti.size();
		for (int i = 0; i < nodi_C; i++) {
			graph.addEdge("PC:"+(++archi_PC), new Pair<String>("P_", "C_"+contesti.get(i)));
		}      //Livello 0-1

		HashMap<String,String[]> luoghi = FromFile.getPlacesNew();
		HashMap<String,List<Object>> contestiCateg = FromFile.getContestiCategorizzati();
		//alcuni luoghi hanno lo stesso nome, al momento vengono accorpati (19 luoghi, 852 in totale 833 usati)
		Object[] nomi= luoghi.keySet().toArray();
		nodi_L=nomi.length;
		nodi_D= FromFile.getCategorie().size();

		for (int i = 0; i < nomi.length; i++) {
			String posto= nomi[i].toString();
			for (int y = 0; y < luoghi.get(posto).length; y++) {
				graph.addEdge("LD:"+(++archi_LD), new Pair<String>("L_"+posto, "D_"+luoghi.get(posto)[y]));
			}
		}//Livello 2-3


		//Per adesso, è spalmato equamente sui contesti
		//Prendo i contesti di fila, prendo un luogo a caso, verifico se il luogo ha tutte le categorie adatte per quel contesto, se si linko altrimenti riprovo
		int p=0;
		for (int i = 0; i < num_events; i++) {
			if (p==contesti.size()) p=0;
			List<Object> contestiToCheck = contestiCateg.get(contesti.get(p));
			int rand_L = (int)(Math.random() * luoghi.size());
			String luogo = nomi[rand_L].toString();
			String[] categorieLuogo = luoghi.get(luogo);
			boolean check=true;
			for(int y=0;y<categorieLuogo.length;y++) {
				if(!contestiToCheck.contains(categorieLuogo[y])) {
					check=false;
				}
			}
			if(check) {
				graph.addEdge("CL:"+(++archi_CL), new Pair<String>("C_"+contesti.get(p), "L_"+nomi[rand_L].toString()));
				p++;
			}
			else {
				i--;
			}

		}//Livello 1-2

		//-----------DETTAGLI-------------------
		//alcuni dati sul jung.Grafo a scopo di verifica
		//Livello 0 - nodi Persona, al momento caso singolo =1;
		//Livello 1 - nodi Contesto, dati dal file contesti.csv generato in base a quelli forniti dal professore, volendo sono modificabili
		//Livello 2 - nodi Luogo, estratti dal file business_torino.csv
		//Livello 3 - nodi Descrizione, estratti dal file business_torino.csv
		//Archi 0-1 PC - archi da Persona a Contesti, livello full connected
		//Archi 1-2 CL - archi da Contesti a Luoghi, al momento generati casualmente in base al valore num_events, ma da sostituire con le azioni passate degli utenti
		//Archi 2-3 LD - archi da Luoghi a Descrizioni, livello statico estratti dal file business_torino.csv
		System.out.println("Dettagli jung.Grafo");
		System.out.println("Livello 0 - Nodi P: "+ nodi_P);
		System.out.println("Livello 1 - Nodi C: "+ nodi_C);
		System.out.println("Livello 2 - Nodi L: "+ nodi_L);
		System.out.println("Livello 3 - Nodi D: "+ nodi_D);
		System.out.println("Archi 0-1 PC: "+ archi_PC);
		System.out.println("Archi 1-2 CL: "+ archi_CL);
		System.out.println("Archi 2-3 LD: "+ archi_LD);
		System.out.println("---");
	}

	public void Pagerank(){
		//-----------PAGERANK-------------------

		//il pagerank è ancora quello normale finchè non capisco come creare il parametro per poter usare quello con prior
		PageRank ranker = new PageRank(graph , 0.3);
		//PageRankWithPriors<V, E> ranker = new PageRank(graph , 0.3);
		ranker.evaluate();


		System.out.println("Tolerance = " + ranker.getTolerance());
		System.out.println("Dump factor = " + (1.00d - ranker.getAlpha() ) );
		System.out.println("Max iterations = " + ranker.getMaxIterations() );

		for (Object v : graph.getVertices()) {
			if(v.toString().contains("L_"))
				System.out.println(v.toString()+" - Score = " + ranker.getVertexScore(v));
		}
	}
	public void Mostra(){

		//-----------LAYOUT AND SHOW-------------------
    		/*
    		FRLayout l = new FRLayout(g);
    	    VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(layout, new Dimension(1500, 1300));
    	    */

		DAGLayout<String, String> layout = new DAGLayout<String, String>(graph);
		VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(layout, new Dimension(1500,1300));
		vs.getRenderer().setVertexRenderer(new CustomRenderer());
		ShowGraph.Show(vs);
	}

	public void Esporta(String type, String filename) throws IOException {
    	    //fix graphml export
			if(type.equals("GraphML")) ExportGraph.exportAsGraphML(graph, filename);
			else if(type.equals("Net")) ExportGraph.exportAsNet(graph, filename);
			else System.out.println("Export Type Error");
	}
    	   
}
