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
    String condicion;

    public Debugger(String identicador, String primeraRegla, String trancision,
            String condicion, String expresion) {
        this.identicador = identicador;
        this.primeraRegla = primeraRegla;
        this.trancision = trancision;
        this.expresion = expresion;
        this.condicion=condicion;
    }
    
    public Debugger(){
        this(new String(),new String(), new String(),new String(),new String());
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

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
    
    

    @Override
    public String toString() {
        
        StringBuilder s= new StringBuilder();
        
        s.append(String.format("identicador:%s "
                + "PrimeraRegla:%s "
                + "trancision:%s "
                + "Condición:%s "
                + "expresion:%s"
               , identicador,primeraRegla,trancision,condicion,expresion));
        
        return s.toString();
    }
    
    

}
