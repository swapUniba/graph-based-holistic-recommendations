package jung;

import java.io.IOException;
import java.util.List;

public class Confronta {
    String citta;
    int numero_persone;
    boolean full_connected;
    boolean grafo_diretto;
    int top_risultati;
    int numero_eventi;
    List contesto;
    List new_contesto;
    int seed;
    boolean seed_set=false;
    int new_numero_persone;
    int new_numero_eventi;

    public Confronta(String citta, int numero_persone, boolean full_connected, boolean grafo_diretto, List<String> contesto, int numero_eventi, int top_risultati){

        this.citta=citta;
        this.numero_persone=numero_persone;
        this.full_connected=full_connected;
        this.grafo_diretto=grafo_diretto;
        this.contesto=contesto;
        this.numero_eventi=numero_eventi;
        this.top_risultati=top_risultati;
        RandomControl.setRandom();
    }

    public void setRandom(int seed){
        seed_set=true;
        RandomControl.setRandom(seed);
    }

    public void Start(String toConfront, boolean show_graph) throws IOException {
        if(seed_set) setRandom(seed);
        Grafo grafo = new Grafo(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Dettagli(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Pagerank(top_risultati);
        grafo.PagerankPriors(top_risultati);
        if(show_graph) grafo.Mostra();


            switch (toConfront) {
                case "citta": {
                    if (citta.equals("bari")) citta = "torino";
                    else citta = "bari";
                    break;
                }

                case "numero_persone": {
                    numero_persone = new_numero_persone;
                    break;
                }

                case "full_connected": {
                    if (full_connected) full_connected = false;
                    else full_connected = true;
                    break;
                }

                case "grafo_diretto": {
                    if (grafo_diretto) grafo_diretto = false;
                    else grafo_diretto = true;
                    break;
                }

                case "contesto": {
                    contesto = new_contesto;
                    break;
                }

                case "numero_eventi": {
                    numero_eventi = new_numero_eventi;
                    break;
                }
            }


        if(seed_set) setRandom(seed); //risettiamo lo stesso random se serve che vada mantenuto

        try {
        grafo = new Grafo(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Dettagli(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Pagerank(top_risultati);
        grafo.PagerankPriors(top_risultati);
        if(show_graph) grafo.Mostra();

        } catch (NullPointerException e){
            System.err.println("Settare correttamente i valori per il confronto");
            return;
        }

    }

    public void setNumero_persone(int numero){
        new_numero_persone=numero;
    }

    public void setNumero_eventi(int numero){
        new_numero_eventi=numero;
    }
    public void setContesto(List contesto){
        new_contesto=contesto;
    }
}
