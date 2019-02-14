package jung;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Experiment {
    public Experiment() {

    }

    public static void runExperiments(String fn, ArrayList<String> cities, ArrayList<Integer> num_users, ArrayList<Boolean> connection_type, List<String> contesto, int top_risultati) throws IOException {


        File f = new File(fn);
        if (!f.exists() && !f.isDirectory()) {
            // do something

            try (PrintWriter writer = new PrintWriter(new File(fn))) {
                //TODO - SISTEMARE PERCHÈ HO AGGIUNTO LE COLONNE DELLE PREFERENZE INIZIALI
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
                sb.append("----quì---");
                //SONO AL MASSIMO 12, VISTO CHE SONO 12 TUTTI I POSSIBILI VALORI DEI PARAMETRI CONTESTUALI E LI SCANDISCI TUTTI IN FILA, ricominciando se terminano
                sb.append("alg");
                for (int n = 0; n < top_risultati; n++) {
                    sb.append(",");
                    sb.append("Top" + String.valueOf(n + 1) + "Name");
                    sb.append(',');
                    sb.append("Top" + String.valueOf(n + 1) + "Score");
                }
                sb.append('\n');
                writer.write(sb.toString());

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }

        }

        int number_experiments = 1;
        for (int cc = 0; cc < cities.size(); cc++) {
            String city = cities.get(cc);
            for (int cn = 0; cn < num_users.size(); cn++) {
                Integer num = num_users.get(cn);
                for (int c_cn = 0; c_cn < connection_type.size(); c_cn++) {
                    Boolean type = connection_type.get(c_cn);
//                    System.out.println("------------------------ EXPERIMENT DETAILS ------------------------ ");
//                    System.out.println(contesto);
//                    System.out.println(city + "\t" + String.valueOf(num) + "\t" + String.valueOf(type));
//                    System.out.println("-------------------------------------------------------------------- ");
                    String conf = String.valueOf(number_experiments) + ',';
                    for (int cl = 1; cl < contesto.size(); cl++) {
                        conf += String.valueOf(contesto.get(cl)) + ',';
                    }

                    conf += city + ',' + String.valueOf(num) + ',' + String.valueOf(type) + ',';

                    Grafo g = new Grafo(city, num, type, contesto);
                    HashMap<String, ArrayList<String>> prefs = g.getP();
                    for (Map.Entry<String, ArrayList<String>> item : prefs.entrySet()) {
                        conf += item.getKey() + ',';
                        ArrayList<String> temp_prefs_place = item.getValue();
                        conf += '[';
                        for (int kont = 0; kont < item.getValue().size(); kont++) {
                            if(kont == item.getValue().size() - 1){
                                conf += temp_prefs_place.get(kont)+']'+',';
                            }else{
                                conf += temp_prefs_place.get(kont)+',';
                            }

                        }
                    }

                    HashMap<String, Double> alg1 = g.Pagerank(top_risultati);
                    HashMap<String, Double> alg2 = g.PagerankPriors(top_risultati);
                    String pr = "";
                    pr += "PageRank";
                    for (Map.Entry<String, Double> entry : alg1.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        pr += ',' + key + ',' + value;
                    }
                    addConfiguration(fn, conf + pr);

                    String prp = "";
                    prp += "PageRankPriors";
                    for (Map.Entry<String, Double> entry : alg2.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        prp += ',' + key + ',' + value;
                    }

                    addConfiguration(fn, conf + prp);
                    //g.Mostra();
                    //System.out.println("\n");
                }
            }
        }
    }

    public static void addConfiguration(String fileName,
                                        String str) {
        try {
            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(str + "\n");
            out.close();
        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }
}
