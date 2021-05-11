package logic;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class Regla {

    private String identicador, primeraRegla, trancision, salto;
    private boolean fin;

    public Regla() {
        this.identicador = "";
        this.primeraRegla = "";
        this.trancision = "";
        this.salto = "";
        this.fin = false;
    }

    public Regla(String identicador, String primeraRegla, String trancision, String salto) {
        this.identicador = identicador;
        this.primeraRegla = primeraRegla;
        this.trancision = trancision;
        this.salto = salto;
        this.fin = false;
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
    String aux= salto;
    if(!salto.equals("")){
    aux="("+String.valueOf(aux.charAt(1))+")";
    }
        s.append(String.format(" %s: %s -> %s %s", this.identicador, this.primeraRegla, this.trancision, aux));

       
        return s.toString();
    }
}
