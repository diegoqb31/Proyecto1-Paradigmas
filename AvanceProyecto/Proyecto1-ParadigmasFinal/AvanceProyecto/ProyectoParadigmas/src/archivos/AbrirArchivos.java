package archivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import proyectoparadigmas.View;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class AbrirArchivos {

    /**
     * Busca entre los archivos .txt, para desplegar el contenido en el TextArea
     *
     * @param vista Recibe la ventana
     * @param jTextArea_Codigo Recibe el contenido del TextArea
     */
    public static void abrirArchivo(javax.swing.JFrame vista, javax.swing.JTextArea jTextArea_Codigo) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            fileChooser.setFileFilter(filter);

            int seleccion = fileChooser.showOpenDialog(vista);
            File fichero = null;

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                fichero = fileChooser.getSelectedFile();
            }

            System.out.println(fichero);

            if ((fichero == null) || (fichero.getName().equals(""))) {
                JOptionPane.showMessageDialog(vista, "Nombre de archivo inválido", "Nombre de archivo inválido", JOptionPane.ERROR_MESSAGE);
            }

            Scanner scn = new Scanner(fichero);

            while (scn.hasNext()) {
                jTextArea_Codigo.insert(scn.nextLine() + "\n", jTextArea_Codigo.getText().length());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
