/*
Autores:
Diego Quiros Brenes
Carlos Chacon Vargas
Alessandro Fazio Perez
Bryan Sanchez Brenes
 */
package proyectoparadigmas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import parser.regla;
import parser.Parser;

/**
 *
 * @author Carlos
 */
public class RunExpresion {

    private ArrayList<String> variables = new ArrayList<>();

    private String expresionPrincipal;

    private ArrayList<String> simbolos = new ArrayList<>(); //lista de simbolos

    private ArrayList<String> markers = new ArrayList<>();

    private String expresionArchivo = "";

    Parser parser;

    public void setExpresionArchivo(String expresionArchivo) {
        this.expresionArchivo = expresionArchivo;
    }

    public ArrayList<String> getVariables() {
        return variables;
    }

    public String getExpresion() {
        return expresionPrincipal;
    }

    public ArrayList<String> getMarkers() {
        return markers;
    }

    public ArrayList<String> getSimbolos() {
        return simbolos;
    }

    public String getExpresionArchivo() {
        return expresionArchivo;
    }

    public void setVariables(ArrayList<String> variables) {
        this.variables = variables;
    }

    public void setExpresion(String expresion) {
        this.expresionPrincipal = expresion;
    }

    public void setSimbolos(ArrayList<String> simbolos) {
        this.simbolos = simbolos;
    }

    public void setMarkers(ArrayList<String> markers) {
        this.markers = markers;
    }

    public RunExpresion(String expresion, ArrayList<String> simbolos, Parser parser) {
        this.expresionPrincipal = expresion;
        this.simbolos = simbolos;
        this.parser = parser;
    }

    public RunExpresion() {

    }

    /*
    p1. Fx -> xF
    p2. xF -> x# (p4)
    p3. x -> Fx
    p4. Gx# -> #x (p4)
    p5. #G -> ^.
    p6. Gxy -> yGx (p4)
    p7. x -> Gx (p4)
    p8. # -> ^.

    1. Fx -> xF
    2. xF -> x#.
    3. x -> Fx
     */
    public ArrayList<String> datosQuemados() {

        ArrayList<regla> reglas = new ArrayList();

        regla r1 = new regla("p1", "Fx", "xF", "");
        regla r2 = new regla("p2", "xF", "x#", "(p4)");
        regla r3 = new regla("p3", "x", "Fx", "");
        regla r4 = new regla("p4", "Gx#", "#x", "(p4)");
        regla r5 = new regla("p5", "#G", ".", "");
        regla r6 = new regla("p6", "Gxy", "yGx", "(p4)");
        regla r7 = new regla("p7", "x", "Gx", "(p4)");
        regla r8 = new regla("p8", "#", ".", "");

        ArrayList<String> mar = new ArrayList();
        mar.add("F");
        mar.add("G");
        mar.add("#");
        mar.add("^");

        // extraerSimbolos("FaabcGabbbbaaacc", mar);
        variables.add("x");
        variables.add("y");
        variables.add("z");
        variables.add("w");

        simbolos.add("a");
        simbolos.add("b");
        simbolos.add("c");

        this.setMarkers(mar);

        r2.setFin(true);

        reglas.add(r1);
        reglas.add(r2);
        reglas.add(r3);
        reglas.add(r4);
        reglas.add(r5);
        reglas.add(r6);
        reglas.add(r7);
        reglas.add(r8);

        return leerExpresion(reglas, "abcabcabcabcabcaaaaabbbbccccccbcbcbccacacaabababacacacaccacacacacacacacacacacacacababababacabbbacccabbbacabbacb");

    }

    public ArrayList<String> leerExpresion(ArrayList<regla> reglas, String expresion) {
        /* p1. Fx -> xF
       p2. xF -> x# (p4)
       p3. x -> Fx
       p4. Gx# -> #x (p4)
       p5. #G -> ^.
       p6. Gxy -> yGx (p4)
       p7. x -> Gx (p4)
       p8. # -> ^.
        
        abc     Fabc    aFbc    abFc
        abcF    abc#    Gabc#   bGac#
        bcGa#   bc#a    Gbc#a   cGb#a
        c#ba    Gc#ba   #cba    #Gcba  cba
         */
        int estado = 0;
        String aux = "";
        ArrayList<String> resultado = new ArrayList();
        while (estado < reglas.size()) {
            aux = contiene(expresion, reglas.get(estado).getPrimeraRegla());
            if (!aux.equals("")) {

                expresion = expresion.replaceFirst(aux, sRegla(aux, reglas.get(estado).getTrancision()));
                resultado.add(expresion);

                if (expresion.contains(".")) {
                    expresion = expresion.replace(".", "");
                    resultado.add(expresion);
                    return resultado;
                }

                if (!reglas.get(estado).getSalto().equals("")) {
                    estado = cambiarEstado(estado, reglas) - 1;
                    continue;
                }
                estado = 0;
                continue;
            }

            estado++;

            if (estado == reglas.size()) {
                estado = 0;
            }

        }

        return resultado;

    }

    public String removeDuplicate(char str[], int n) {
        int index = 0;

        for (int i = 0; i < n; i++) {

            int j;
            for (j = 0; j < i; j++) {
                if (str[i] == str[j]) {
                    break;
                }
            }

            if (j == i) {
                str[index++] = str[i];
            }
        }
        return String.valueOf(Arrays.copyOf(str, index));
    }

    public String sRegla(String pR, String sR) {
        /* sR -> yGx --> aGb  zyGx <-- Gabc  a:x:0  b:y:1 c:z:2
           pR -> Gab --> bGa                   1      0    ...             */

        String simUtilizados = extraerSimbolos(pR, false);
        String varUtilizadas = extraerSimbolos(sR, true);
        ArrayList<String> repetidos = new ArrayList();
        ArrayList<String> sim = new ArrayList();
        int contSim = 0;

        if (!simUtilizados.equals("")) {
            for (int i = 0; i < simUtilizados.length(); i++) {
                sim.add(String.valueOf(simUtilizados.charAt(i)));
                sim.add(variables.get(i));
            }

            if (varUtilizadas.length() > 1) {
                simUtilizados = verficarPosicionVariable(varUtilizadas, sim);
            }

            for (int i = 0; i < sR.length(); i++) {
                String aux = String.valueOf(sR.charAt(i));
                if (isVar(aux, repetidos)) {
                    sR = sR.replaceAll(aux, String.valueOf(simUtilizados.charAt(contSim)));
                    contSim++;
                    repetidos.add(aux);
                }
            }

            return sR;
        } else {
            return sR;
        }

    }

    public String verficarPosicionVariable(String varUtilizadas, ArrayList<String> sim) {
        /* a x b y   Gab --> bGa  Gxy --> yGx  
        
        Gabc --> abc --> Gxyz -->   bx ay cz  --> yzGx --> acGb*/

        String r = "";
        int j = 0;

        /*  ax  by    yx  -> ab */
        for (int i = 0; i < varUtilizadas.length(); i++) {

            int index = sim.indexOf((String) String.valueOf(varUtilizadas.charAt(i)));

            r = r + sim.get(index - 1);

            j++;

        }

        return r;
    }

    public String contiene(String expresion, String pRegla) {

        int contSim = 0;
        String pR = pRegla;
        ArrayList<String> repetidos = new ArrayList();
        String simUtilizados = extraerSimbolos(expresion, true);
        ArrayList<String> permuta = permutations(simUtilizados, simUtilizados.length());

        if (!findMarker(expresion) && findMarker(pRegla)) {
            return "";
        }

        for (int j = -1; j < permuta.size(); j++) {
            if (j == -1) {
                simUtilizados = permuta.get(permuta.indexOf(simUtilizados));
                permuta.remove(permuta.indexOf(simUtilizados));
            } else {
                simUtilizados = permuta.get(j);
            }
            pRegla = pR;
            contSim = 0;
            repetidos.clear();
            if (j == -1 && extraerSimbolos(pRegla, false).length() > 1) {
                String aux2 = verificarValRepetidos(expresion, pRegla, simUtilizados);
                if (!aux2.equals("")) {
                    return aux2;
                }
            }
            for (int i = 0; i < pRegla.length(); i++) {
                String aux = String.valueOf(pRegla.charAt(i));
                if (isVar(aux, repetidos)) {

                    pRegla = pRegla.replaceAll(aux, String.valueOf(simUtilizados.charAt(contSim)));
                    contSim++;
                    repetidos.add(aux);
                }
            }

            if (expresion.contains(pRegla)) {
                return pRegla;
            }

        }

        return "";

    }

    public boolean findMarker(String expresion) {

        for (int i = 0; i < markers.size(); i++) {

            if (expresion.contains(markers.get(i))) {
                return true;
            }

        }
        return false;

    }

    public boolean isVar(String sim, ArrayList<String> repetidos) {

        for (int i = 0; i < variables.size(); i++) {

            if (sim.equals(variables.get(i)) && !isRepetidos(sim, repetidos)) {
                return true;
            }

        }
        return false;

    }

    public String verificarValRepetidos(String expresion, String pRegla, String simUtilizados) {

        int contSim = 0;
        String pR = pRegla;
        int cantSimbolos = extraerSimbolos(pRegla, true).length();
        for (int i = 0; i < cantSimbolos; i++) {
            String aux = String.valueOf(extraerSimbolos(pRegla, true).charAt(i));

            pRegla = pRegla.replaceAll(aux, String.valueOf(simUtilizados.charAt(contSim)));

            if (i + 1 == cantSimbolos && contSim < simUtilizados.length()) {
                contSim++;
                i = -1;

                if (expresion.contains(pRegla)) {
                    return pRegla;
                }
                if (contSim == simUtilizados.length()) {
                    return "";
                }
                pRegla = pR;
            }

        }

        return "";

    }

    public boolean isRepetidos(String sim, ArrayList<String> repetidos) {
        int i = 0;

        while (!repetidos.isEmpty()) {
            if (repetidos.get(i).equals(sim)) {
                return true;
            }
            repetidos.remove(i);
        }
        return false;
    }

    public String extraerSimbolos(String expresion, boolean bandera) {
        String fina = expresion;
        for (int i = 0; i < expresion.length(); i++) {

            String aux = String.valueOf(expresion.charAt(i));
            for (int j = 0; j < markers.size(); j++) {

                if (aux.equals(markers.get(j))) {

                    fina = fina.replaceAll(markers.get(j), "");
                    break;

                }

            }

        }
        if (bandera) {
            fina = removeDuplicate(fina.toCharArray(), fina.length());
        }
        return fina;

    }

    public int cambiarEstado(int estado, ArrayList<regla> reglas) {

        char est = reglas.get(estado).getSalto().charAt(2);
        String es = String.valueOf(est);
        return Integer.parseInt(es);

    }

    private void swap(char[] arr, int i, int j) {
        char c = arr[i];
        arr[i] = arr[j];
        arr[j] = c;
    }

    private void reverse(char[] arr, int i, int j) {
        while (i < j) {
            swap(arr, i++, j--);
        }
    }

    public ArrayList<String> permutations(String str, int n) {
        ArrayList<String> partial = new ArrayList<>();
        char[] s = str.toCharArray();
        Arrays.sort(s);

        while (true) {
            partial.add(String.valueOf(s));

            int i = n - 1;
            while (s[i - 1] >= s[i]) {
                if (--i == 0) {
                    return partial;
                }
            }

            int j = n - 1;
            while (j > i && s[j] <= s[i - 1]) {
                j--;
            }

            swap(s, i - 1, j);

            reverse(s, i, n - 1);
        }

    }

}
