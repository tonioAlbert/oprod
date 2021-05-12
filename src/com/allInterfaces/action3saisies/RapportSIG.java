/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisies;


import com.classes.action3saisie.Formats;
import com.classes.action3saisie.Querry;
import com.classes.action3saisie.Region;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RAP
 */
public class RapportSIG extends javax.swing.JInternalFrame {
    
    private final String selectRegion = "Séléctionner un région";
    
    String dateDebut = "";
    String dateFin = "";
    String username = "";
    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    private String demarche = "";
    /**
     * Creates new form SaisieParOperateur
     */
    public RapportSIG(String HOST, Integer PORT, String DBNAME, String USER, String PWD, String dem) {
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        
        
        initComponents();
        
        this.j_bouton_exporter_rapport_sig.setEnabled(false);
        
        this.j_comb_demarche.removeAllItems();
        this.j_comb_demarche.addItem(dem);
        this.demarche = dem;
        
        
        this.j_combo_region.removeAllItems();
        
        // RECUPERATION DE TOUS LES REGIONS DANS LA BASE
        
        HashMap<String, String> regions = new Region(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getAllRegions();
        
        if(regions.isEmpty()){
            System.out.println("Impossible de récupérér les regions dans la base de données!");
            System.exit(0);
        }else{
            
            this.j_combo_region.addItem(selectRegion);
            System.out.println("tous les regions : "+ regions.isEmpty());

            Iterator it = regions.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, String> val = (Map.Entry)it.next();
                //System.out.println( " = " + val.getValue().toString());
                this.j_combo_region.addItem(val.getValue().toString());
            }
        
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        j_panel_saisie_par_op = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        j_table_rapport_sig = new javax.swing.JTable();
        j_label_annee_saisie1 = new javax.swing.JLabel();
        j_comb_demarche = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        j_combo_region = new javax.swing.JComboBox<>();
        j_bouton_valider_rapport_sig = new javax.swing.JButton();
        j_bouton_exporter_rapport_sig = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Rapport Traitements SIG");

        j_table_rapport_sig.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        j_table_rapport_sig.setFont(new java.awt.Font("Arial Narrow", 0, 13)); // NOI18N
        j_table_rapport_sig.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Région", "District", "Commune", "non_acheval", "rl_anomalie"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(j_table_rapport_sig);
        if (j_table_rapport_sig.getColumnModel().getColumnCount() > 0) {
            j_table_rapport_sig.getColumnModel().getColumn(3).setResizable(false);
            j_table_rapport_sig.getColumnModel().getColumn(4).setResizable(false);
        }

        j_label_annee_saisie1.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_label_annee_saisie1.setText("Démarche");

        j_comb_demarche.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_comb_demarche.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner une démarche", "OGCF", "OCFM" }));

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jLabel2.setText("Région");

        j_combo_region.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_combo_region.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner un région" }));
        j_combo_region.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                j_combo_regionItemStateChanged(evt);
            }
        });

        j_bouton_valider_rapport_sig.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_bouton_valider_rapport_sig.setText("Valider");
        j_bouton_valider_rapport_sig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_bouton_valider_rapport_sigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout j_panel_saisie_par_opLayout = new javax.swing.GroupLayout(j_panel_saisie_par_op);
        j_panel_saisie_par_op.setLayout(j_panel_saisie_par_opLayout);
        j_panel_saisie_par_opLayout.setHorizontalGroup(
            j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(j_panel_saisie_par_opLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j_label_annee_saisie1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(j_comb_demarche, 0, 222, Short.MAX_VALUE)
                    .addComponent(j_combo_region, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                .addGap(23, 23, 23))
            .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(j_panel_saisie_par_opLayout.createSequentialGroup()
                    .addGap(124, 124, 124)
                    .addComponent(j_bouton_valider_rapport_sig, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(935, Short.MAX_VALUE)))
        );
        j_panel_saisie_par_opLayout.setVerticalGroup(
            j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, j_panel_saisie_par_opLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_comb_demarche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_annee_saisie1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_combo_region, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, j_panel_saisie_par_opLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, j_panel_saisie_par_opLayout.createSequentialGroup()
                    .addContainerGap(176, Short.MAX_VALUE)
                    .addComponent(j_bouton_valider_rapport_sig, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(83, 83, 83)))
        );

        j_bouton_exporter_rapport_sig.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_bouton_exporter_rapport_sig.setText("Exporter");
        j_bouton_exporter_rapport_sig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_bouton_exporter_rapport_sigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(j_panel_saisie_par_op, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(j_bouton_exporter_rapport_sig, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(j_panel_saisie_par_op, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(j_bouton_exporter_rapport_sig)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void j_bouton_exporter_rapport_sigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_bouton_exporter_rapport_sigActionPerformed
        
      
        
    }//GEN-LAST:event_j_bouton_exporter_rapport_sigActionPerformed

    private void j_combo_regionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_j_combo_regionItemStateChanged


    }//GEN-LAST:event_j_combo_regionItemStateChanged

    private void j_bouton_valider_rapport_sigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_bouton_valider_rapport_sigActionPerformed

        
        List <String[]> traitements;
        Object rowData[] = new Object[5];
        
        
        String selected_region = (String)this.j_combo_region.getSelectedItem();
        
        if(selected_region.equals(selectRegion)){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner une région","Séléction Région", JOptionPane.INFORMATION_MESSAGE);
            this.j_bouton_exporter_rapport_sig.setEnabled(false);
            //this.setAlwaysOnTop(true);
        }else{
            
                        traitements  = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getRapportSIG(selected_region, Formats.ConvertOcfmToOcm(this.demarche));
                        
                        DefaultTableModel tableau = (DefaultTableModel) j_table_rapport_sig.getModel();
                        
                        if(traitements.size() <1){
                        
                            System.out.println("Aucune résultat à été trouvé !");
                            JOptionPane.showMessageDialog(null, "Aucune résultat à été trouvé !","Aucun résultat trouvé", JOptionPane.INFORMATION_MESSAGE);
                      
                        }else{

                            // réinisialisation des champs
                            Formats.resetTable(tableau);


                            // BOUCLE POUR INSERTION DES DONNEES DANS LE TABLEAU
                            for (int a = 0; a < traitements.size(); a++) {

                                rowData[0] = traitements.get(a)[0].toUpperCase();
                                rowData[1] = traitements.get(a)[1];
                                rowData[2] = traitements.get(a)[2];
                                rowData[3] = traitements.get(a)[3];
                                rowData[4] = traitements.get(a)[4];
                                tableau.addRow(rowData);
                            }
                            
                            this.j_bouton_exporter_rapport_sig.setEnabled(true);
                        }
            
        }
        
    }//GEN-LAST:event_j_bouton_valider_rapport_sigActionPerformed

 
    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton j_bouton_exporter_rapport_sig;
    private javax.swing.JButton j_bouton_valider_rapport_sig;
    private javax.swing.JComboBox<String> j_comb_demarche;
    private javax.swing.JComboBox<String> j_combo_region;
    private javax.swing.JLabel j_label_annee_saisie1;
    private javax.swing.JPanel j_panel_saisie_par_op;
    private javax.swing.JTable j_table_rapport_sig;
    // End of variables declaration//GEN-END:variables
}
