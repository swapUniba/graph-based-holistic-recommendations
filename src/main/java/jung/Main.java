package jung;

import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException {

        Grafo grafo = new Grafo("torino");
        //Grafo grafo = new Grafo("bari");

        grafo.Mostra();
        grafo.Pagerank();

    }
}
