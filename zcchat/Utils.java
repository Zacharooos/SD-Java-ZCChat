package zcchat;

import java.util.List;

public class Utils {
    public static void ClearConsole() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void DisplayList(List<String> lista) {
        for (String elemento : lista) {
            System.out.println(elemento);
        }
    }
}
