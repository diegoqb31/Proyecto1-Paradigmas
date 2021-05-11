/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import logic.Debugger;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import logic.Parser;

/**
 * @author Carlos Chacon Vargas
 * @author Bryan Sanchez Brenes
 * @author Diego Quiros Brenes
 * @author Alessandro Fazio Perez
 */
public class ViewDebugger extends javax.swing.JFrame {

    /**
     * Creates new form ViewDebugger
     */
    private ArrayList<Debugger> listaDebugger;
    private JFrame vie;
    private Parser parser;
    
    public ViewDebugger() {
        initComponents();
    }
    
      public ViewDebugger(JFrame vie,Parser parser) {
        initComponents();
        this.vie=vie;
        this.parser = parser;
        this.jTextArea_Reglas.setText(parser.toString());
    }

    public ArrayList<Debugger> getListaDebugger() {
        return listaDebugger;
    }

    public void setListaDebugger(ArrayList<Debugger> listaDebugger) {
        this.listaDebugger = listaDebugger;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtnSiguiente = new javax.swing.JButton();
        jBtnDetener = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane_Resultado = new javax.swing.JScrollPane();
        jTextArea_Reglas = new javax.swing.JTextArea();

        setTitle("Debugger");
        setLocationByPlatform(true);
        setName("Debugger"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jBtnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next-button.png"))); // NOI18N
        jBtnSiguiente.setText("SIguiente");
        jBtnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSiguienteActionPerformed(evt);
            }
        });

        jBtnDetener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/stop.png"))); // NOI18N
        jBtnDetener.setText("Detener");
        jBtnDetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDetenerActionPerformed(evt);
            }
        });

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Regla", "Regla", "Condición", "Expresión"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/play.png"))); // NOI18N
        jButton1.setText("Continuar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea_Reglas.setEditable(false);
        jTextArea_Reglas.setColumns(20);
        jTextArea_Reglas.setRows(5);
        jScrollPane_Resultado.setViewportView(jTextArea_Reglas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnSiguiente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnDetener)
                                .addGap(113, 113, 113)
                                .addComponent(jButton1)
                                .addGap(110, 110, 110))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane_Resultado, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSiguiente)
                    .addComponent(jBtnDetener)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane_Resultado)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSiguienteActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) this.jTable.getModel();
        
        if(posicion<listaDebugger.size()){
        Object[] fila = new Object[4];
        fila[0] = listaDebugger.get(posicion).getIdenticador();
        fila[1] = listaDebugger.get(posicion).getPrimeraRegla()+ "-->" +listaDebugger.get(posicion).getTrancision();
        fila[2] = listaDebugger.get(posicion).getCondicion();
        fila[3] = listaDebugger.get(posicion).getExpresion();
        
        modelo.addRow(fila);
        this.jTable.setModel(modelo);

        posicion++;

    }else{
        jBtnSiguiente.setEnabled(false);
        }
    }//GEN-LAST:event_jBtnSiguienteActionPerformed

    
    private void toDebug(String p){
        
        StringBuilder s = new StringBuilder();
        
        

        //return s.toString();
        
    }
    
    private void jBtnDetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDetenerActionPerformed
        // TODO add your handling code here:
        jBtnSiguiente.setEnabled(false);
    }//GEN-LAST:event_jBtnDetenerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jBtnSiguiente.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
        this.setVisible(false);
        vie.setVisible(true);
         dispose();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosed

    int posicion = 0;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnDetener;
    private javax.swing.JButton jBtnSiguiente;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_Resultado;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable;
    private javax.swing.JTextArea jTextArea_Reglas;
    // End of variables declaration//GEN-END:variables
}
