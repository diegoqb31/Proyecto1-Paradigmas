/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class parser {

    private String symbols;
    private List<String> vars;
    private List<String> markets;
    private List<String> reglas;
    private boolean resetearvars = false;
    private boolean resetearmarkets = false;

    public parser(String symbols,
            ArrayList<String> reglas) {
        this.symbols = symbols;
        this.vars = new ArrayList();
        this.vars.add("x");
        this.vars.add("y");
        this.vars.add("z");
        this.markets = new ArrayList();
        this.markets.add("β");
        this.markets.add("δ");
        this.reglas = reglas;
    }

    public parser() {

        this("abcdefghijklmnopqrstuvwxyz0123456789",
                new ArrayList()
        );
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("Symbols: %s%n"
                + "Vars: %s%n"
                + "Markets: %s%n"
                + "Reglas:%n", symbols, vars, markets));

        int pos = 1;
        for (String regla : reglas) {
            s.append(String.format("P%d: %s%n", pos++, regla));
        }

        return s.toString();
    }

    public void agregarSymbols(String s) {
        int d = s.indexOf(":");

        String ptr = s.substring(d + 1);
        this.symbols = ptr;
    }

    public void agregarVars(String s) {
        int d = s.indexOf(":");
        String ptr = s.substring(d + 1);
        String[] p = ptr.split(",");
        if (!resetearvars) {
            resetearvars = true;
            vars.clear();
            for (int i = 0; i < p.length; i++) {
                String v = String.valueOf(p[i]);

                vars.add(v);

            }

        }
    }

    public void agregarMarkets(String s) {
        int d = s.indexOf(":");
        String ptr = s.substring(d + 1);
        String[] p = ptr.split(",");
        if (!resetearmarkets) {
            resetearmarkets = true;
            markets.clear();
            for (int i = 0; i < p.length; i++) {
                String v = String.valueOf(p[i]);

                markets.add(v);

            }

        }
    }

    public void agregarReglas(String s) {

    }
}
