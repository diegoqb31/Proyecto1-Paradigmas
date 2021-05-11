package main;



import views.View;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class ProyectoParadigmas {

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                View view = new View();
                view.setLocation(380,170);
                view.setVisible(true);
            }
        });
        
    }
    
    
  
}
