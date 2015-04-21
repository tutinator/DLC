/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabajopractico;

import clases.Gestor;
import java.io.File;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Santi
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    private Gestor gestor;
    /**
     * Creates new form VentanaPrincipal1
     */
    public VentanaPrincipal() {
        initComponents();
        gestor = new Gestor(this);
        gestor.cargarGrilla(grid_grilla);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btm_procesar = new javax.swing.JButton();
        txt_documento = new javax.swing.JTextField();
        btm_buscar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid_grilla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Analizador de Textos");
        setMinimumSize(new java.awt.Dimension(585, 490));

        btm_procesar.setText("Procesar");
        btm_procesar.setName(""); // NOI18N
        btm_procesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btm_procesarActionPerformed(evt);
            }
        });

        txt_documento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_documentoActionPerformed(evt);
            }
        });
        txt_documento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_documentoKeyReleased(evt);
            }
        });

        btm_buscar.setText("Buscar");
        btm_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btm_buscarActionPerformed(evt);
            }
        });

        grid_grilla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Palabra", "Cantidad de Documentos", "Frecuencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_grilla.getTableHeader().setReorderingAllowed(false);
        grid_grilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                grid_grillaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(grid_grilla);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btm_procesar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addComponent(txt_documento, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btm_buscar)
                        .addGap(0, 0, 0))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btm_procesar)
                    .addComponent(txt_documento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btm_buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public JTable getGrid_grilla() {
        return grid_grilla;
    }
    

    private void btm_procesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btm_procesarActionPerformed
       gestor = new Gestor(this);
       JFileChooser fc = new JFileChooser();
       FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
      
        fc.setFileFilter(filtro);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(true);
        if(fc.showDialog(this, "Procesar")!= JFileChooser.CANCEL_OPTION)
        {
            VentanaProcesando ventProcesando = new VentanaProcesando(gestor);
            ventProcesando.setVisible(true);
            gestor.setTextosAProcesar(fc.getSelectedFiles());
            gestor.setVentanaProcesamiento(ventProcesando);
            gestor.execute();
            
        }
        
            
            
       
       
    }//GEN-LAST:event_btm_procesarActionPerformed

    private void btm_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btm_buscarActionPerformed
        this.buscar(this.txt_documento.getText());
        
    }//GEN-LAST:event_btm_buscarActionPerformed

    private void txt_documentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_documentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_documentoActionPerformed

    private void txt_documentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_documentoKeyReleased
       this.buscar(this.txt_documento.getText());
    }//GEN-LAST:event_txt_documentoKeyReleased

    private void grid_grillaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grid_grillaMousePressed
        int fila = grid_grilla.rowAtPoint(evt.getPoint());
        String cadena = (String)grid_grilla.getValueAt(fila, 0);
        String frecuencia = (String)grid_grilla.getValueAt(fila, 1);
        if(evt.getClickCount() == 2)
        {
        gestor.traerDatosPalabra(cadena, frecuencia);
        
        }
                
    }//GEN-LAST:event_grid_grillaMousePressed

    private void buscar(String cond)
    {
       RowFilter<Object,Object> filtroPalabras = new RowFilter<Object,Object>() 
       {
        public boolean include(Entry<? extends Object, ? extends Object> entry) 
        {

            if (entry.getStringValue(0).startsWith(cond.toLowerCase())) 
            {
                return true;
            }
      
         return false;
        }
        };
        TableModel model = grid_grilla.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        sorter.setRowFilter(filtroPalabras);
        grid_grilla.setRowSorter(sorter);
        sorter.sort();
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btm_buscar;
    private javax.swing.JButton btm_procesar;
    private javax.swing.JTable grid_grilla;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_documento;
    // End of variables declaration//GEN-END:variables
}
