/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisies;

import com.classes.action3saisie.Formats;
import com.classes.action3saisie.Hash;
import com.classes.action3saisie.Utilisateurs;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 *
 * @author RAP
 */
public class UserFormDialog extends javax.swing.JFrame {
    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    
    private String placheHolder = "Tapez ici votre nom d'utilisateur";
    
    
    /**
     * Creates new form UserFormDialog
     */
    public UserFormDialog(String host, Integer port, String dbname, String user, String password, List<String> demarches) {
        
        this.BDD_HOST = host;
        this.BDD_PORT = port;
        this.BDD_DBNAME = dbname;
        this.BDD_USER = user;
        this.BDD_PWD = password;
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        initComponents();
        
        if(demarches.isEmpty() || demarches == null){
            System.out.println("Impossible de lancer le programme car la table type_op est vide");
            System.exit(0);
        }else{
            this.j_combo_demarche.removeAllItems();
            this.j_combo_demarche.addItem("Séléctionner une démarche");
            for(String dem : demarches) {
                //System.out.println("Pouf valeur de demande : " + dmd.toUpperCase());
                this.j_combo_demarche.addItem(Formats.ConvertOcmToOcfm(dem));
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

        jPanel1 = new javax.swing.JPanel();
        txt_username = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btn_connexion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        j_combo_demarche = new javax.swing.JComboBox<>();
        j_label_demarche = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Se Connecter");
        setName("jf_login"); // NOI18N

        txt_username.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txt_username.setText("gaetan");
        txt_username.setToolTipText("Nom d'utilisateur");
        txt_username.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_usernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_usernameFocusLost(evt);
            }
        });
        txt_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_usernameKeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jButton1.setText("Quitter");
        jButton1.setMaximumSize(new java.awt.Dimension(89, 33));
        jButton1.setMinimumSize(new java.awt.Dimension(89, 33));
        jButton1.setPreferredSize(new java.awt.Dimension(89, 33));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        btn_connexion.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        btn_connexion.setText("Connexion");
        btn_connexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connexionActionPerformed(evt);
            }
        });
        btn_connexion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_connexionKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jLabel1.setText("Nom d'utilisateur");

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jLabel2.setText("Mot de passe");

        txt_password.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txt_password.setText("2021+");
        txt_password.setToolTipText("Mot de passe");
        txt_password.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_passwordFocusLost(evt);
            }
        });
        txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passwordKeyPressed(evt);
            }
        });

        j_combo_demarche.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_combo_demarche.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Séléctionner une démarche" }));
        j_combo_demarche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                j_combo_demarcheKeyPressed(evt);
            }
        });

        j_label_demarche.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_label_demarche.setText("Démarche");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_demarche, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_username)
                    .addComponent(j_combo_demarche, javax.swing.GroupLayout.Alignment.LEADING, 0, 250, Short.MAX_VALUE)
                    .addComponent(txt_password))
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(btn_connexion)
                .addGap(79, 79, 79)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_combo_demarche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_label_demarche))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_connexion)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void ChargeLogin(){
        
        String txt_username = this.txt_username.getText();
        String txt_password = String.valueOf(this.txt_password.getPassword());
        //String demarche = this.j_combo_demarche.getSelectedItem().toString();
        String demarche = this.j_combo_demarche.getSelectedItem().toString();
        String txt_demarche = "Séléctionner une démarche";
        
        String hash_txt_password = "";
        
        if(txt_username.equals("") && txt_password.equals("")){
            
            System.out.println("Le champ Nom d'utilisateur et mot de passe sont tous requises !");
            JOptionPane.showMessageDialog(null, "Le champ Nom d'utilisateur et mot de passe sont tous requises !","Tous le champs sont requises", JOptionPane.INFORMATION_MESSAGE); 
            
        }else if(txt_username.equals("") || txt_username.equals(placheHolder)){
            
            System.out.println("Champ nom d'utilisateur requise !");
            JOptionPane.showMessageDialog(null, "Le champ Nom d'utilisateur est requise !","Champ nom d'utilisateur requise", JOptionPane.INFORMATION_MESSAGE);
            
        }else if(txt_password.equals("")){
            System.out.println("Champ mot de passe requise !");
            JOptionPane.showMessageDialog(null, "Le champ mot de passe est requise !","Champ mot de passe requise", JOptionPane.INFORMATION_MESSAGE);
            
        }else if(demarche.equals(txt_demarche)){
            System.out.println("Veuillez sélectionner une démarche");
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une démarche","Aucune selectione du champ démarche", JOptionPane.INFORMATION_MESSAGE);
        }else{
            
            
            System.out.println("démarche selectionné = " + txt_demarche);
        
            Utilisateurs user = new Utilisateurs(this.BDD_HOST, this.BDD_DBNAME, this.BDD_PORT, this.BDD_USER, this.BDD_PWD);
            String user_login_bdd = user.getLogin(txt_username);
            String user_mot_de_passe_bdd = user.getPassword();
            String user_id_profil_bdd = user.getProfil();


            hash_txt_password = Hash.getHash(txt_password.getBytes(), "MD5");
        
            if(user_login_bdd == null){
                System.out.println("Nom d'utilisateur n'existe pas dans la base de données !");
                JOptionPane.showMessageDialog(null, "Nom d'utilisateur n'existe pas dans la base de données !","Nom d'utilisateur incorrect et/ou introuvable", JOptionPane.INFORMATION_MESSAGE); 
            }else{
            
                if (hash_txt_password.equals(user_mot_de_passe_bdd)){
                    
                SwingUtilities.invokeLater(() -> {
                        Home home = new Home(this.BDD_HOST, this.BDD_DBNAME, this.BDD_PORT, this.BDD_USER, this.BDD_PWD, txt_username, txt_password, Formats.ConvertOcmToOcfm(demarche), user_id_profil_bdd);
                        home.setVisible(true);
                        this.setVisible(false);
                });


                }else{
                    System.out.print("Mot de passe saisie incorrect !");
                    JOptionPane.showMessageDialog(null, "Mot de passe saisie incorrect !","Erreur Mot de passe", JOptionPane.INFORMATION_MESSAGE); 
                }

            }
        }
    }
    private void btn_connexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connexionActionPerformed
        
        this.ChargeLogin();

    }//GEN-LAST:event_btn_connexionActionPerformed

    private void txt_usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usernameKeyPressed
        
        if(evt.getKeyCode() == 10){
        
            this.ChargeLogin();

        }else if(evt.getKeyCode() == 27){
        
            System.exit(0);

        }else{
            
            System.out.println("Autres touche touché ..." + evt.getKeyCode());
        }
    }//GEN-LAST:event_txt_usernameKeyPressed

    private void btn_connexionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_connexionKeyPressed

        if(evt.getKeyCode() == 10){
        
            this.ChargeLogin();

        }else if(evt.getKeyCode() == 27){
        
            System.exit(0);

        }else{
            
            System.out.println("Autres touche touché ..." + evt.getKeyCode());
        }
    }//GEN-LAST:event_btn_connexionKeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if(evt.getKeyCode() == 10 || evt.getKeyCode() == 27){
        
            System.exit(0);

        }else{
            
            System.out.println("Autres touche touché ..." + evt.getKeyCode());
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void txt_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passwordKeyPressed
        if(evt.getKeyCode() == 10){
        
            this.ChargeLogin();

        }else if(evt.getKeyCode() == 27){
        
            System.exit(0);

        }else{
            
            System.out.println("Autres touche touché ..." + evt.getKeyCode());
        }    
    }//GEN-LAST:event_txt_passwordKeyPressed

    private void j_combo_demarcheKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_j_combo_demarcheKeyPressed
        if(evt.getKeyCode() == 10){
        
            this.ChargeLogin();

        }else if(evt.getKeyCode() == 27){
        
            System.exit(0);

        }else{
            
            System.out.println("Autres touche touché ..." + evt.getKeyCode());
        }
    }//GEN-LAST:event_j_combo_demarcheKeyPressed

    private void txt_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusGained

        if(this.txt_username.getText().equals(placheHolder)){
            this.txt_username.setText("");
            //this.txt_username.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_usernameFocusGained

    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost
        if(this.txt_username.getText().equals("")){
            this.txt_username.setText(placheHolder);
            //this.txt_username.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_txt_usernameFocusLost

    private void txt_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusGained


    }//GEN-LAST:event_txt_passwordFocusGained

    private void txt_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusLost

    }//GEN-LAST:event_txt_passwordFocusLost

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFormDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_connexion;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> j_combo_demarche;
    private javax.swing.JLabel j_label_demarche;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
