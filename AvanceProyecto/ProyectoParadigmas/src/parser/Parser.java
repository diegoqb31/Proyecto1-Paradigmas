package parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class Parser {

    private final HashSet<Character> symbols, vars, markers;
    private final HashSet<String> reglas;
    private boolean parseMarkers, parseSymbols, parseVars;

    /**
     * Constructor para la clase Parser
     */
    public Parser() {
        this.symbols = new HashSet();
        this.vars = new HashSet();
        this.markers = new HashSet();
        this.reglas = new HashSet();

        this.parseSymbols = false;
        this.parseVars = false;
        this.parseMarkers = false;
    }

    /**
     * Sobreescribe el método {@link #toString()}
     *
     * @return Returna un String con los symbols, vars y markers
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format(" Symbols: %s%n Vars: %s%n Markers: %s%n Reglas:%n", symbols, vars, markers));

        for (String regla : reglas) {
            s.append(String.format("%s%n", regla));
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
                this.checkVars(c, "SYMBOL");
                this.checkMarkers(c, "SYMBOL");
                this.symbols.add(c);
            }
        }

        if (!parseVars) {
            for (char c : "wxyz".toCharArray()) {
                this.checkSymbols(c, "VAR");
                this.checkMarkers(c, "VAR");
                this.vars.add(c);

            }
        }

        if (!parseMarkers) {
            for (char c : "αβγδ".toCharArray()) {
                this.checkSymbols(c, "MARKER");
                this.checkVars(c, "MARKER");
                this.markers.add(c);
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
            if (!this.symbols.add(symbol)) {
                throw new Exception("El SYMBOL \"" + symbol + "\" ya fue declarado anteriormente");
            }

            this.checkVars(symbol, "SYMBOL");
            this.checkMarkers(symbol, "SYMBOL");
        }
    }

    /**
     * Agrega las vars al HashSet de vars
     *
     * @param vars El String con todos las vars
     * @throws Exception Tira una excepción en caso de que la var haya sido
     * declarado anteriormente
     */
    private void agregarVars(String vars) throws Exception {
        for (char var : vars.toCharArray()) {
            if (!this.vars.add(var)) {
                throw new Exception("La VAR \"" + var + "\" ya fue declarada anteriormente");
            }

            this.checkSymbols(var, "VAR");
            this.checkMarkers(var, "VAR");
        }
    }

    /**
     * Agrega los markers al HashSet de markers
     *
     * @param markers El String con todos los markers
     * @throws Exception Tira una excepción en caso de que el marker haya sido
     * declarado anteriormente
     */
    private void agregarMarkers(String markers) throws Exception {
        for (char marker : markers.toCharArray()) {
            if (!this.markers.add(marker)) {
                throw new Exception("El MARKER \"" + marker + "\" ya fue declarado anteriormente");
            }

            this.checkSymbols(marker, "MARKER");
            this.checkVars(marker, "MARKER");
        }
    }

    /**
     * Agrega los symbols al HashSet de reglas
     *
     * @param reglas El String con todas los reglas
     * @throws Exception Tira una excepción en caso de que la regla haya sido
     * declarado anteriormente
     */
    private void agregarReglas(String reglas) throws Exception {
        for (String regla : Arrays.asList(reglas)) {
            if (!this.reglas.add(regla)) {
                throw new Exception("La regla \"" + markers + "\" ya fue declarada anteriormente");
            }
        }
    }

    private void checkSymbols(char caracter, String tipo) throws Exception {
        if (this.symbols.contains(caracter)) {
            throw new Exception("El " + tipo + " \"" + caracter + "\" ya está siendo utilizado en la declaración de SYMBOLS");
        }
    }

    private void checkVars(char caracter, String tipo) throws Exception {
        if (this.vars.contains(caracter)) {
            throw new Exception("El " + tipo + " \"" + caracter + "\" ya está siendo utilizado en la declaración de VARS");
        }
    }

    private void checkMarkers(char caracter, String tipo) throws Exception {
        if (this.markers.contains(caracter)) {
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
        linea = linea.toLowerCase().replaceAll("^\\s+", "").replaceAll("\\s+$", "");
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
                    if (parseSymbols) {
                        throw new Exception("Error al parsear SYMBOLS, ya fueron declarados anteriormente");
                    }

                    this.agregarSymbols(linea.substring(linea.indexOf(" ") + 1));
                    this.parseSymbols = true;

                    break;

                case "vars":
                    if (parseVars) {
                        throw new Exception("Error al parsear VARS, ya fueron declaradas anteriormente");
                    }
                    
                    this.agregarVars(linea.substring(linea.indexOf(" ") + 1));
                    this.parseVars = true;

                    break;

                case "markers":
                    if (parseMarkers) {
                        throw new Exception("Error al parsear MARKERS, ya fueron declarados anteriormente");
                    }
                    
                    this.agregarMarkers(linea.substring(linea.indexOf(" ") + 1));
                    this.parseMarkers = true;
                    
                    break;

                default:
                    throw new Exception("Tipo no definido, tiene que ser SYMBOL, VAR o MARKER");
            }

        } else {
            this.agregarReglas(linea);
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
    }

    /**
     * Get symbols
     *
     * @return El HashSet de symbols
     */
    public HashSet<Character> getSymbols() {
        return symbols;
    }

    /**
     * Get vars
     *
     * @return El HashSet de vars
     */
    public HashSet<Character> getVars() {
        return vars;
    }

    /**
     * Get markers
     *
     * @return El HashSet de markers
     */
    public HashSet<Character> getMarkers() {
        return markers;
    }

    /**
     * Get reglas
     *
     * @return El HashSet de reglas
     */
    public HashSet<String> getReglas() {
        return reglas;
    }
}
