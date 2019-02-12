package jung;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main( String[] args ) throws IOException {

        //TOTEST WITH AND WITHOUT P_
        String citta = "torino"; //"bari"
        int numero_persone = 5;
        boolean full_connected = false;
        int top_risultati = 5;
        //List<String> contesto = Arrays.asList("P_0", "C_bambini","C_colazione","C_cattivo_umore","C_non_buona_salute","C_weekend");
        List<String> contesto = FromFile.RandomContext();

        Grafo grafo = new Grafo(citta, numero_persone, full_connected, contesto);
        //grafo.Pagerank(top_risultati);
        grafo.PagerankPriors(top_risultati);

        grafo.Mostra();
    }
}
