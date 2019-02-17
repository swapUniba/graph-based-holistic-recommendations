package jung;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        int number_context_randomgenerated = 1;
        //TOTEST WITH AND WITHOUT P_
        String citta = "torino"; //"bari"
        //int numero_persone = 100;
        boolean full_connected = false;
        int top_risultati = 3;
        //List<String> contesto = Arrays.asList("P_0", "C_famiglia","C_colazione","C_cattivo_umore","C_non_buona_salute","C_weekend");
        //List<String> contesto = FromFile.RandomContext();
        //Grafo grafo = new Grafo(citta, numero_persone, full_connected, contesto);
        //grafo.Pagerank(top_risultati);
        //grafo.PagerankPriors(top_risultati);
        //grafo.Mostra();

        ArrayList<String> cities = new ArrayList<String>();
        //cities.add("bari");
        cities.add("torino");
        ArrayList<Integer> num_users = new ArrayList<Integer>();
        num_users.add(1);
        //num_users.add(10);
        //num_users.add(100);
//        for (int i = 100; i <= 1000; i = i + 100) {
//            num_users.add(i);
//        }
        ArrayList<Boolean> connection_type = new ArrayList<Boolean>();
        //connection_type.add(false);
        connection_type.add(true);

        //penso che poi sarà sempre false e si potrà togliere
        ArrayList<Boolean> graph_type = new ArrayList<Boolean>();
        graph_type.add(false);
        graph_type.add(true);

        Experiment set_exps = new Experiment();
        //set_exps.runExperiments("logs.csv", cities, num_users, connection_type, contesto, top_risultati, 50);

        for (int k_c = 0; k_c < number_context_randomgenerated; k_c++) {
            List<String> contesto = FromFile.RandomContext();
            set_exps.runExperiments("logs.csv", k_c + 1, cities, num_users, connection_type, graph_type, contesto, top_risultati, 50);
        }

    }

}
