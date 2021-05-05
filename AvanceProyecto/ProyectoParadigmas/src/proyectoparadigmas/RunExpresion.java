/*
Autores:
Diego Quiros Brenes
Carlos Chacon Vargas
Alessandro Fazio Perez
Bryan Sanchez Brenes
 */
package proyectoparadigmas;

import java.util.ArrayList;
import java.util.Arrays;
import parser.Regla;
import parser.Parser;
import parser.ValoresPruebaParser;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class RunExpresion {

    private ArrayList<String> simbolos;
    private ArrayList<String> variables;
    private ArrayList<String> markers;
    private ArrayList<Regla> reglas;
    private String expresion;
    private ArrayList<String> resultado;

    public RunExpresion(Parser parser, String expresion) {
        this.simbolos = parser.getSymbols();
        this.variables = parser.getVars();
        this.markers = parser.getMarkers();
        this.reglas = new ArrayList<>();
        // this.reglas=parser.getReglas();
        this.expresion = expresion;
        this.resultado = new ArrayList<>();

    }

    public void calcular() {
        leerExpresion();
    }

    public void calcularValoresPrueba() {
        ValoresPruebaParser parser = ValoresPruebaParser.getIntance();

        this.simbolos = parser.getSymbols();
        this.variables = parser.getVars();
        this.markers = parser.getMarkers();
        this.reglas = parser.getReglas();
        this.expresion = parser.getExpression();

        calcular();
    }

    private ArrayList<String> leerExpresion() {
        /* 
       p1. Fx -> xF
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

        resultado.add("Expresion-> " + expresion);
        while (estado < reglas.size()) {
            aux = contiene(expresion, reglas.get(estado).getPrimeraRegla());
            if (!aux.equals("")) {

                expresion = expresion.replaceFirst(aux, sRegla(aux, reglas.get(estado).getTrancision()));
                resultado.add(reglas.get(estado).getIdenticador() + "-> " + expresion);

                if (expresion.contains(".")) {
                    expresion = expresion.replace(".", "");
                    resultado.add("Resultado-> " + expresion);
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

    private String sRegla(String pR, String sR) {
        /* sR -> yGx --> aGb  zyGx <-- Gabc  a:x:0  b:y:1 c:z:2
           pR -> Gab --> bGa                   1      0    ...             */

        String simUtilizados = extraerSimbolos(pR);
        String varUtilizadas = extraerSimbolos(sR);
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

    private String verficarPosicionVariable(String varUtilizadas, ArrayList<String> sim) {
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

    private String contiene(String expresion, String pRegla) {

        int contSim = 0;
        String pR = pRegla;
        ArrayList<String> repetidos = new ArrayList();
        String simUtilizados = extraerSimbolos(expresion);
        ArrayList<String> permuta = permutations(simUtilizados, simUtilizados.length());

        for (int j = 0; j < permuta.size(); j++) {
            if (j == 0) {
                simUtilizados = permuta.get(permuta.indexOf(simUtilizados));
                permuta.remove(permuta.indexOf(simUtilizados));
            } else {
                simUtilizados = permuta.get(j);
            }
            pRegla = pR;
            contSim = 0;
            repetidos.clear();
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

    private boolean isVar(String sim, ArrayList<String> repetidos) {

        for (int i = 0; i < variables.size(); i++) {

            if (sim.equals(variables.get(i)) && !isRepetidos(sim, repetidos)) {
                return true;
            }

        }
        return false;

    }

    private boolean isRepetidos(String sim, ArrayList<String> repetidos) {
        int i = 0;

        while (!repetidos.isEmpty()) {
            if (repetidos.get(i).equals(sim)) {
                return true;
            }
            repetidos.remove(i);
        }
        return false;
    }

    private String extraerSimbolos(String expresion) {
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
        return fina;

    }

    private int cambiarEstado(int estado, ArrayList<Regla> reglas) {

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

    private ArrayList<String> permutations(String str, int n) {
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (String string : resultado) {
            s.append(String.format("%s%n", string));
        }

        return s.toString();
    }

}
