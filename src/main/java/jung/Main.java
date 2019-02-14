package jung;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        int number_context_randomgenerated = 500;
        //TOTEST WITH AND WITHOUT P_
        String citta = "torino"; //"bari"
        //int numero_persone = 100;
        boolean full_connected = false;
        int top_risultati = 5;
        //List<String> contesto = Arrays.asList("P_0", "C_bambini","C_colazione","C_cattivo_umore","C_non_buona_salute","C_weekend");
        //List<String> contesto = FromFile.RandomContext();
        //Grafo grafo = new Grafo(citta, numero_persone, full_connected, contesto);
        //grafo.Pagerank(top_risultati);
        //grafo.PagerankPriors(top_risultati);
        //grafo.Mostra();

        ArrayList<String> cities = new ArrayList<String>();
        cities.add("bari");
        cities.add("torino");
        ArrayList<Integer> num_users = new ArrayList<Integer>();
        for (int i = 1; i <= 1000; i = i + 200) {
            num_users.add(i);
        }
        ArrayList<Boolean> connection_type = new ArrayList<Boolean>();
        connection_type.add(false);
        connection_type.add(true);

        Experiment set_exps = new Experiment();
        for (int k_c = 0; k_c < number_context_randomgenerated; k_c++){
            List<String> contesto = FromFile.RandomContext();
            set_exps.runExperiments("result_hum1.csv", cities, num_users, connection_type, contesto, top_risultati);
        }

    }

}
