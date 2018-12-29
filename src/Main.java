import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.PolarPoint;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;

public class Main {

    public static void main(String[] args) {
        SparseMultigraph<Integer, String> g = new SparseMultigraph<>();
        int num_v = 10;
        int[] vertici = new int[10];

        for (int i = 0; i < num_v; i++) {
            g.addVertex(i+1);
            vertici[i] = i;
        }

        g.addEdge("1-2", 1, 2);
        g.addEdge("1-3", 1, 3);
        g.addEdge("1-4", 1, 4);
        g.addEdge("2-5", 2, 5);
        g.addEdge("2-6", 2, 6);
        g.addEdge("2-7", 2, 7);
        g.addEdge("3-8", 3, 8);
        g.addEdge("3-9", 3, 9);
        g.addEdge("3-10", 3, 10);

        System.out.println(
                "Numero archi --> " + g.getEdgeCount()
                        + "\nNumero vertici --> " + g.getVertexCount()
                        + "\nEndPoint of First-Edge--> " + g.getEndpoints("First")
                        + "\nInDegree on vertex 6: --> " + g.inDegree(6)
                        + "\nToString --> " + g.toString()
        );

        // The Layout<V, E> is parameterized by the vertex and edge types
        Layout<Integer, String> layout = new CircleLayout(g);
        //Layout<Integer, String> layout = new BalloonLayout<Integer, String>(g);

        layout.setSize(new Dimension(600, 600)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types -- THIS IS THE CANVAS
        BasicVisualizationServer<Integer, String> vv =
                new BasicVisualizationServer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(600, 600)); //Sets the viewing area size

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());

        JFrame f = new JFrame("Simple Graph View");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(vv);
        f.pack();
        f.setVisible(true);
    }

}



