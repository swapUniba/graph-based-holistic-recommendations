package jung;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main( String[] args ) throws IOException {

        Grafo grafo = new Grafo("torino");
        //Grafo grafo = new Grafo("bari");




        //TOTEST WITH AND WITHOUT P_
        int top_risultati =5;
        //grafo.Pagerank(top_risultati);
        //List<String> contesto = Arrays.asList("P_", "C_bambini","C_colazione","C_cattivo_umore","C_non_buona_salute","C_weekend");
        //grafo.PagerankPriors(contesto, top_risultati);
        grafo.PagerankPriors(FromFile.RandomContext(), top_risultati);

        grafo.Mostra();

    }
}
