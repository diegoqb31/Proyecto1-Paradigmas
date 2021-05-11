/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class Debugger {

    String identicador;
    String primeraRegla;
    String trancision;
    String expresion;

    public Debugger(String identicador, String primeraRegla, String trancision, String expresion) {
        this.identicador = identicador;
        this.primeraRegla = primeraRegla;
        this.trancision = trancision;
        this.expresion = expresion;
    }
    
    public Debugger(){
        this(new String(),new String(), new String(),new String());
    }

    public String getIdenticador() {
        return identicador;
    }

    public void setIdenticador(String identicador) {
        this.identicador = identicador;
    }

    public String getPrimeraRegla() {
        return primeraRegla;
    }

    public void setPrimeraRegla(String primeraRegla) {
        this.primeraRegla = primeraRegla;
    }

    public String getTrancision() {
        return trancision;
    }

    public void setTrancision(String trancision) {
        this.trancision = trancision;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
    
    

    @Override
    public String toString() {
        
        StringBuilder s= new StringBuilder();
        
        s.append(String.format("identicador:%s "
                + "PrimeraRegla:%s "
                + "trancision:%s "
                + "expresion:%s%n"
               , identicador,primeraRegla,trancision,expresion));
        
        return s.toString();
    }
    
    

}
