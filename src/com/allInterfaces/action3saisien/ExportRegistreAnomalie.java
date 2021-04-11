/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisien;

import com.connectDb.ConnectDb;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import com.classes.action3saisie.Demande;
import com.dao.action3saisie.Dao;
import java.util.HashMap;
import javax.swing.JOptionPane;



/**
 *
 * @author RAP
 */
public class ExportRegistreAnomalie extends javax.swing.JInternalFrame {
    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    
    private static Connection connectDatabase;
    private static PreparedStatement st;
    private static ResultSet rs;
    
    private final String selectRegion = "Séléctionner un région";
    private final String selectDistrict = "Séléctionner un district";
    private final String selectCommune = "Séléctionner une commune";
    private final String selectFokontany = "Séléctionner une fokontany";
    private final String selectHameau = "Séléctionner un hameau";

    
    
    /**
     * Creates new form SaisieParOperateur
     */
    public ExportRegistreAnomalie(String HOST, Integer PORT, String DBNAME, String USER, String PWD) {
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        
        initComponents();
        
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getConnection();
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "C@seF&Ge0X2", "postgres").getConnection();
        this.j_combo_region.removeAllItems();
        this.j_combo_region.addItem(selectRegion);
        this.j_combo_region.addItem("ATSINANANA");
        this.j_combo_region.addItem("ANALANJIROFO");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        j_combo_region = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        j_combo_district = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        j_combo_commune = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        j_combo_fokontany = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        j_combo_hameau = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        j_label_folder_export = new javax.swing.JTextField();
        j_button_folder_export = new javax.swing.JButton();
        j_button_exporter = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Export Registre Anomalie");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Export Registre Anomalie Saisie");

        jLabel2.setText("Région");

        j_combo_region.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner un région" }));
        j_combo_region.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                j_combo_regionItemStateChanged(evt);
            }
        });

        jLabel3.setText("District");

        j_combo_district.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner un district" }));
        j_combo_district.setEnabled(false);
        j_combo_district.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                j_combo_districtItemStateChanged(evt);
            }
        });

        jLabel4.setText("Commune");

        j_combo_commune.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner une commune" }));
        j_combo_commune.setEnabled(false);
        j_combo_commune.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                j_combo_communeItemStateChanged(evt);
            }
        });

        jLabel5.setText("Fokontany");

        j_combo_fokontany.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner une fokontany" }));
        j_combo_fokontany.setEnabled(false);
        j_combo_fokontany.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                j_combo_fokontanyItemStateChanged(evt);
            }
        });

        jLabel6.setText("Hameau");

        j_combo_hameau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner un hameau" }));
        j_combo_hameau.setEnabled(false);

        jLabel7.setText("Emplacement de l'export");

        j_label_folder_export.setEditable(false);

        j_button_folder_export.setIcon(new javax.swing.ImageIcon("C:\\Users\\RAP\\Documents\\NetBeansProjects\\A3_suivi_saisie\\ressources\\img\\Webp.net-resizeimage.png")); // NOI18N
        j_button_folder_export.setBorderPainted(false);
        j_button_folder_export.setContentAreaFilled(false);
        j_button_folder_export.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        j_button_folder_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_button_folder_exportActionPerformed(evt);
            }
        });

        j_button_exporter.setText("Exporter");
        j_button_exporter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_button_exporterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(j_button_exporter, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(j_label_folder_export)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(j_button_folder_export, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(j_combo_commune, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(j_combo_region, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(j_combo_district, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(j_combo_fokontany, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(j_combo_hameau, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(jLabel1)))
                        .addGap(0, 114, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(274, 274, 274))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_combo_region, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_combo_district, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_combo_commune, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_combo_fokontany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_combo_hameau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(j_label_folder_export, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(j_button_folder_export, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(j_button_exporter, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void j_combo_regionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_j_combo_regionItemStateChanged

        String selected = "";

        if(evt.getStateChange() == ItemEvent.SELECTED){

            selected += evt.getItem();

            if(selected.equals(selectRegion)){
                this.j_combo_district.setEnabled(false);
                this.j_combo_commune.setEnabled(false);
                this.j_combo_fokontany.setEnabled(false);
                this.j_combo_hameau.setEnabled(false);
            }else{

                this.j_combo_district.removeAllItems();
                this.j_combo_district.addItem(selectDistrict);

                HashMap<String, String> reg = new Demande(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getRegions(selected);

                for (String i : reg.keySet()) {
                    this.j_combo_district.addItem( i + "  _  " + reg.get(i));
                }

                this.j_combo_district.setEnabled(true);
                this.j_combo_commune.setEnabled(false);
                this.j_combo_fokontany.setEnabled(false);
                this.j_combo_hameau.setEnabled(false);
            }
        }

    }//GEN-LAST:event_j_combo_regionItemStateChanged

    private void j_combo_districtItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_j_combo_districtItemStateChanged

        String selected = "";

        if(evt.getStateChange() == ItemEvent.SELECTED){

            selected += evt.getItem();
            boolean containsStr = selected.contains("  _  ");

            if(selected.equals(selectCommune)){
                this.j_combo_commune.setEnabled(false);
                this.j_combo_fokontany.setEnabled(false);
                this.j_combo_hameau.setEnabled(false);
            }else{

                this.j_combo_commune.setEnabled(false);

                if(containsStr){

                    this.j_combo_commune.removeAllItems();
                    this.j_combo_commune.addItem(selectCommune);
                    HashMap<String, String> com = new Demande(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getCommunes(selected.split("  _  ")[1]);

                    for (String i : com.keySet()) {
                        this.j_combo_commune.addItem( i + "  _  " + com.get(i));
                    }

                    this.j_combo_commune.setEnabled(true);
                    this.j_combo_fokontany.setEnabled(false);
                    this.j_combo_hameau.setEnabled(false);
                }

            }
        }
    }//GEN-LAST:event_j_combo_districtItemStateChanged

    private void j_combo_communeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_j_combo_communeItemStateChanged
        String selected = "";

        if(evt.getStateChange() == ItemEvent.SELECTED){

            selected += evt.getItem();
            boolean containsStr = selected.contains("  _  ");

            if(selected.equals(selectFokontany)){
                this.j_combo_fokontany.setEnabled(false);
                this.j_combo_hameau.setEnabled(false);
            }else{

                this.j_combo_fokontany.setEnabled(false);

                if(containsStr){

                    this.j_combo_fokontany.removeAllItems();
                    this.j_combo_fokontany.addItem(selectFokontany);
                    HashMap<String, String> com = new Demande(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getFokontany(selected.split("  _  ")[1]);

                    for (String i : com.keySet()) {
                        this.j_combo_fokontany.addItem( i + "  _  " + com.get(i));
                    }

                    this.j_combo_fokontany.setEnabled(true);
                    this.j_combo_hameau.setEnabled(false);
                }

            }
        }
    }//GEN-LAST:event_j_combo_communeItemStateChanged

    private void j_combo_fokontanyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_j_combo_fokontanyItemStateChanged
        String selected = "";

        if(evt.getStateChange() == ItemEvent.SELECTED){

            selected += evt.getItem();
            boolean containsStr = selected.contains("  _  ");

            if(selected.equals(selectHameau)){
                this.j_combo_hameau.setEnabled(false);
            }else{

                this.j_combo_hameau.setEnabled(false);

                if(containsStr){

                    this.j_combo_hameau.removeAllItems();
                    this.j_combo_hameau.addItem(selectHameau);
                    HashMap<String, String> com = new Demande(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getHameau(selected.split("  _  ")[1]);

                    for (String i : com.keySet()) {
                        this.j_combo_hameau.addItem( i + "  _  " + com.get(i));
                    }

                    this.j_combo_hameau.setEnabled(true);
                }

            }
        }
    }//GEN-LAST:event_j_combo_fokontanyItemStateChanged

    private void j_button_folder_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_button_folder_exportActionPerformed

        String locationFile = "";
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = fc.showOpenDialog(this);

        if(response == JFileChooser.APPROVE_OPTION){

            j_label_folder_export.setText(fc.getSelectedFile().toString());

            locationFile += fc.getSelectedFile().toString();

            //System.out.println("LOCATION = " + locationFile );
        }else{

            j_label_folder_export.setText("");
        }

        locationFile = "";

    }//GEN-LAST:event_j_button_folder_exportActionPerformed

    private void j_button_exporterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_button_exporterActionPerformed

        String selected_region = (String)this.j_combo_region.getSelectedItem();
        String selected_district = (String)this.j_combo_district.getSelectedItem();
        String selected_commune = (String)this.j_combo_commune.getSelectedItem();
        String selected_fokontany = (String)this.j_combo_fokontany.getSelectedItem();
        String selected_hameau = (String)this.j_combo_hameau.getSelectedItem();

        if(selected_region.equals(selectRegion)){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner une région","Séléction Région", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
        }else if(selected_district.equals(selectDistrict)){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un district","Séléction District", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
        }else if(selected_commune.equals(selectCommune)){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner une commune","Séléction Commune", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
        }else if(selected_fokontany.equals(selectFokontany)){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un fokontany","Séléction Fokontany", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
        }else if(selected_hameau.equals(selectHameau)){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un hameau","Séléction Hameau", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
        }else if(this.j_label_folder_export.getText().equals("")){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner le dossier de destination de l'export","Séléction Emplacement", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
        }else{
            //this.setAlwaysOnTop(false);
            //JOptionPane.showMessageDialog(null, "Tout est OK","OK", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
            //System.out.println("Export RP en cours ..." );
            
            String code_district = selected_district.split("  _  ")[0].trim();
            String code_commune = selected_commune.split("  _  ")[0].trim();
            String code_fokontany = selected_fokontany.split("  _  ")[0].trim();
            String code_hameau = selected_hameau.split("  _  ")[0].trim();
            
            
            String district = selected_district.split("  _  ")[1].trim();
            String commune = selected_commune.split("  _  ")[1].trim();
            String fokontany = selected_fokontany.split("  _  ")[1].trim();
            String hameau = selected_hameau.split("  _  ")[1].trim();
            
            //System.out.println("Code hameau :" + code_hameau );

            //Boolean RP = new Demande().getRegistreParcellaireProvisoire(selected_region, selected_district.split("  _  ")[1], selected_commune.split("  _  ")[1], this.j_label_folder_export.getText());

            System.out.println("System Dans btn exporter ... "+new Dao().getRegistreAnomalie(selected_region, code_district , district , code_commune , commune , code_fokontany, fokontany , code_hameau, hameau , this.j_label_folder_export.getText()));
        }

    }//GEN-LAST:event_j_button_exporterActionPerformed

 
    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton j_button_exporter;
    private javax.swing.JButton j_button_folder_export;
    private javax.swing.JComboBox<String> j_combo_commune;
    private javax.swing.JComboBox<String> j_combo_district;
    private javax.swing.JComboBox<String> j_combo_fokontany;
    private javax.swing.JComboBox<String> j_combo_hameau;
    private javax.swing.JComboBox<String> j_combo_region;
    private javax.swing.JTextField j_label_folder_export;
    // End of variables declaration//GEN-END:variables
}
