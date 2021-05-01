/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoparadigmas;

/**
 *
 * @author Carlos
 */
public class regla {
    
    String identicador;
    String primeraRegla;
    String trancision;
    String salto;
    boolean fin;

    public regla(String identicador, String primeraRegla, String trancision,String salto) {
        this.identicador = identicador;
        this.primeraRegla = primeraRegla;
        this.trancision = trancision;
        this.salto = salto;
    }

    public void setIdenticador(String identicador) {
        this.identicador = identicador;
    }

    public void setPrimeraRegla(String primeraRegla) {
        this.primeraRegla = primeraRegla;
    }

    public void setTrancision(String trancision) {
        this.trancision = trancision;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public String getIdenticador() {
        return identicador;
    }

    public String getPrimeraRegla() {
        return primeraRegla;
    }

    public String getTrancision() {
        return trancision;
    }

    public boolean isFin() {
        return fin;
    }

    public void setSalto(String salto) {
        this.salto = salto;
    }

    public String getSalto() {
        return salto;
    }
  
    
    
}
