package Jung.test;

import javax.swing.JFrame;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public class ShowGraph {

	public static void Show(VisualizationViewer<String, String> vs) {
	    JFrame frame = new JFrame();
	    frame.getContentPane().add(vs);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	  }
}
