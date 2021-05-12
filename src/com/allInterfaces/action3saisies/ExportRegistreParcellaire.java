/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisies;

import com.classes.action3saisie.Formats;
import com.connectDb.ConnectDb;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import com.classes.action3saisie.Querry;
import com.classes.action3saisie.Region;
import com.export.action3saisie.Exports;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;



/**
 *
 * @author RAP
 */
public class ExportRegistreParcellaire extends javax.swing.JInternalFrame {
    
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
    
    private String type_operation = "";
    
    
    /**
     * Creates new form SaisieParOperateur
     */
    public ExportRegistreParcellaire(String HOST, Integer PORT, String DBNAME, String USER, String PWD, String TYPE_OPERATION) {
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        this.type_operation = TYPE_OPERATION;
        
        initComponents();

        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "C@seF&Ge0X2", "postgres").getConnection();
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

        jLabel2 = new javax.swing.JLabel();
        j_combo_region = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        j_combo_district = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        j_combo_commune = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        j_label_folder_export = new javax.swing.JTextField();
        j_button_folder_export = new javax.swing.JButton();
        j_button_exporter_rp_provisoire = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Exportation Registre Parcellaire Provisoire");
        setMinimumSize(new java.awt.Dimension(543, 380));
        setPreferredSize(new java.awt.Dimension(543, 380));

        jLabel2.setText("Région");

        j_combo_region.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner un région" }));
        j_combo_region.setMinimumSize(new java.awt.Dimension(151, 27));
        j_combo_region.setPreferredSize(new java.awt.Dimension(151, 27));
        j_combo_region.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                j_combo_regionItemStateChanged(evt);
            }
        });

        jLabel3.setText("District");

        j_combo_district.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner un district" }));
        j_combo_district.setEnabled(false);
        j_combo_district.setMinimumSize(new java.awt.Dimension(151, 27));
        j_combo_district.setPreferredSize(new java.awt.Dimension(151, 27));
        j_combo_district.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                j_combo_districtItemStateChanged(evt);
            }
        });

        jLabel4.setText("Commune");

        j_combo_commune.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner une commune" }));
        j_combo_commune.setEnabled(false);
        j_combo_commune.setMinimumSize(new java.awt.Dimension(151, 27));
        j_combo_commune.setPreferredSize(new java.awt.Dimension(151, 27));

        jLabel7.setText("Emplacement de l'export");

        j_label_folder_export.setEditable(false);
        j_label_folder_export.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        j_label_folder_export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                j_label_folder_exportMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                j_label_folder_exportMouseEntered(evt);
            }
        });

        j_button_folder_export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ressources/Webp.net-resizeimage.png"))); // NOI18N
        j_button_folder_export.setBorderPainted(false);
        j_button_folder_export.setContentAreaFilled(false);
        j_button_folder_export.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        j_button_folder_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_button_folder_exportActionPerformed(evt);
            }
        });

        j_button_exporter_rp_provisoire.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_button_exporter_rp_provisoire.setText("Exporter");
        j_button_exporter_rp_provisoire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_button_exporter_rp_provisoireActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(j_combo_commune, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(j_combo_region, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(j_combo_district, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(112, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(j_label_folder_export))
                        .addGap(18, 18, 18)
                        .addComponent(j_button_folder_export, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(j_button_exporter_rp_provisoire, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(202, 202, 202))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
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
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(j_label_folder_export, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(j_button_folder_export, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(j_button_exporter_rp_provisoire, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
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
            }else{

                this.j_combo_district.removeAllItems();
                this.j_combo_district.addItem(selectDistrict);

                HashMap<String, String> reg = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getRegions(selected);

                for (String i : reg.keySet()) {
                    this.j_combo_district.addItem( i + "  _  " + reg.get(i));
                }

                this.j_combo_district.setEnabled(true);
                this.j_combo_commune.setEnabled(false);
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
            }else{

                this.j_combo_commune.setEnabled(false);

                if(containsStr){

                    this.j_combo_commune.removeAllItems();
                    this.j_combo_commune.addItem(selectCommune);
                    HashMap<String, String> com = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER).getCommunes(selected.split("  _  ")[1]);

                    for (String i : com.keySet()) {
                        this.j_combo_commune.addItem( i + "  _  " + com.get(i));
                    }

                    this.j_combo_commune.setEnabled(true);
                }

            }
        }
    }//GEN-LAST:event_j_combo_districtItemStateChanged
    
    private void GetLocationToSaveFile(){
        
        String locationFile = "";
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = fc.showOpenDialog(this);

        if(response == JFileChooser.APPROVE_OPTION){

            j_label_folder_export.setText(fc.getSelectedFile().toString());

            locationFile += fc.getSelectedFile().toString();

            //System.out.println("LOCATION = " + locationFile );
        }
        //else{

            //j_label_folder_export.setText("");
        //}

        locationFile = "";
    }
    
    
    private void j_button_folder_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_button_folder_exportActionPerformed
        this.GetLocationToSaveFile();
    }//GEN-LAST:event_j_button_folder_exportActionPerformed

    private void j_button_exporter_rp_provisoireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_button_exporter_rp_provisoireActionPerformed

        String selected_region = (String)this.j_combo_region.getSelectedItem();
        String selected_district = (String)this.j_combo_district.getSelectedItem();
        String selected_commune = (String)this.j_combo_commune.getSelectedItem();

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
        }else if(this.j_label_folder_export.getText().equals("")){
            //this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Veuillez selectionner le dossier de destination de l'export","Séléction Emplacement", JOptionPane.INFORMATION_MESSAGE);
            //this.setAlwaysOnTop(true);
        }else{
            

        new SwingWorker(){
            @Override
            protected Object doInBackground() throws Exception{

            //System.out.println("Thread en cours dans export RP = " + Thread.currentThread().getName());

            String code_district = selected_district.split("  _  ")[0];
            String code_commune = selected_commune.split("  _  ")[0];
            String district = selected_district.split("  _  ")[1];
            String commune = selected_commune.split("  _  ")[1];
            

            List reponsePersPhysique = new ArrayList(new Exports(BDD_HOST, BDD_PORT, BDD_DBNAME, BDD_PWD, BDD_USER, Formats.ConvertOcfmToOcm(type_operation).toLowerCase()).getRegistreParcellaireProvisoirePersonnePhysique(selected_region, code_district , district , code_commune , commune , j_label_folder_export.getText()));

            

            String responsePersonnePhysique = (String)reponsePersPhysique.get(0);
            String EmplacementFichierExcelExporterRP = (String)reponsePersPhysique.get(1);
            String responsePersonneMorale = "";
            
            System.out.println("EmplacementFichierExcelExporterRP vaut = "+EmplacementFichierExcelExporterRP);
                        
                        
            if(responsePersonnePhysique.equals("success-personne-physique")){
                
            List reponsePersMorale = new ArrayList(new Exports(BDD_HOST, BDD_PORT,BDD_DBNAME, BDD_PWD, BDD_USER, Formats.ConvertOcfmToOcm(type_operation).toLowerCase()).getRegistreParcellaireProvisoirePersonneMorale(selected_region, code_district , district , code_commune , commune , EmplacementFichierExcelExporterRP));

                int export = JOptionPane.showConfirmDialog(null, "Voulez-vous ouvrir le dossier de l'export du fichier exporté ?", "RP provisoire exporté avec succès !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                    if(export == JOptionPane.YES_OPTION){
                        // ouverture de l'emplacement selectionner par l'utiisateur
                        try {
                            Desktop.getDesktop().open(new File(j_label_folder_export.getText()));
                        }catch(Exception ee){
                            JOptionPane.showMessageDialog(null, "Suppression fichier d'export impossible", "Impossible de supprimer automatiquement le fichier d'export car vous l'avez supprimé manuellement !\n\n"+ee.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

            }else if(responsePersonnePhysique.equals("empty-personne-physique")){
                
                List ReRecuperationRpPersonneMorale = new ArrayList(new Exports(BDD_HOST, BDD_PORT, BDD_DBNAME, BDD_PWD, BDD_USER, Formats.ConvertOcfmToOcm(type_operation).toLowerCase()).getRegistreParcellaireProvisoirePersonneMorale(selected_region, code_district , district , code_commune , commune , EmplacementFichierExcelExporterRP));

                responsePersonneMorale = (String)ReRecuperationRpPersonneMorale.get(0);
                
                if(responsePersonneMorale.equals("success-personne-morale")){
                    
                int export = JOptionPane.showConfirmDialog(null, "Voulez-vous ouvrir le dossier de l'export du fichier exporté ?", "RP provisoire exporté avec succès !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                    if(export == JOptionPane.YES_OPTION){
                        // ouverture de l'emplacement selectionner par l'utiisateur
                        try {
                            Desktop.getDesktop().open(new File(j_label_folder_export.getText()));
                        }catch(Exception ee){
                            JOptionPane.showMessageDialog(null, "Suppression fichier d'export impossible", "Impossible de supprimer automatiquement le fichier d'export car vous l'avez supprimé manuellement !\n\nRetour: "+ee.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                        
                        }
                    }  
                }else if(responsePersonnePhysique.equals("empty-personne-physique") && responsePersonneMorale.equals("empty-personne-morale")){
                    
                try {
                    
                    Files.deleteIfExists(Paths.get(EmplacementFichierExcelExporterRP));
                    JOptionPane.showMessageDialog(null, "Aucun registre parcellaire pret à être exporté sur : \n\ncommune: "+commune+"\n"+"\n"+"Type d'opération : "+type_operation, "Export RP provisoire impossible", JOptionPane.INFORMATION_MESSAGE);
                
                } catch (IOException ex) {
                    //Logger.getLogger(ExportRegistreAnomalie.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Suppression fichier d'export impossible", "Impossible de supprimer automatiquement le fichier d'export car vous l'avez supprimé manuellement !\n\nRetour: "+ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                        
                }
            }
                
                
            }else if(responsePersonnePhysique.equals("empty-personne-physique") && responsePersonneMorale.equals("empty-personne-morale")){
                
                try {
                    Files.deleteIfExists(Paths.get(EmplacementFichierExcelExporterRP));
                    JOptionPane.showMessageDialog(null, "Aucun registre parcellaire pret à être exporté sur : \n\ncommune: "+commune+"\n"+"\n"+"Type d'opération : "+type_operation, "Export RP provisoire impossible", JOptionPane.INFORMATION_MESSAGE);
                
                } catch (IOException ex) {
                    //Logger.getLogger(ExportRegistreAnomalie.class.getName()).log(Level.SEVERE, null, ex);
                    
                            JOptionPane.showMessageDialog(null, "Suppression fichier d'export impossible", "Impossible de supprimer automatiquement le fichier d'export car vous l'avez supprimé manuellement !\n\nRetour: "+ex.getMessage(), JOptionPane.INFORMATION_MESSAGE);
                        
                }
            }
            
            //else{
            //    JOptionPane.showMessageDialog(null, "Aucun registre parcellaire pret à être exporté sur : \n\ncommune: "+commune+"\n"+"\n"+"Type d'opération : "+this.type_operation, "Export RP provisoire impossible", JOptionPane.INFORMATION_MESSAGE);
            //}
                return null;
            }
        }.execute();
            

        }

    }//GEN-LAST:event_j_button_exporter_rp_provisoireActionPerformed

    private void j_label_folder_exportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_label_folder_exportMouseClicked
        //System.out.println("Click sur chemin emplacement...");
        this.GetLocationToSaveFile();
    }//GEN-LAST:event_j_label_folder_exportMouseClicked

    private void j_label_folder_exportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_label_folder_exportMouseEntered
        this.j_label_folder_export.setToolTipText("Cliquer pour séléctionner le dossier de destionation de l'export");
    }//GEN-LAST:event_j_label_folder_exportMouseEntered

 
    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton j_button_exporter_rp_provisoire;
    private javax.swing.JButton j_button_folder_export;
    private javax.swing.JComboBox<String> j_combo_commune;
    private javax.swing.JComboBox<String> j_combo_district;
    private javax.swing.JComboBox<String> j_combo_region;
    private javax.swing.JTextField j_label_folder_export;
    // End of variables declaration//GEN-END:variables
}
