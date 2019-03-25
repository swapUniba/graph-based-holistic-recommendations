# Graph-based Context-aware Holistic Recommendations

This is the code used to perform offline tests with random generation of values for the users preferences.

### Prerequisites

Add the JUNG library dependencies in your pom.xml file.
```
<dependencies>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-graph-impl</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-algorithms</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-api</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-visualization</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-io</artifactId>
            <version>2.1.1</version>
        </dependency>
    </dependencies>
```

## Usage

You can run three types of operations:

* Single
* Compare
* Experiment

### Single

Perform a single execution and generate results.

```
        //Set variables
        String citta = "bari";
        int numero_persone = 1;
        boolean full_connected = true;
        boolean grafo_diretto = true;
        int top_risultati = 10;
        int numero_eventi = 15;
        RandomControl.setRandom(2323);  //random seed


        //SINGLE GRAPH
        List<String> contesto = Arrays.asList("P_0", "C_amici","C_si_attivita","C_cattivo_umore","C_stanco","C_weekend","C_cena");
        Grafo grafo = new Grafo(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Dettagli(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi);
        grafo.Pagerank(top_risultati);
        grafo.Mostra();

```

### Compare

Perform an execution of two graph differing only in one parameter to compare results.

```
        //Set variables
        String citta = "bari";
        int numero_persone = 1;
        boolean full_connected = false;
        boolean grafo_diretto = false;
        int top_risultati = 10;
        int numero_eventi = 15;
        List<String> contesto = Arrays.asList("P_0", "C_famiglia","C_si_attivita","C_cattivo_umore","C_stanco","C_weekend","C_cena");

        //COMPARE GRAPH
        Confronta confronto = new Confronta(citta, numero_persone, full_connected, grafo_diretto, contesto, numero_eventi, top_risultati);
        confronto.setRandom(2323);  //random seed
        confronto.Start("grafo_diretto", false);


```

### Experiment

Performs an exponential number of execution and saved results data to file for statistical analysis.

```
        //Set variables
        int top_risultati = 3;
        int number_context_randomgenerated = 1;
        RandomControl.setRandom(2323);  //random seed
        
        ArrayList<String> cities = new ArrayList<String>();
        cities.add("bari");
        cities.add("torino");

        ArrayList<Integer> num_users = new ArrayList<Integer>();
        num_users.add(1);
        num_users.add(5);


        ArrayList<Boolean> connection_type = new ArrayList<Boolean>();
        connection_type.add(false);
        connection_type.add(true);

        //EXPERIMENT
        Experiment set_exps = new Experiment();

        for (int k_c = 0; k_c < number_context_randomgenerated; k_c++) {
            List<String> contesto = FromFile.RandomContext();
            set_exps.runExperiments("logs.csv", k_c + 1, cities, num_users, connection_type, graph_type, contesto, top_risultati, 50);
        }
        
```

## Built With

* Intellij IDEA
* Maven
* [JUNG](https://github.com/jrtom/jung)

## Authors

* **Marco Mirizio**
* **Federico Impellizzeri**


## Acknowledgments

* Prof. **Cataldo Musto**
