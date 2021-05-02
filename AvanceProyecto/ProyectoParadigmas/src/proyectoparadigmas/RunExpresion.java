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
import proyectoparadigmas.regla;

/**
 *
 * @author Carlos
 */
public class RunExpresion {

    private ArrayList<String> variables = new ArrayList<>();

    private String expresion;

    private ArrayList<Character> simbolos; //lista de simbolos

    private ArrayList<String> markers;
    
    private String expresionArchivo = "";

    Boolean m[][];

    public void setExpresionArchivo(String expresionArchivo) {
        this.expresionArchivo = expresionArchivo;
    }

    public ArrayList<String> getVariables() {
        return variables;
    }

    public String getExpresion() {
        return expresion;
    }

    public ArrayList<String> getMarkers() {
        return markers;
    }

    public ArrayList<Character> getSimbolos() {
        return simbolos;
    }

    public String getExpresionArchivo() {
        return expresionArchivo;
    }

    public Boolean[][] getM() {
        return m;
    }

    public void setVariables(ArrayList<String> variables) {
        this.variables = variables;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public void setSimbolos(ArrayList<Character> simbolos) {
        this.simbolos = simbolos;
    }

    public void setM(Boolean[][] m) {
        this.m = m;
    }

    public void setMarkers(ArrayList<String> markers) {
        this.markers = markers;
    }

    public RunExpresion(String expresion, ArrayList<Character> simbolos, Boolean[][] m) {
        this.expresion = expresion;
        this.simbolos = simbolos;
        this.m = m;
    }

    public RunExpresion() {
    }

    public void leerExpresionExistente(String ruta) {
        String retornar = "";
        String linea = "";
        try {
            ArrayList array = new ArrayList();
            File archivo = new File(ruta);

            if (archivo.exists()) {
                FileReader fr = new FileReader(archivo);
                BufferedReader buffer = new BufferedReader(fr);
                String cursor;
                while ((linea = buffer.readLine()) != null) {
                    retornar += linea + "\n";
                }
                buffer.close();
            } else {
                System.out.println("No existe el archivo...");
            }
        } catch (Exception ex) {
        }
        this.setExpresionArchivo(retornar);
        System.out.println("Retorna la exp: " + expresionArchivo + "\n");
    }

    public static Boolean[][] calcularatriz(ArrayList<Character> variables) {

        int columnas = variables.size();
        int filas = 1 << columnas;
        Boolean matriz[][] = new Boolean[filas][columnas]; //matriz de la tabla

        int brincos = 1;

        for (int i = columnas - 1; i >= 0; i--) {
            Boolean valor = false;
            int j = 0;
            while (j < filas) {
                for (int k = 0; k < brincos; j++, k++) {
                    matriz[j][i] = valor;
                }
                valor = !valor;
            }
            brincos <<= 1;
        }
        return matriz;
    }

    public void guardarExpresionExistente(String ruta, String cadena) throws IOException {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            archivo.createNewFile();
            System.out.println("Se creo el archivo...");
        }
        try {
            FileWriter escribirArchivo = new FileWriter(archivo, true);
            BufferedWriter buffer = new BufferedWriter(escribirArchivo);
            buffer.write(cadena);
            buffer.newLine();
            buffer.close();
        } catch (IOException ex) {
        }
    }

    public void imprimeMatriz(Boolean[][] matriz) { //imprime la matriz con formato
        Boolean[][] tablaVerdad = matriz;
        for (Boolean[] fila : tablaVerdad) {
            for (Boolean columna : fila) {

                System.out.printf("\t %c", columna ? 'V' : 'F');
            }
            System.out.println();
            System.out.println();
        }
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
    
    
       GbFb
        Fa    a
        Fb    b
        Fc    c
    1. Fx -> xF
    2. xF -> x#.
    3. x -> Fx
     */
    public void datosQuemados() {

        ArrayList<regla> reglas = new ArrayList();

        regla r1 = new regla("p1", "Fx", "xF", "");
        regla r2 = new regla("p2", "x", "x#", "(p2)");
        regla r3 = new regla("p3", "x", "Fx", "");
        
        ArrayList<String> mar = new ArrayList();
        mar.add("F");
        mar.add("G");
        
        extraerSimbolos("FaabcGab",mar);
        
        r2.setFin(true);

        reglas.add(r1);
        reglas.add(r2);
        reglas.add(r3);

        leerExpresion(reglas, "abc", "xyz");
        this.setMarkers(mar);

    }

    public void leerExpresion(ArrayList<regla> reglas, String expresion, String vars) {
        /* Fabc ----- abbc         a,b,c     x = a,b,c  vbnG -> bnF
        Fa -> ab   F,G,H */
        int estado = 0;

        while (estado < reglas.size()) {

            if (expresion.contains(reglas.get(estado).getPrimeraRegla())) {

                if (!reglas.get(estado).getSalto().equals("")) {
                    estado = cambiarEstado(estado, reglas);
                    continue;
                }

            }

            estado++;

            if (estado < reglas.size()) {
                estado = 0;
            }

        }

    }

    public boolean contiene(String expresion, String  pRegla, ArrayList<regla> reglas, String vars) {
        /*  aFbc  -->>>  xyz      F,G,H      
        
        pRegla -->   Fx    Fa -> Fb -> Fc 
    
        */ 
        
        
        for (int i = 0; i < expresion.length(); i++) {
            
            

        }
        return true;

    }
    
    public String extraerSimbolos(String expresion,ArrayList<String> markers1){
         String fina = expresion;
        for (int i = 0; i < expresion.length(); i++) {
            
            String aux = String.valueOf(expresion.charAt(i));
            for (int j = 0; j < markers1.size(); j++) {
                
                if(aux.equals(markers1.get(j))){
                    
                    fina = fina.replaceAll(markers1.get(j), "");
                    break;
                    
                }
                
            }
            
        }
        return fina;
        
    }

    public int cambiarEstado(int estado, ArrayList<regla> reglas) {

        char est = reglas.get(estado).getSalto().charAt(2);
        String es = String.valueOf(est);
        return Integer.parseInt(es);

    }

}
