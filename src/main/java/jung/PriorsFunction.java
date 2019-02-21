package jung;

import com.google.common.base.Function;

import java.util.List;

public class PriorsFunction {

    public static Function getF1(List contesto){
    Function f1 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else return 0.0;
    });

    return f1;
}
    public static Function getF2(List contesto){
    Function f2 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else if (i.toString().startsWith("C_")) return 0.3;
        else return 0.0;
    });
        return f2;
}
    public static Function getF3(List contesto, List pref_c){
    Function f3 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else if (pref_c.contains(i.toString().substring(2))) return 0.5;
        else return 0.0;
    });
        return f3;
}
    public static Function getF4(List contesto, List pref_c){
    Function f4 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else if (i.toString().startsWith("C_")) return 0.3;
        else if (pref_c.contains(i.toString().substring(2))) return 0.5;
        else return 0.0;
    });
        return f4;
    }
}
