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
        try (PrintWriter writer = new PrintWriter(new File("result_humgraph.csv"))) {

            StringBuilder sb = new StringBuilder();
            sb.append("company");
            sb.append(",");
            sb.append("type");
            sb.append(',');
            sb.append("mood");
            sb.append(',');
            sb.append("health");
            sb.append(',');
            sb.append("settimana");
            sb.append(',');
            sb.append("city");
            sb.append(",");
            sb.append("cardinality user");
            sb.append(",");
            sb.append("full connection");
            sb.append(",");
            sb.append("top_number");
            sb.append(",");
            sb.append("alg");
            for (int n = 0; n < top_risultati; n++) {
                sb.append(",");
                sb.append("Top" + String.valueOf(n + 1) + "Name");
                sb.append(',');
                sb.append("Top" + String.valueOf(n + 1) + "Score");
            }
            sb.append('\n');
            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        for (int cc = 0; cc < cities.size(); cc++) {
            String city = cities.get(cc);
            for (int cn = 0; cn < num_users.size(); cn++) {
                Integer num = num_users.get(cn);
                for (int c_cn = 0; c_cn < connection_type.size(); c_cn++) {
                    Boolean type = connection_type.get(c_cn);
                    System.out.println("------------------------ EXPERIMENT DETAILS ------------------------ ");
                    System.out.println(contesto);
                    System.out.println(city + "\t" + String.valueOf(num) + "\t" + String.valueOf(type));
                    System.out.println("-------------------------------------------------------------------- ");
                    String conf = "";
                    for (int cl = 1; cl < contesto.size(); cl++) {
                        conf += String.valueOf(contesto.get(cl)) + ',';
                    }

                    conf += city + ',' + String.valueOf(num) + ',' + String.valueOf(type) + ',' + String.valueOf(top_risultati)+',';

                    Grafo g = new Grafo(city, num, type, contesto);
                    HashMap<String, Double> alg1 = g.Pagerank(top_risultati);
                    HashMap<String, Double> alg2 = g.PagerankPriors(top_risultati);
                    String pr = "";
                    pr += "PageRank";
                    for (Map.Entry<String, Double> entry : alg1.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        pr += ',' + key + ',' + value;
                    }
                    g.addConfiguration("result_humgraph.csv", conf + pr);

                    String prp = "";
                    prp += "PageRankPriors";
                    for (Map.Entry<String, Double> entry : alg2.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        prp += ',' + key + ',' + value;
                    }

                    g.addConfiguration("result_humgraph.csv", conf + prp);
                    System.out.println("\n");
                }
            }
        }

    }

}
