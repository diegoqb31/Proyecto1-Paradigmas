/*
Autores:
Diego Quiros Brenes
Carlos Chacon Vargas
Alessandro Fazio Perez
Bryan Sanchez Brenes
 */
package proyectoparadigmas;

import archivos.Debugger.Debugger;
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
public class RunExpresion implements Runnable {

    private ArrayList<String> simbolos;
    private ArrayList<String> variables;
    private ArrayList<String> markers;
    private ArrayList<Regla> Reglas;
    private ArrayList<Debugger> listaDebugger;
    private String expresion;
    private ArrayList<String> resultado;
    private boolean exit;
    private boolean debug;
    Thread t;
    private int estado = 0;
    private boolean end = false;
    ViewDebug view;

    public RunExpresion(Parser parser, String expresion, boolean debug) {
        this.simbolos = parser.getSymbols();
        this.variables = parser.getVars();
        this.markers = parser.getMarkers();;
        this.Reglas = parser.getReglas();
        this.expresion = expresion;
        this.resultado = new ArrayList<>();
        this.exit = false;
        this.debug = debug;
        this.listaDebugger = new ArrayList<>();
        this.t = new Thread(this, "run");
        this.t.start();

    }

    /*
#symbols abcdefghijklmnopqrstuvwxyz0123456789
#vars wxyz
#markers GF#^
    
% Invierte una hilera.

p1: Fx -> xF
p2: xF -> x# (p4)
p3: x -> Fx
p4: Gx# -> #x (p4)
p5: #G -> ^.
p6: Gxy -> yGx (p4)
p7: x -> Gx (p4)
p8: # -> ^.
    

     */
    private String expresionPrincipal;

    private String expresionArchivo = "";

    Parser parser;

    public void setListaDebugger(ArrayList<Debugger> listaDebugger) {
        this.listaDebugger = listaDebugger;
    }

    public ArrayList<Debugger> getListaDebugger() {
        return listaDebugger;
    }

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

    public void setView(ViewDebug view) {
        this.view = view;
    }

    public ViewDebug getView() {
        return view;
    }

    public ArrayList<Regla> getReglas() {
        return Reglas;
    }

    public String getExpresionArchivo() {
        return expresionArchivo;
    }

    public void setVariables(ArrayList<String> variables) {
        this.variables = variables;
    }

    public ArrayList<String> getResultado() {
        return resultado;
    }

    public void calcularValoresPrueba() {
        ValoresPruebaParser parser = ValoresPruebaParser.getIntance();

        this.simbolos = parser.getSymbols();
        this.variables = parser.getVars();
        this.markers = parser.getMarkers();
        this.Reglas = parser.getReglas();
        this.expresion = parser.getExpression();
        this.exit = false;
        this.debug = true;
        this.resultado = new ArrayList<>();
        this.t = new Thread(this, "run");
        this.t.start();

    }

    public void setSimbolos(ArrayList<String> simbolos) {
        this.simbolos = simbolos;
    }

    public void setMarkers(ArrayList<String> markers) {
        this.markers = markers;
    }

    public RunExpresion() {

    }

    private ArrayList<String> leerExpresion() {

        int estado = 0;
        String aux = "";

        resultado.add("Expresion-> " + expresion);
        while (estado < Reglas.size()) {
            aux = contiene(expresion, Reglas.get(estado).getPrimeraRegla());
            if (!aux.equals("")) {

                if (!Reglas.get(estado).getTrancision().equals("^")) {
                    expresion = expresion.replaceFirst(aux, sRegla(aux, Reglas.get(estado).getTrancision()));
                }

                resultado.add(Reglas.get(estado).getIdenticador() + "-> " + expresion);

                if (expresion.contains(".")) {
                    expresion = expresion.replace(".", "");
                    resultado.add("Resultado-> " + expresion);
                    return resultado;
                }

                if (!Reglas.get(estado).getSalto().equals("")) {
                    estado = cambiarEstado(estado, Reglas) - 1;
                    continue;
                }
                estado = 0;
                continue;
            }

            estado++;

            if (estado == Reglas.size()) {
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

    private String sRegla(String pR, String sR) {
        /* sR -> yGx --> aGb  zyGx <-- Gabc  a:x:0  b:y:1 c:z:2
           pR -> Gab --> bGa  
        
        1      0    ...             */

        String simUtilizados = extraerSimbolos(pR, false);
        String varUtilizadas = extraerSimbolos(sR, true);
        ArrayList<String> repetidos = new ArrayList();
        ArrayList<String> sim = new ArrayList();
        int contSim = 0;

        if (!simUtilizados.equals("")) {
            for (int i = 0; i < simUtilizados.length(); i++) {
                sim.add(String.valueOf(simUtilizados.charAt(i)));
                sim.add(String.valueOf(varUtilizadas.charAt(i)));
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

    private boolean isVar(String sim, ArrayList<String> repetidos) {

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

    private int cambiarEstado(int estado, ArrayList<Regla> Reglas) {

        char est = Reglas.get(estado).getSalto().charAt(1);
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

        resultado.forEach((String string) -> {
            s.append(string).append("\n");
        });

        return s.toString();
    }

    public void resume() {

        if (!end) {
            exit = false;
            this.run();
        }
    }

    
    public void agregarDatosDebugger(String id, String regla1, String regla2, String expresion){
        Debugger debugger = new Debugger(id,regla1,regla2,expresion);
                    listaDebugger.add(debugger);
    }
    @Override
    public void run() {
        int i = 0;

        while (!exit) {

            // int estado = 0;
            String aux = "";

            while (estado < Reglas.size()) {

                aux = contiene(expresion, Reglas.get(estado).getPrimeraRegla());
                if (!aux.equals("")) {

                    if (!Reglas.get(estado).getTrancision().equals("^")) {
                        expresion = expresion.replaceFirst(aux, sRegla(aux, Reglas.get(estado).getTrancision()));
                    }

                    resultado.add(Reglas.get(estado).getIdenticador() + "-> " + expresion);

                    System.out.print(expresion);

                
                    agregarDatosDebugger(Reglas.get(estado).getIdenticador(), aux, Reglas.get(estado).getTrancision(), expresion);
                    
                    if (Reglas.get(estado).isFin()) {
                        resultado.add("Resultado-> " + expresion);

                        end = true;
                        this.view.getjTextArea_Resultado().setText(this.toString());
                        this.stop(true);
                        break;
                    }

                   

                    if (!Reglas.get(estado).getSalto().equals("")) {
                        estado = cambiarEstado(estado, Reglas) - 1;

                        if (this.stop(debug)) {
                            this.view.getjTextArea_Resultado().setText(this.toString());
                            break;
                        }
                        continue;
                    }

                    estado = 0;

                    if (this.stop(debug)) {
                        this.view.getjTextArea_Resultado().setText(this.toString());
                        break;
                    }
                    continue;
                }

                estado++;

                if (estado == Reglas.size()) {
                    estado = 0;

                }

                if (this.stop(debug)) {
                    break;
                }

            }
        }
    }

    public boolean stop(boolean bandera) {
        if (bandera) {
            exit = true;
            return true;
        }
        return false;
    }

}
