/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import logic.Regla;
import logic.RunExpresion;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class ValoresPruebaParser {

    private final ArrayList<String> symbols, vars, markers;
    private final ArrayList<Regla> reglas;
    private String expression;
    private static final ValoresPruebaParser instance = null;
    private RunExpresion exp;
    
    private ValoresPruebaParser() {
        this.symbols = new ArrayList<>();

        this.vars = new ArrayList<>();
        this.markers = new ArrayList<>();
        this.reglas = new ArrayList<>();
        this.expression = new String();
        creandoValores();
    }

    public static ValoresPruebaParser getIntance() {
        return instance != null ? instance : new ValoresPruebaParser();
    }

    private void creandoValores() {

        symbols.add("a");
        symbols.add("b");
        symbols.add("c");

        vars.add("x");
        vars.add("y");
        vars.add("z");
        vars.add("w");

        markers.add("F");
        markers.add("G");
        markers.add("#");
        markers.add("^");

        Regla r1 = new Regla("p1", "Fx", "xF", "");
        Regla r2 = new Regla("p2", "xF", "x#", "(p4)");
        Regla r3 = new Regla("p3", "x", "Fx", "");
        Regla r4 = new Regla("p4", "Gx#", "#x", "(p4)");
        Regla r5 = new Regla("p5", "#G", ".", "");
        Regla r6 = new Regla("p6", "Gxy", "yGx", "(p4)");
        Regla r7 = new Regla("p7", "x", "Gx", "(p4)");
        Regla r8 = new Regla("p8", "#", ".", "");
        r2.setFin(true);

        reglas.add(r1);
        reglas.add(r2);
        reglas.add(r3);
        reglas.add(r4);
        reglas.add(r5);
        reglas.add(r6);
        reglas.add(r7);
        reglas.add(r8);

        expression = "abc";
    }

    public ArrayList<String> getSymbols() {
        return symbols;
    }

    public ArrayList<String> getVars() {
        return vars;
    }

    public ArrayList<String> getMarkers() {
        return markers;
    }

    public ArrayList<Regla> getReglas() {
        return reglas;
    }

    public String getExpression() {
        return expression;
    }
}
