package jung;


import java.awt.Dimension;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import com.google.common.base.Function;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.PageRankWithPriors;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import org.apache.commons.csv.writer.CSVConfig;
import org.apache.commons.csv.writer.CSVWriter;

import javax.swing.*;

public class Grafo {

    private DelegateForest<String, String> graph;
    private HashMap<String, String[]> luoghi;
    private ArrayList<String> contesti;
    private List<String> contesto;
    private HashMap<String, ArrayList<String>> preferenze;
    public Grafo(String data, int numero_persone, boolean full_connected, List<String> input_contesto) throws IOException {
        FromFile.SetData(data);
        //Save context user locally
        contesto = input_contesto;

        //Instantiante Graph Boject
        graph = new DelegateForest<>();
        int nodi_P = 0, nodi_C = 0, nodi_L = 0, nodi_D = 0, archi_PC = 0, archi_CL = 0, archi_LD = 0;
        int num_events = 50;

        //Create users
        for (int i = 0; i < numero_persone; i++) {
            graph.addVertex("P_" + i); //Livello 0
            nodi_P++;
        }

        // Read context from file
        contesti = FromFile.getContesti();
        // Get number of context
        nodi_C = contesti.size();


        if (full_connected) {
            // For each user
            for (int i = 0; i < numero_persone; i++) {
                // For each different context parameters (TAKING ALL VALUES)
                for (int y = 0; y < nodi_C; y++) {
                    // Create edge between (Users - Context Parameters)
                    graph.addEdge("PC:" + (++archi_PC), new Pair<>("P_" + i, "C_" + contesti.get(y)));
                }
            }
        } else {
            // TODO: capire come connettere le altre persone
            // For each user
            for (int i = 0; i < numero_persone; i++) {
                // For each different context parameters (TAKING ONLY USER VALUES)
                for (int y = 1; y < contesto.size(); y++) {
                    // Create edge between (Users - User Context Parameters)
                    graph.addEdge("PC:" + (++archi_PC), new Pair<>("P_" + i, contesto.get(y)));
                }
            }
        }//Livello 0-1


        luoghi = FromFile.getPlacesNew();
        HashMap<String, List<Object>> contestiCateg = FromFile.getContestiCategorizzati();
        //alcuni luoghi hanno lo stesso nome, al momento vengono accorpati (19 luoghi, 852 in totale 833 usati)
        Object[] nomi = luoghi.keySet().toArray();
        nodi_L = nomi.length;
        nodi_D = FromFile.getCategorie().size();

        for (int i = 0; i < nomi.length; i++) {
            String posto = nomi[i].toString();
            for (int y = 0; y < luoghi.get(posto).length; y++) {
                graph.addEdge("LD:" + (++archi_LD), new Pair<>("L_" + posto, "D_" + luoghi.get(posto)[y]));
            }
        }//Livello 2-3



        HashMap<String, ArrayList<String>> pref = new HashMap<>();
        //Per adesso, è spalmato equamente sui contesti
        //Prendo i contesti di fila, prendo un luogo a caso, verifico se il luogo ha tutte le categorie adatte per quel contesto, se si linko altrimenti riprovo
        int p = 0;
        for (int i = 0; i < num_events; i++) {
            if (p == contesti.size()) p = 0;
            List<Object> contestiToCheck = contestiCateg.get(contesti.get(p));
            int rand_L = (int) (Math.random() * luoghi.size());
            String luogo = nomi[rand_L].toString();
            String[] categorieLuogo = luoghi.get(luogo);
            boolean check = true;
            for (int y = 0; y < categorieLuogo.length; y++) {
                if (!contestiToCheck.contains(categorieLuogo[y])) {
                    check = false;
                }
            }

            //System.out.println("Contesto:" +contesti.get(p) + " - Luogo: " + nomi[rand_L]+ " - Contesti Luogo: "+ Arrays.toString(categorieLuogo) + " - Check: " + check);
            if (check) {
                graph.addEdge("CL:" + (++archi_CL), new Pair<>("C_" + contesti.get(p), "L_" + nomi[rand_L].toString()));
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(nomi[rand_L].toString());
                if(pref.containsKey(contesti.get(p))){
                    ArrayList<String> t = pref.get(contesti.get(p));
                    t.add(nomi[rand_L].toString());
                    pref.put(contesti.get(p), t);
                }else{
                    pref.put(contesti.get(p), temp);
                }


                p++;
            } else {
                i--;
            }


        }//Livello 1-2

        preferenze = pref;

        //-----------DETTAGLI-------------------
        //alcuni dati sul jung.Grafo a scopo di verifica
        //Livello 0 - nodi Persona, al momento caso singolo =1;
        //Livello 1 - nodi Contesto, dati dal file contesti.csv generato in base a quelli forniti dal professore, volendo sono modificabili
        //Livello 2 - nodi Luogo, estratti dal file business_torino.csv
        //Livello 3 - nodi Descrizione, estratti dal file business_torino.csv
        //Archi 0-1 PC - archi da Persona a Contesti, livello full connected (TOTEST NOT FULL CONNECTED?)
        //Archi 1-2 CL - archi da Contesti a Luoghi, al momento generati casualmente in base al valore num_events, ma da sostituire con le azioni passate degli utenti
        //Archi 2-3 LD - archi da Luoghi a Descrizioni, livello statico estratti dal file business_torino.csv
//		System.out.println("Dettagli Grafo");
//		System.out.println("Livello 0 - Nodi P: "+ nodi_P);
//		System.out.println("Livello 1 - Nodi C: "+ nodi_C);
//		System.out.println("Livello 2 - Nodi L: "+ nodi_L);
//		System.out.println("Livello 3 - Nodi D: "+ nodi_D);
//		System.out.println("Archi 0-1 PC: "+ archi_PC);
//		System.out.println("Archi 1-2 CL: "+ archi_CL);
//		System.out.println("Archi 2-3 LD: "+ archi_LD);
//		System.out.println("---");
    }

    public HashMap<String, ArrayList<String>> getP() {
        return preferenze;
    }

    public HashMap<String, Double> Pagerank(int top_results) {

        HashMap<String, Double> result_pr = new HashMap<String, Double>();

        PageRank ranker = new PageRank(graph, 0.3);
        ranker.evaluate();

        System.out.println("Dettagli Pagerank");
        System.out.println("-------------------------------------------------------------------- ");
        System.out.println("Tolerance = " + ranker.getTolerance());
        System.out.println("Dump factor = " + (1.00d - ranker.getAlpha()));
        System.out.println("Max iterations = " + ranker.getMaxIterations());
        System.out.println("-------------------------------------------------------------------- ");
        HashMap<String, Double> map = new HashMap();
        for (Object v : graph.getVertices()) {
            if (v.toString().contains("L_") && !ranker.getVertexScore(v).toString().equals("0.0")) {
                map.put(v.toString(), (Double) ranker.getVertexScore(v));
            }
        }

        Object[] obj = map.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).toArray();

        for (int i = 0; i < top_results; i++) {
            String nome = obj[i].toString();
            String score = nome.substring(nome.indexOf("=") + 1);
            nome = nome.substring(nome.indexOf("_") + 1, nome.indexOf("="));
            result_pr.put(nome, Double.valueOf(score));
            String stamp = i + 1 + " - " + nome + " - Score: " + score + " [";
            String[] cats = luoghi.get(nome);
            for (String s : cats
            ) {
                stamp = stamp.concat(s + ",");
            }
            stamp = stamp.substring(0, stamp.length() - 1).concat("]");
            System.out.println(stamp);
        }

        System.out.println("-------------------------------------------------------------------- ");

        return result_pr;
    }

    public HashMap<String, Double> PagerankPriors(int top_results) {

        HashMap<String, Double> result_prp = new HashMap<String, Double>();

        Function f = ((Object i) -> {
            if (contesto.contains(i)) return 1.0;
            else return 0.0;
        });

        PageRankWithPriors ranker = new PageRankWithPriors(graph, f, 0.3);
        ranker.evaluate();

        System.out.println("Dettagli PagerankWithPriors");
        System.out.println("-------------------------------------------------------------------- ");
        System.out.println("Tolerance = " + ranker.getTolerance());
        System.out.println("Dump factor = " + (1.00d - ranker.getAlpha()));
        System.out.println("Max iterations = " + ranker.getMaxIterations());
        System.out.println("-------------------------------------------------------------------- ");
        //Magari dopo i risultati rifiltrare per categoria per ottenere risultati completamente coerenti
        System.out.println("Contesto preso in considerazione: " + contesto);
        System.out.println("-------------------------------------------------------------------- ");
        HashMap<String, Double> map = new HashMap();
        for (Object v : graph.getVertices()) {
            if (v.toString().contains("L_") && !ranker.getVertexScore(v).toString().equals("0.0")) {
                map.put(v.toString(), (Double) ranker.getVertexScore(v));
            }
        }

        Object[] obj = map.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).toArray();

        for (int i = 0; i < top_results; i++) {
            String nome = obj[i].toString();
            String score = nome.substring(nome.indexOf("=") + 1);
            nome = nome.substring(nome.indexOf("_") + 1, nome.indexOf("="));
            result_prp.put(nome, Double.valueOf(score));
            String stamp = i + 1 + " - " + nome + " - Score: " + score + " [";
            String[] cats = luoghi.get(nome);
            for (String s : cats
            ) {
                stamp = stamp.concat(s + ",");
            }
            stamp = stamp.substring(0, stamp.length() - 1).concat("]");
            System.out.println(stamp);
        }
        System.out.println("-------------------------------------------------------------------- ");
        return result_prp;
    }


    public void Mostra() {

        DAGLayout<String, String> layout = new DAGLayout<String, String>(graph);
        layout.setRoot("P_0");
        VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(layout, new Dimension(1000, 800));
        vs.getRenderer().setVertexRenderer(new CustomRenderer());
        JFrame frame = new JFrame();
        frame.getContentPane().add(vs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void Esporta(String type, String filename) throws IOException {

        //fix graphml export
        if (type.equals("GraphML")) ExportGraph.exportAsGraphML(graph, filename);
        else if (type.equals("Net")) ExportGraph.exportAsNet(graph, filename);
        else System.out.println("Export Type Error");
    }


    public static void addConfiguration(String fileName,
                                        String str) {
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(str + "\n");
            out.close();
        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }
}
