/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisies;


import com.classes.action3saisie.Formats;
import com.classes.action3saisie.Querry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RAP
 */
public class SaisieParOperateur extends javax.swing.JInternalFrame {
    
    private final String comb_demarche = "Séléctionner une démarche";
    private final String com_critere_date = "Séléctionner une critère";
    private final String com_critere_par_date = "Par Date";
    private final String com_critere_intervale_de_date = "Intervale de date";
    private final String com_par_login = "Par Utilisateurs";
    private final String com_select_username = "Séléctionner un nom d'utilisateur";
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
    public SaisieParOperateur(String HOST, Integer PORT, String DBNAME, String USER, String PWD, String DEM) {
        
        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;

        
        initComponents();
        
        this.j_date_debut.setEnabled(false);
        this.j_comb_demarche.removeAllItems();
        this.j_comb_demarche.addItem(DEM);
        this.demarche = DEM;
        
        
        this.j_label_login.setVisible(false);
        this.j_comb_login.setVisible(false);
        
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
        j_table_saisie_par_operateur = new javax.swing.JTable();
        j_label_date_saisie = new javax.swing.JLabel();
        j_date_debut = new com.toedter.calendar.JDateChooser();
        j_label_annee_saisie1 = new javax.swing.JLabel();
        j_comb_demarche = new javax.swing.JComboBox<>();
        j_bouton_search = new javax.swing.JButton();
        j_button_reset = new javax.swing.JButton();
        j_label_date_saisie1 = new javax.swing.JLabel();
        j_date_fin = new com.toedter.calendar.JDateChooser();
        j_comb_select_critere_date = new javax.swing.JComboBox<>();
        j_label_annee_saisie2 = new javax.swing.JLabel();
        j_comb_login = new javax.swing.JComboBox<>();
        j_label_login = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Recherche saisie(s) des opérateurs");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/ressources/logo geox2~2.png"))); // NOI18N

        j_table_saisie_par_operateur.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        j_table_saisie_par_operateur.setFont(new java.awt.Font("Arial Narrow", 0, 13)); // NOI18N
        j_table_saisie_par_operateur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Opérateur", "Commune", "Nombre de Saisie"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(j_table_saisie_par_operateur);
        if (j_table_saisie_par_operateur.getColumnModel().getColumnCount() > 0) {
            j_table_saisie_par_operateur.getColumnModel().getColumn(2).setResizable(false);
        }

        j_label_date_saisie.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_label_date_saisie.setText("Date de début");

        j_date_debut.setDateFormatString("dd/MM/yyyy");
        j_date_debut.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N

        j_label_annee_saisie1.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_label_annee_saisie1.setText("Démarche");

        j_comb_demarche.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_comb_demarche.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner une démarche", "OGCF", "OCFM" }));

        j_bouton_search.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_bouton_search.setText("Rechercher");
        j_bouton_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        j_bouton_search.setMaximumSize(new java.awt.Dimension(67, 33));
        j_bouton_search.setMinimumSize(new java.awt.Dimension(67, 33));
        j_bouton_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_bouton_searchActionPerformed(evt);
            }
        });

        j_button_reset.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_button_reset.setText("Réinitialiser");
        j_button_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        j_button_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_button_resetActionPerformed(evt);
            }
        });

        j_label_date_saisie1.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_label_date_saisie1.setText("Date de fin");

        j_date_fin.setDateFormatString("dd/MM/yyyy");
        j_date_fin.setEnabled(false);
        j_date_fin.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N

        j_comb_select_critere_date.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_comb_select_critere_date.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner une critère", "Par Date", "Intervale de date", "Par Utilisateurs" }));
        j_comb_select_critere_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_comb_select_critere_dateActionPerformed(evt);
            }
        });

        j_label_annee_saisie2.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_label_annee_saisie2.setText("Critères");

        j_comb_login.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_comb_login.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner un nom d'utilisateur" }));

        j_label_login.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_label_login.setText("Sélectionner un Login");

        javax.swing.GroupLayout j_panel_saisie_par_opLayout = new javax.swing.GroupLayout(j_panel_saisie_par_op);
        j_panel_saisie_par_op.setLayout(j_panel_saisie_par_opLayout);
        j_panel_saisie_par_opLayout.setHorizontalGroup(
            j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(j_panel_saisie_par_opLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j_label_date_saisie1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_annee_saisie1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_annee_saisie2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_date_saisie, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_login, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(j_panel_saisie_par_opLayout.createSequentialGroup()
                        .addComponent(j_bouton_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(j_button_reset))
                    .addComponent(j_comb_login, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_date_fin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(j_date_debut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(j_comb_demarche, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(j_comb_select_critere_date, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        j_panel_saisie_par_opLayout.setVerticalGroup(
            j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(j_panel_saisie_par_opLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_comb_demarche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_annee_saisie1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_comb_select_critere_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_annee_saisie2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j_label_date_saisie)
                    .addComponent(j_date_debut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j_label_date_saisie1)
                    .addComponent(j_date_fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_comb_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_login))
                .addGap(43, 43, 43)
                .addGroup(j_panel_saisie_par_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_bouton_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_button_reset))
                .addGap(46, 46, 46))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(j_panel_saisie_par_op, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

 
    
    private void j_bouton_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_bouton_searchActionPerformed
        
        List <String[]> saisieDesOperateurs;
        Object rowData[] = new Object[3];
        
        String demarcheFormater = Formats.ConvertOcfmToOcm((String)this.j_comb_demarche.getSelectedItem());
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        String val_critere = this.j_comb_select_critere_date.getSelectedItem().toString();
        
        if(demarche.equals(comb_demarche)){
            
            System.out.println("Veuillez séléctionner une démarche");
            JOptionPane.showMessageDialog(null, "Veuillez séléctionner une démarche !","Séléctionner une démarche", JOptionPane.INFORMATION_MESSAGE);

        }else{
            
            if(val_critere.equals(com_critere_date)){
                System.out.println("Veuillez séléctionner une critère de date");
                JOptionPane.showMessageDialog(null, "Veuillez séléctionner une critère de date!","Séléctionner critère de date", JOptionPane.INFORMATION_MESSAGE);
            }else{
                         
                if(val_critere.equals(com_critere_par_date)){

                    if(this.j_date_debut.getDate() == null){
                        System.out.println("Veuillez remplir le date fin");
                        JOptionPane.showMessageDialog(null, "Date début de saisie vide ou incorrect !","Erreur lors remplissage date de début de saisie", JOptionPane.INFORMATION_MESSAGE); 
                    }else{
                        

                        saisieDesOperateurs  = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getSimpleSaisieParOperateur( this.j_date_debut, demarcheFormater);

                        DefaultTableModel tableau = (DefaultTableModel) j_table_saisie_par_operateur.getModel();

                        
                        if(saisieDesOperateurs.size() <1){
                        
                            System.out.println("Aucune saisie à été trouvé !");
                            JOptionPane.showMessageDialog(null, "Aucune saisie à été trouvé !","Aucun résultat trouvé", JOptionPane.INFORMATION_MESSAGE); 
                      
                        }else{
                            
                            Formats.resetTable(tableau);

                            // BOUCLE POUR INSERTION DES DONNEES DANS LE TABLEAU
                            for (int a = 0; a < saisieDesOperateurs.size(); a++) {

                                rowData[0] = saisieDesOperateurs.get(a)[0].toUpperCase();
                                rowData[1] = saisieDesOperateurs.get(a)[1];
                                rowData[2] = saisieDesOperateurs.get(a)[2];
                                tableau.addRow(rowData);
                            }
                        }
                                

                    }

                     
                }else if(val_critere.equals(com_critere_intervale_de_date)){

                    if(this.j_date_debut.getDate() == null && this.j_date_fin.getDate() == null){
                        System.out.println("Veuillez remplir les dates (début et fin) de saisie");
                        JOptionPane.showMessageDialog(null, "Dates (début et/ou fin) de saisie vide ou incorrect !","Séléctionner les dates de saisie", JOptionPane.INFORMATION_MESSAGE); 
                    }else if(this.j_date_debut.getDate() == null){
                        System.out.println("Veuillez remplir le date début");
                        JOptionPane.showMessageDialog(null, "Date début de saisie vide ou incorrect !","Erreur lors remplissage date début de saisie", JOptionPane.INFORMATION_MESSAGE); 
                    }else if(this.j_date_fin.getDate() == null){
                        System.out.println("Veuillez remplir le date fin");
                        JOptionPane.showMessageDialog(null, "Date fin de saisie vide ou incorrect !","SErreur lors remplissage date Fin de saisie", JOptionPane.INFORMATION_MESSAGE); 
                    }else{
                        
                        saisieDesOperateurs  = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getSaisieParOperateurBetweenTwoDate(this.j_date_debut, this.j_date_fin, demarcheFormater);
                        
                        DefaultTableModel tableau = (DefaultTableModel) j_table_saisie_par_operateur.getModel();
                        
                        if(saisieDesOperateurs.size() <1){
                        
                            System.out.println("Aucune saisie à été trouvé !");
                            JOptionPane.showMessageDialog(null, "Aucune saisie à été trouvé !","Aucun résultat trouvé", JOptionPane.INFORMATION_MESSAGE);
                      
                        }else{

                            // réinisialisation des cjhamps
                            Formats.resetTable(tableau);


                            // BOUCLE POUR INSERTION DES DONNEES DANS LE TABLEAU
                            for (int a = 0; a < saisieDesOperateurs.size(); a++) {

                                rowData[0] = saisieDesOperateurs.get(a)[0].toUpperCase();
                                rowData[1] = saisieDesOperateurs.get(a)[1];
                                rowData[2] = saisieDesOperateurs.get(a)[2];
                                tableau.addRow(rowData);
                            }
                        }
                        
                    }
                    
                    
                }else{
                    
                    String selected = this.j_comb_login.getSelectedItem().toString();
                    
                    if(selected.equals(com_select_username)){
                            System.out.println("Aucun Nom d'utilisateur sélectionné !");
                            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un nom d'utilisateur !","Aucun nom d'utilisateur sélectionné", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        
                        username = selected.split("  _  ")[1];
                        saisieDesOperateurs  = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getSaisieWithLogin(demarcheFormater, username);
                        
                        if(saisieDesOperateurs.size() <1){
                        
                            System.out.println("Aucune saisie à été trouvé !");
                            JOptionPane.showMessageDialog(null, "Aucune saisie à été effectué par l'utilisateur : "+username.toUpperCase()+" pendant l'opération "+this.demarche.toUpperCase()+" !","Aucune saisie trouvé", JOptionPane.INFORMATION_MESSAGE);
                      
                        }else{
                            
                            DefaultTableModel tableau = (DefaultTableModel) j_table_saisie_par_operateur.getModel();
                            
                            Formats.resetTable(tableau);


                            // BOUCLE POUR INSERTION DES DONNEES DANS LE TABLEAU
                            for (int a = 0; a < saisieDesOperateurs.size(); a++) {

                                rowData[0] = saisieDesOperateurs.get(a)[0].toUpperCase();
                                rowData[1] = saisieDesOperateurs.get(a)[1];
                                rowData[2] = saisieDesOperateurs.get(a)[2];
                                tableau.addRow(rowData);
                            }
                        }
                    }
                    

                }

            }
        
        }
    
    }//GEN-LAST:event_j_bouton_searchActionPerformed

    private void j_comb_select_critere_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_comb_select_critere_dateActionPerformed
        
        this.j_date_debut.setEnabled(false);
        this.j_date_fin.setEnabled(false);
        String selectedValue = this.j_comb_select_critere_date.getSelectedItem().toString();
        
        //System.out.println("selectedValue vaut : " + selectedValue);
        
        
        switch(selectedValue){
            
            case  com_critere_par_date : 

                this.j_date_fin.setCalendar(null);
                this.j_date_fin.setEnabled(false);
                
                
                this.j_date_debut.setCalendar(null);
                this.j_date_debut.setEnabled(true);
                
                
                this.j_label_login.setVisible(false);
                this.j_comb_login.setVisible(false);
                
                break;
                
            case  com_critere_intervale_de_date : 
                
                this.j_date_debut.setEnabled(true);
                this.j_date_fin.setEnabled(true);
                
                this.j_date_fin.setCalendar(null);
                this.j_date_debut.setCalendar(null);
                
                this.j_label_login.setVisible(false);
                this.j_comb_login.setVisible(false);
                break;
               
            // SI LA SELECTION EST UNE LOGIN ALORS : 
            case com_par_login :
                
                this.j_date_debut.setDate(null);
                this.j_date_debut.setEnabled(false);

                this.j_date_fin.setDate(null);
                this.j_date_fin.setEnabled(false);
                
                this.j_comb_login.removeAllItems();
                

                this.j_comb_login.addItem(com_select_username);
                    
                    
                HashMap<String, String> users = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getAllUsers();

                for (String i : users.keySet()) {
                    this.j_comb_login.addItem("Id : "+ i + "  _  " + users.get(i));
                    //System.out.println("IDDDDD : " + users.get(i));
                 } 
                this.j_label_login.setVisible(true);
                this.j_comb_login.setVisible(true);
                break;
                
                
            default :
                
                this.j_date_fin.setCalendar(null);
                this.j_date_fin.setEnabled(false);
                
                
                this.j_date_debut.setCalendar(null);
                this.j_date_debut.setEnabled(false);
                
                
                this.j_label_login.setVisible(false);
                this.j_comb_login.setVisible(false);
                
                break;
        }
    }//GEN-LAST:event_j_comb_select_critere_dateActionPerformed

    private void j_button_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_button_resetActionPerformed
       
        this.j_date_debut.setDate(null);
        this.j_date_debut.setEnabled(false);
        
        this.j_date_fin.setDate(null);
        this.j_date_fin.setEnabled(false);
        
        this.j_comb_login.setVisible(false);
        this.j_label_login.setVisible(false);
        
        DefaultTableModel my_model = (DefaultTableModel) j_table_saisie_par_operateur.getModel();
        
        for(int i = my_model.getRowCount(); i > 0; --i){
            my_model.removeRow(i-1);  
        }
    }//GEN-LAST:event_j_button_resetActionPerformed


    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton j_bouton_search;
    private javax.swing.JButton j_button_reset;
    private javax.swing.JComboBox<String> j_comb_demarche;
    private javax.swing.JComboBox<String> j_comb_login;
    private javax.swing.JComboBox<String> j_comb_select_critere_date;
    private com.toedter.calendar.JDateChooser j_date_debut;
    private com.toedter.calendar.JDateChooser j_date_fin;
    private javax.swing.JLabel j_label_annee_saisie1;
    private javax.swing.JLabel j_label_annee_saisie2;
    private javax.swing.JLabel j_label_date_saisie;
    private javax.swing.JLabel j_label_date_saisie1;
    private javax.swing.JLabel j_label_login;
    private javax.swing.JPanel j_panel_saisie_par_op;
    private javax.swing.JTable j_table_saisie_par_operateur;
    // End of variables declaration//GEN-END:variables
}
