import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        SparseMultigraph<Integer, String> g = new SparseMultigraph<>();
        for (int i = 0; i < 10; i++)
            g.addVertex(i);


        g.addEdge("First", 1, 2);
        g.addEdge("Second", 2, 3);
        g.addEdge("Third", 8, 4);
        g.addEdge("Fourth", 5, 1);
        g.addEdge("Fifth", 7, 4);
        g.addEdge("Sixth", 3, 4);
        g.addEdge("Seventh", 4, 1);
        g.addEdge("Eigth", 6, 0);

        System.out.println(
                "Numero archi --> " + g.getEdgeCount()
                        + "\nNumero vertici --> " + g.getVertexCount()
                        + "\nEndPoint of First-Edge--> " + g.getEndpoints("First")
                        + "\nInDegree on vertex 6: --> " + g.inDegree(6)
                        + "\nToString --> " + g.toString()
        );


    }

}



