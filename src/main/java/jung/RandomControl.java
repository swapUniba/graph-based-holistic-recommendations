package jung;

import java.util.Random;

public class RandomControl {

    private static Random RandomnessController;

    public static Random getControl(){
        return RandomnessController;
    }

    public static void setRandom(int seed){
        RandomnessController = new Random(seed);
    }

    public static void setRandom(){
        RandomnessController = new Random();
    }
}
