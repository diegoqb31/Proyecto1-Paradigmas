package parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class Parser {

    private final ArrayList<String> symbols, vars, markers;
    private final ArrayList<Regla> reglas;
    private final ArrayList<String> reglasString;
    private boolean parseMarkers, parseSymbols, parseVars;
    private int cantidadReglas;

    /**
     * Constructor para la clase Parser
     */
    public Parser() {
        this.symbols = new ArrayList<>();
        this.vars = new ArrayList<>();
        this.markers = new ArrayList<>();
        this.reglas = new ArrayList<>();
        this.reglasString = new ArrayList<>();

        this.parseSymbols = false;
        this.parseVars = false;
        this.parseMarkers = false;
        
        this.cantidadReglas = 0;
    }

    /**
     * Sobreescribe el método {@link #toString()}
     *
     * @return Returna un String con los symbols, vars y markers
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Regla regla : this.reglas) {
            
            s.append(regla.toString());
            if(regla.isFin()){
                s.append(".");
            }
            s.append("\n");
        }

        return s.toString();
    }

    /**
     * Setea los valores por defecto en caso de que no se sobreescriban en el
     * código
     */
    private void setValoresDefecto() throws Exception {
        if (!parseSymbols) {
            for (char c : "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray()) {
                this.checkMarkers(c, "SYMBOL");
                this.symbols.add(String.valueOf(c));
            }
        }

        if (!parseVars) {
            for (char c : "wxyz".toCharArray()) {
                this.checkMarkers(c, "VAR");
                this.vars.add(String.valueOf(c));

            }
        }

        if (!parseMarkers) {
            for (char c : "αβγδ".toCharArray()) {
                this.checkSymbols(c, "MARKER");
                this.checkVars(c, "MARKER");
                this.markers.add(String.valueOf(c));
            }
        }
    }

    /**
     * Agrega los symbols al HashSet de symbols
     *
     * @param symbols El String con todos los symbols
     * @throws Exception Tira una excepción en caso de que el symbol haya sido
     * declarado anteriormente
     */
    private void agregarSymbols(String symbols) throws Exception {
        for (char symbol : symbols.toCharArray()) {
            if (!this.symbols.add(String.valueOf(symbol))) {
                throw new Exception("El SYMBOL \"" + symbol + "\" ya fue declarado anteriormente");
            }

            this.checkMarkers(symbol, "SYMBOL");
        }
    }

    /**
     * Agrega las vars al ArrayList de vars
     *
     * @param vars El String con todos las vars
     * @throws Exception Tira una excepción en caso de que la var haya sido
     * declarado anteriormente
     */
    private void agregarVars(String vars) throws Exception {
        for (char var : vars.toCharArray()) {
            if (!this.vars.add(String.valueOf(var))) {
                throw new Exception("La VAR \"" + var + "\" ya fue declarada anteriormente");
            }
            
            this.checkMarkers(var, "VAR");
        }
    }

    /**
     * Agrega los markers al ArrayList de markers
     *
     * @param markers El String con todos los markers
     * @throws Exception Tira una excepción en caso de que el marker haya sido
     * declarado anteriormente
     */
    private void agregarMarkers(String markers) throws Exception {
        for (char marker : markers.toCharArray()) {
            if (!this.markers.add(String.valueOf(marker))) {
                throw new Exception("El MARKER \"" + marker + "\" ya fue declarado anteriormente");
            }

            this.checkSymbols(marker, "MARKER");
            this.checkVars(marker, "MARKER");
        }
    }

    /**
     * Agrega la regla al ArrayList de reglas
     *
     * @param linea La linea que contiene la regla
     * @throws Exception Tira una excepción en caso de que la regla haya sido
     * declarado anteriormente o haya errores de parsing en la regla
     */
    private void procesarRegla(String linea) throws Exception {
        if (!this.reglasString.add(linea)) {
            throw new Exception("La regla \"" + markers + "\" ya fue declarada anteriormente");
        }
        
        if (!linea.contains("->") || (linea.contains(".") && linea.contains("("))) {
            throw new Exception("Error de parsing en reglas");
        }
        
        linea = linea.replaceAll("\\s+","");
        
        Regla regla = new Regla();
        String etiqueta = "P" + this.cantidadReglas++;
        
        try {
            if (linea.contains(":")) {
                etiqueta = linea.substring(0, linea.indexOf(":"));
                linea = linea.replace(etiqueta + ":", "");
            }
            
            String patron = linea.substring(0, linea.indexOf("->"));
            String sustitucion = linea.substring(linea.indexOf("->") + 2);
            String salto = "";
            
            if (sustitucion.contains(".")) {
                regla.setFin(true);
                sustitucion = sustitucion.replace(".", "");
                
            } else if (sustitucion.contains("(")) {
                salto = sustitucion.substring(sustitucion.indexOf("(") + 1, sustitucion.indexOf(")"));
            }
            
            sustitucion = sustitucion.replace("(" + salto + ")", "");
                        
            regla.setIdenticador(etiqueta);
            regla.setPrimeraRegla(patron);
            regla.setTrancision(sustitucion);
            regla.setSalto(salto);
            
            this.agregarRegla(regla);
            
        } catch (Exception e) {
            throw new Exception("Error de parsing en reglas");
        }
    }
    
    private void agregarRegla(Regla regla) throws Exception {
        if (this.reglas.isEmpty()) {
            this.reglas.add(regla);
            return;
        }
        
        for (Regla r : this.reglas) {
            if (r.getIdenticador().equals(regla.getIdenticador())) {
                throw new Exception("La regla fue declarada anteriormente");
            }
        }
        
        this.reglas.add(regla);
    }

    private void checkSymbols(char caracter, String tipo) throws Exception {
        if (this.symbols.contains(String.valueOf(caracter))) {
            throw new Exception("El " + tipo + " \"" + caracter + "\" ya está siendo utilizado en la declaración de SYMBOLS");
        }
    }

    private void checkVars(char caracter, String tipo) throws Exception {
        if (this.vars.contains(String.valueOf(caracter))) {
            throw new Exception("El " + tipo + " \"" + caracter + "\" ya está siendo utilizado en la declaración de VARS");
        }
    }

    private void checkMarkers(char caracter, String tipo) throws Exception {
        if (this.markers.contains(String.valueOf(caracter))) {
            throw new Exception("El " + tipo + " \"" + caracter + "\" ya está siendo utilizado en la declaración de MARKERS");
        }
    }

    /**
     * Parsea cada línea del código
     *
     * @param linea La línea de código a parsear
     * @throws Exception Tira una excepción en caso de que haya dos
     * declaraciones de symbols, vars, markers
     */
    private void leerLinea(String linea) throws Exception {
        linea = linea.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
        char primerCaracter = linea.charAt(0);

        if (primerCaracter == '%') {
            return;
        }

        if (primerCaracter == '#') {
            String tipo = "";
            try {
                tipo = linea.substring(1, linea.indexOf(" "));
                
            } catch (Exception e) {
                throw new Exception("Error al parsear " + linea);
            }

            switch (tipo) {
                case "symbols":
                    this.agregarSymbols(linea.substring(linea.indexOf(" ") + 1));
                    this.parseSymbols = true;

                    break;

                case "vars":
                    this.agregarVars(linea.substring(linea.indexOf(" ") + 1));
                    this.parseVars = true;

                    break;

                case "markers":
                    this.agregarMarkers(linea.substring(linea.indexOf(" ") + 1));
                    this.parseMarkers = true;
                    
                    break;

                default:
                    throw new Exception("Tipo no definido, tiene que ser SYMBOLS, VARS o MARKERS");
            }

        } else {
            this.procesarRegla(linea);
        }
    }
    
    private void validarReglas() throws Exception {
        for (Regla regla : this.reglas) {
            boolean valido = false;
            
            for (char s : regla.getPrimeraRegla().toCharArray()) {
                if (this.symbols.contains(String.valueOf(s)) || this.markers.contains(String.valueOf(s)) || this.vars.contains(String.valueOf(s)) || s == '^') {
                    valido = true;
                    break;
                }
            }
            
            if (!valido) {
                throw new Exception("La regla no utiliza los SYMBOLS, MARKERS o VARS decladaros anteriormente");
            }
            
            valido = false;
            
            for (char s : regla.getTrancision().toCharArray()) {
                if (this.symbols.contains(String.valueOf(s)) || this.markers.contains(String.valueOf(s)) || this.vars.contains(String.valueOf(s)) || s == '^') {
                    valido = true;
                    break;
                }
            }
            
            if (!valido) {
                throw new Exception("La regla no utiliza los SYMBOLS, MARKERS o VARS decladaros anteriormente");
            }
            
            valido = false;
            
            if (!regla.getSalto().equals("")) {
                for (Regla r : this.reglas) {
                    if (regla.getSalto().equals(r.getIdenticador())) {
                        valido = true;
                        break;
                    }
                }
                
                if (!valido) {
                    throw new Exception("La etiqueta de salto de una regla no existe como regla");
                }
            }
            
        }
    }

    /**
     * Normaliza el código
     *
     * @param codigo El código de entrada
     * @throws Exception Tira una excepcion en caso de que
     * {@link #leerLinea(java.lang.String)} tire una excepción
     */
    public void leerCodigo(String codigo) throws Exception {
        codigo = codigo.replaceAll("(?m)^[ \t]*\r?\n", "");

        try (BufferedReader reader = new BufferedReader(new StringReader(codigo))) {
            String linea = reader.readLine();

            while (linea != null) {
                this.leerLinea(linea);
                linea = reader.readLine();
            }
        }

        this.setValoresDefecto();
        
        this.validarReglas();
    }

    /**
     * Get symbols
     *
     * @return El HashSet de symbols
     */
    public  ArrayList<String> getSymbols() {
        return symbols;
    }

    /**
     * Get vars
     *
     * @return El HashSet de vars
     */
    public  ArrayList<String> getVars() {
        return vars;
    }

    /**
     * Get markers
     *
     * @return El HashSet de markers
     */
    public  ArrayList<String> getMarkers() {
        return markers;
    }

    /**
     * Get reglas
     *
     * @return El HashSet de reglas
     */
    public  ArrayList<Regla> getReglas() {
        return reglas;
    }
}
