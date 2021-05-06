/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisies;


/**
 *
 * @author RAP
 */
public class ModificationConfigurationBaseDeDonnees extends javax.swing.JInternalFrame {

    
    private String BDD_HOST = "";
    private Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    
    
    /**
     * Creates new form UserFormDialog
     */
    public ModificationConfigurationBaseDeDonnees(String host, Integer port, String dbname, String user, String password) {
            
        this.BDD_HOST = host;
        this.BDD_PORT = port;
        this.BDD_DBNAME = dbname;
        this.BDD_USER = user;
        this.BDD_PWD = password;
        
        System.out.println("host  = " + host);
        System.out.println("port  = " + port);
        System.out.println("dbname  = " + dbname);
        System.out.println("user  = " + user);
        System.out.println("password  = " + password);
        
        
    
        initComponents();
        
        
                
        this.txt_username.setText(user);
        this.txt_nom_bdd.setText(dbname);
        this.txt_nom_hote.setText(host);
        this.txt_port.setText(port.toString());
        this.txt_password.setText(password);
            

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
        btn_annuler = new javax.swing.JButton();
        btn_valider = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        j_label_nom_hote = new javax.swing.JLabel();
        j_label_port = new javax.swing.JLabel();
        j_label_nom_bdd = new javax.swing.JLabel();
        txt_port = new javax.swing.JTextField();
        txt_password = new javax.swing.JTextField();
        txt_nom_hote = new javax.swing.JTextField();
        txt_nom_bdd = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modification configuration Base de données");
        setName("jf_login"); // NOI18N

        txt_username.setToolTipText("Nom d'utilisateur");

        btn_annuler.setText("Annuler");
        btn_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annulerActionPerformed(evt);
            }
        });
        btn_annuler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_annulerKeyPressed(evt);
            }
        });

        btn_valider.setText("Valider");
        btn_valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validerActionPerformed(evt);
            }
        });
        btn_valider.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_validerKeyPressed(evt);
            }
        });

        jLabel1.setText("Nom d'utilisateur");

        jLabel2.setText("Mot de passe");

        j_label_nom_hote.setText("Nom d'hôte");

        j_label_port.setText("Port");

        j_label_nom_bdd.setText("Nom Base de données");

        txt_port.setToolTipText("Nom d'utilisateur");

        txt_password.setToolTipText("Nom d'utilisateur");

        txt_nom_hote.setToolTipText("Nom d'utilisateur");

        txt_nom_bdd.setToolTipText("Nom d'utilisateur");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(302, Short.MAX_VALUE)
                .addComponent(btn_valider)
                .addGap(62, 62, 62)
                .addComponent(btn_annuler)
                .addGap(28, 28, 28))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(j_label_nom_bdd, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(txt_nom_bdd))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(j_label_port, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(txt_port))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j_label_nom_hote, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_username)
                            .addComponent(txt_password)
                            .addComponent(txt_nom_hote))))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_label_nom_hote)
                    .addComponent(txt_nom_hote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_label_port)
                    .addComponent(txt_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j_label_nom_bdd)
                    .addComponent(txt_nom_bdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_annuler)
                    .addComponent(btn_valider))
                .addGap(43, 43, 43))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        //this.j_menu_parametres.

        //this.getParent().getParent().getParent().getParent().getParent().setBackground(Color.red);
        
    }//GEN-LAST:event_btn_annulerActionPerformed


    
    
    private void btn_validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validerActionPerformed
        


    }//GEN-LAST:event_btn_validerActionPerformed

    private void btn_validerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_validerKeyPressed


    }//GEN-LAST:event_btn_validerKeyPressed

    private void btn_annulerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_annulerKeyPressed
        
    }//GEN-LAST:event_btn_annulerKeyPressed

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
            java.util.logging.Logger.getLogger(ModificationConfigurationBaseDeDonnees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificationConfigurationBaseDeDonnees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificationConfigurationBaseDeDonnees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificationConfigurationBaseDeDonnees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                

                
                System.out.println("Tonga ato ny lozakaaaa");
                
                //new UserFormDialog(HOST, PORT, DBNAME, USER, PWD).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_annuler;
    private javax.swing.JButton btn_valider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel j_label_nom_bdd;
    private javax.swing.JLabel j_label_nom_hote;
    private javax.swing.JLabel j_label_port;
    private javax.swing.JTextField txt_nom_bdd;
    private javax.swing.JTextField txt_nom_hote;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_port;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}