package jung;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        //TOTEST WITH AND WITHOUT P_
        String citta = "torino"; //"bari"
        //int numero_persone = 100;
        boolean full_connected = false;
        int top_risultati = 5;
        //List<String> contesto = Arrays.asList("P_0", "C_bambini","C_colazione","C_cattivo_umore","C_non_buona_salute","C_weekend");
        List<String> contesto = FromFile.RandomContext();
        //Grafo grafo = new Grafo(citta, numero_persone, full_connected, contesto);
        //grafo.Pagerank(top_risultati);
        //grafo.PagerankPriors(top_risultati);
        //grafo.Mostra();

        ArrayList<String> cities = new ArrayList<String>();
        cities.add("bari");
        cities.add("torino");
        ArrayList<Integer> num_users = new ArrayList<Integer>();
        num_users.add(1);
        num_users.add(100);
        num_users.add(500);
        num_users.add(1000);
        ArrayList<Boolean> connection_type = new ArrayList<Boolean>();
        connection_type.add(false);
        connection_type.add(true);

        Experiment set_exps = new Experiment();
        set_exps.runExperiments("result_hum.csv", cities, num_users, connection_type, contesto, top_risultati);

    }

}
