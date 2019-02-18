package jung;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

/*
        //VARIABLES SINGLE
        String citta = "torino"; //"bari"
        int numero_persone = 5;
        boolean full_connected = false;
        boolean grafo_diretto = false;
        int top_risultati = 10;
        int numero_eventi = 10;
        //RandomControl.setRandom(); //totalmente random
        RandomControl.setRandom(2323);  //mantiene lo stesso "random" per confrontare meglio alcuni casi particolari


        //SINGLE GRAPH
        //List<String> contesto = FromFile.RandomContext();
        List<String> contesto = Arrays.asList("P_0", "C_famiglia","C_si_attivita","C_cattivo_umore","C_stanco","C_weekend");
        Grafo grafo = new Grafo(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Dettagli(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Pagerank(top_risultati);
        grafo.PagerankPriors(top_risultati);
        //grafo.Mostra();
*/
/*
        //VARIABLES CONFRONT
        String citta = "torino"; //"bari"
        int numero_persone = 5;
        boolean full_connected = false;
        boolean grafo_diretto = false;
        int top_risultati = 10;
        int numero_eventi = 10;
        List<String> contesto = Arrays.asList("P_0", "C_famiglia","C_si_attivita","C_cattivo_umore","C_stanco","C_weekend");

        //CONFRONT GRAPH
        Confronta confronto = new Confronta(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi, top_risultati);
        confronto.setRandom(2323);  //non settarlo per true random, può essere utile per confrontare due run senza cambiare niente
        confronto.setContesto(Arrays.asList("P_0", "C_famiglia","C_cattivo_umore","C_stanco"));   //test per vedere che accade con contesti incompleti
        confronto.Start("contesto", false);

        //confronto.Start("grafo_diretto", false); //test per vedere che accade con stesso contesto ma grafo diretto, non serve settare niente per i valori boolean
*/


        //VARIABLES EXPERIMENT
        int top_risultati = 3;
        int number_context_randomgenerated = 1;
        RandomControl.setRandom(); //totalmente random
        //RandomControl.setRandom(2323);  //mantiene lo stesso "random" per confrontare meglio alcuni casi particolari

        ArrayList<String> cities = new ArrayList<String>();
        cities.add("bari");
        cities.add("torino");

        ArrayList<Integer> num_users = new ArrayList<Integer>();
        num_users.add(1);
        num_users.add(5);
        //num_users.add(100);
//        for (int i = 100; i <= 1000; i = i + 100) {
//            num_users.add(i);
//        }

        ArrayList<Boolean> connection_type = new ArrayList<Boolean>();
        connection_type.add(false);
        connection_type.add(true);

        //penso che poi sarà sempre false e si potrà togliere
        ArrayList<Boolean> graph_type = new ArrayList<Boolean>();
        graph_type.add(false);
        graph_type.add(true);


        //EXPERIMENT
        Experiment set_exps = new Experiment();
        //set_exps.runExperiments("logs.csv", cities, num_users, connection_type, contesto, top_risultati, 50);

        for (int k_c = 0; k_c < number_context_randomgenerated; k_c++) {
            List<String> contesto = FromFile.RandomContext();
            set_exps.runExperiments("logs.csv", k_c + 1, cities, num_users, connection_type, graph_type, contesto, top_risultati, 50);
        }




    }
}
