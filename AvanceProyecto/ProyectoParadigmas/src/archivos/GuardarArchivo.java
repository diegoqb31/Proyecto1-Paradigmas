package archivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class guardarArchivo {

    /**
    * Guarda un archivo .txt con el contenido del TextArea
    * @param jTextArea_Codigo Contenido del TextArea
    */
    public static void guardarComo(JTextArea jTextArea_Codigo) {
        JFileChooser guardar = new JFileChooser();
        
        guardar.showSaveDialog(null);
        guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        File archivo = guardar.getSelectedFile();

        guardarFichero(jTextArea_Codigo.getText(), archivo);
    }

    /**
     * Guarda el archivo .txt en la ruta
     * @param codigo El contenido del código
     * @param archivo El archivo seleccionado
     */
    private static void guardarFichero(String codigo, File archivo) {
        FileWriter escribir;
        
        try {
            escribir = new FileWriter(archivo, true);
            escribir.write(codigo);
            escribir.close();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar, ponga nombre al archivo");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar, en la salida");
        }
    }
}

