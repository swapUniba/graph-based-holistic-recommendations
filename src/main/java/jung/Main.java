package jung;

import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException {

        String data = "torino";
        //String data = "bari";
        FromFile.SetData(data);

        Grafo grafo = new Grafo();
        grafo.Mostra();
        grafo.Pagerank();

    }
}
