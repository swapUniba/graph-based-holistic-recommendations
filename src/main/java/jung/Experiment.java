package jung;

import javafx.collections.transformation.SortedList;

import javax.print.DocFlavor;
import java.io.*;
import java.util.*;

public class Experiment {
    public Experiment() {

    }

    public static void runExperiments(String fn, ArrayList<String> cities, ArrayList<Integer> num_users, ArrayList<Boolean> connection_type,
                                      List<String> contesto, int top_risultati, int number_events) throws IOException {

        File f = new File(fn);
        if (f.exists()) {
            f.delete();
        }
        if (!f.isDirectory()) {
            // do something

            try (PrintWriter writer = new PrintWriter(new File(fn))) {
                //TODO - SISTEMARE PERCHÈ HO AGGIUNTO LE COLONNE DELLE PREFERENZE INIZIALI
                StringBuilder sb = new StringBuilder();
                sb.append("experiment");
                sb.append(",");
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
                sb.append("alg");

                //SONO AL MASSIMO 12, VISTO CHE SONO 12 TUTTI I POSSIBILI VALORI DEI PARAMETRI CONTESTUALI E LI SCANDISCI TUTTI IN FILA, ricominciando se terminano

                int number_context_available = FromFile.getContesti().size();
                int howmany_columns = number_events;
                if (number_events > number_context_available) {
                    howmany_columns = number_context_available;
                }
                for (int col = 0; col < howmany_columns; col++) {
                    sb.append(",");
                    sb.append("available_value" + String.valueOf(col + 1));
                    sb.append(",");
                    sb.append("preferences" + String.valueOf(col + 1));
                }

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


                    System.out.println("--------------------------------- EXPERIMENT #"+String.valueOf(number_experiments)+" DETAILS\n");
                    System.out.println(contesto);
                    System.out.println(city + "\t" + String.valueOf(num) + "\t" + String.valueOf(type));

                    String conf = String.valueOf(number_experiments) + ',';
                    for (int cl = 1; cl < contesto.size(); cl++) {
                        conf += String.valueOf(contesto.get(cl)) + ',';
                    }

                    conf += city + ',' + String.valueOf(num) + ',' + String.valueOf(type) + ',';

                    Grafo g = new Grafo(city, num, type, contesto, number_events);
                    HashMap<String, ArrayList<String>> prefs = g.getP();
                    String preferences = "";


                    System.out.println("\n_________________\nPREFERENCES A PRIORI");
                    for (Map.Entry<String, ArrayList<String>> item : prefs.entrySet()) {
                        System.out.println("\n_________________\nKEY: "+item.getKey());
                        preferences += item.getKey() + ',';
                        ArrayList<String> temp_prefs_place = item.getValue();
                        System.out.println(temp_prefs_place);
                        Collections.sort(temp_prefs_place);
                        System.out.println(temp_prefs_place);
                        preferences += '[';
                        for (int kont = 0; kont < item.getValue().size(); kont++) {
                            if (kont == item.getValue().size() - 1) {
                                preferences += temp_prefs_place.get(kont) + ']'+',';
                            } else {
                                preferences += temp_prefs_place.get(kont) + ";";
                            }

                        }
                    }

                    HashMap<String, Double> alg1 = g.Pagerank(top_risultati);
                    HashMap<String, Double> alg2 = g.PagerankPriors(top_risultati);
                    //SOME STUFFS WITH COMMA IN CSV - so it's deleted the last character of preferences which is a comma
                    //Commenting this line means that you find double comma write on file
                    System.out.println("\n--------------------------------- END EXPERIMENT\n");
                    preferences = preferences.substring(0,preferences.length()-1);



                    String pr = "";
                    pr += "PageRank"+','+preferences;
                    for (Map.Entry<String, Double> entry : alg1.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        pr += ',' + key + ',' + value;
                    }
                    addConfiguration(fn, conf + pr);

                    String prp = "";
                    prp += "PageRankPriors"+','+preferences;
                    for (Map.Entry<String, Double> entry : alg2.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        prp += ',' + key + ',' + value;
                    }

                    addConfiguration(fn, conf + prp);
                    //g.Mostra();
                    //System.out.println("\n");
                    number_experiments++;
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
