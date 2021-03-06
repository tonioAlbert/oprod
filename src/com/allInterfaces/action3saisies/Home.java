/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allInterfaces.action3saisies;


import com.allInterfaces.action3saisies.Exportations.ExportVectorisationSansSaisie;
import com.allInterfaces.action3saisies.Exportations.ExportRegistreAnomalie;
import com.allInterfaces.action3saisies.Exportations.ExportSaisieSansVectorisation;
import com.allInterfaces.action3saisies.Exportations.ExportRegistreAnomalieCSVLola;
import com.allInterfaces.action3saisies.Exportations.ExportRegistreParcellaire;
import com.allInterfaces.action3saisies.Exportations.ExportCFEditerParCommunes;
import com.classes.action3saisie.Formats;
import com.classes.action3saisie.Querry;
import com.connectDb.ConnectDb;
import com.classes.action3saisie.Utilisateurs;
import com.createForm.tonio.CreateUI;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author RAP
 */
public class Home extends javax.swing.JFrame {
    
    private static Connection connectDatabase;
    private static PreparedStatement st;
    private static ResultSet rs;
    private final String userName;
    private final String pswd;
    private final String type_operation;

    private String BDD_HOST = "";
    private final Integer BDD_PORT;
    private String BDD_DBNAME = "";
    private String BDD_USER = "";
    private String BDD_PWD = "";
    private String[] BDD_ID_PROFIL = {};
    
    String retour = "";

    /**
     * Creates new form Home
     * @param HOST
     * @param DBNAME
     * @param PORT
     * @param USER
     * @param PWD
     * @param username
     * @param password
     * @param TYPE_OPERATION
     * @param ID_PROFIL
     * @param xx
     * @param yy
     */
    public Home(String HOST, String DBNAME, Integer PORT, String USER, String PWD , String username, String password, String TYPE_OPERATION, String[] ID_PROFIL, Integer xx, Integer yy) {

        
        this.userName = username;
        this.pswd = password;
        this.type_operation = TYPE_OPERATION;

        this.BDD_HOST = HOST;
        this.BDD_PORT = PORT;
        this.BDD_DBNAME = DBNAME;
        this.BDD_USER = USER;
        this.BDD_PWD = PWD;
        this.BDD_ID_PROFIL = ID_PROFIL;
        

        initComponents();
        
        CreateUI.setFontAndPolicyMenuBar(this.jMenu_Consultations);
           
        
        //this.j_menu_controllesSaisies.setEnabled(false);
        this.j_menu_export_listes_demandeurs.setEnabled(false);
        //this.j_menu_parametres.setEnabled(false);
        
        if(this.BDD_ID_PROFIL[2] != null && this.BDD_ID_PROFIL[2].equals("2")){
            
            this.j_menu_formatages.setEnabled(false);
            this.j_menu_exports.setEnabled(false);
            this.j_menu_rapport_cf_editable.setEnabled(false);
            this.j_menu_item_rapport_saisie.setEnabled(false);
            this.j_menu_item_rapports_sig.setEnabled(false);
            this.j_menu_itemStat_Nbre_pret_CQE.setEnabled(false);
            j_menu_item_rapports_sig.setVisible(false);
            
            //this.j_menu_controllesSaisies.setEnabled(false);
        }
        
        
        if(this.BDD_ID_PROFIL[4].toLowerCase().equals("f")){
            
            this.j_menu_parametres.setEnabled(false);
        }

        this.jProgressBar_home.setVisible(false);
        this.j_label_texte_loading.setVisible(false);
        this.j_label_loading.setVisible(false);

        //System.out.println("Bonjour : " + username + "\nVotre mot de passe est : "+ password + "\nType d'op??ration : "+ type_op );
        if(this.type_operation.equals("OGCF")){
            this.lbl_type_operation.setText("Type d'op??ration s??l??ctionn?? : "+this.type_operation);
        }else{
            this.lbl_type_operation.setText("Type d'op??ration s??l??ctionn?? : OCFM");
        }
        
        //this.lbl_test.setText("Bonjour " + username + " !");
        
        
        String nomAtelier = new Querry(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD, this.type_operation).getNomAtelier();
        //System.out.println("Nom de latelier sur home : " + nomAtelier);
        if(nomAtelier.equals("ATS")){
            this.j_menu_item_rapports_sig.setEnabled(true);
        }else{
            this.j_menu_item_rapports_sig.setEnabled(false);
        }
        
        if (this.type_operation.equals("OCFM")) {
            this.j_menu_item_rapports_sig.setEnabled(false);
            this.j_menu_stat_vectorisation_par_communes.setText("Rapport vectorisation par commune(s)");
        }
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //connectDatabase = new ConnectDb("192.168.88.10", 5432, "oprod", "C@seF&Ge0X2", "postgres").getConnection();
        connectDatabase = new ConnectDb(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD).getConnection();
        
     //j_menu_export_listes_anomalies_csv_lola_.setEnabled(false);
     
     //j_menu_export_listes_anomalies_vecto_saisie.setEnabled(false);
     //jMenu2.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JLogin = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        paneChampsLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        dpContent = new javax.swing.JDesktopPane();
        lbl_test = new javax.swing.JLabel();
        lbl_type_operation = new javax.swing.JLabel();
        jProgressBar_home = new javax.swing.JProgressBar();
        j_label_texte_loading = new javax.swing.JLabel();
        j_label_loading = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        j_menu_fichier = new javax.swing.JMenu();
        j_menu_item_deconnexion = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        j_menu_item_quitter_application = new javax.swing.JMenuItem();
        j_menu_formatages = new javax.swing.JMenu();
        j_menu_item_formate_nom = new javax.swing.JMenuItem();
        j_menu_item_formate_prenom = new javax.swing.JMenuItem();
        j_menu_item_formate_lieu_naissance_ = new javax.swing.JMenuItem();
        j_menu_item_nom_parents = new javax.swing.JMenuItem();
        j_menu_item_adresse_demandeur = new javax.swing.JMenuItem();
        j_menu_item_lieu_dit = new javax.swing.JMenuItem();
        j_menu_item_formte_voisins = new javax.swing.JMenuItem();
        j_menu_item_formte_charges = new javax.swing.JMenuItem();
        j_menu_item_formte_consistance_ = new javax.swing.JMenuItem();
        j_menu_item_formte_lieu_cin = new javax.swing.JMenuItem();
        j_menu_item_formte_leiu_acte_naissance = new javax.swing.JMenuItem();
        j_menu_exports = new javax.swing.JMenu();
        j_menu_export_listes_demandeurs = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        j_menu_export_listes_anomalies = new javax.swing.JMenuItem();
        j_menu_export_listes_anomalies_vecto_saisie = new javax.swing.JMenuItem();
        j_menu_export_listes_anomalies_csv_lola_ = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        j_menu_export_listes_dossiers_pret_cqe = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        j_menu_export_listes_cf_editer_par_commune = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        j_menu_export_listes_saisie_sans_vecto = new javax.swing.JMenuItem();
        j_menu_export_listes_vecto_sans_saisie = new javax.swing.JMenuItem();
        j_menu_stats = new javax.swing.JMenu();
        j_menu_stat_vectorisation_par_communes = new javax.swing.JMenuItem();
        j_menu_stat_saisie_par_operateur = new javax.swing.JMenuItem();
        j_menu_stat_anomalies_par_commune = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        j_menu_rapport_cf_editable = new javax.swing.JMenuItem();
        j_menu_item_rapport_saisie = new javax.swing.JMenuItem();
        j_menu_item_rapports_sig = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        j_menu_itemStat_Nbre_pret_CQE = new javax.swing.JMenuItem();
        j_menu_controllesSaisies = new javax.swing.JMenu();
        j_menu_controles_saisie = new javax.swing.JMenuItem();
        j_menu_parametres = new javax.swing.JMenu();
        j_menu_config_bdd = new javax.swing.JMenuItem();
        jMenu_Consultations = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        j_menu_importation_saisie_croise = new javax.swing.JMenuItem();

        JLogin.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        JLogin.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        JLogin.setName("Connexion"); // NOI18N
        JLogin.setResizable(false);
        JLogin.setSize(new java.awt.Dimension(400, 500));

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jButton2)
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel1.setText("Nom d'utilisateur");
        jLabel1.setName("username"); // NOI18N

        javax.swing.GroupLayout paneChampsLoginLayout = new javax.swing.GroupLayout(paneChampsLogin);
        paneChampsLogin.setLayout(paneChampsLoginLayout);
        paneChampsLoginLayout.setHorizontalGroup(
            paneChampsLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneChampsLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        paneChampsLoginLayout.setVerticalGroup(
            paneChampsLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneChampsLoginLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(paneChampsLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JLoginLayout = new javax.swing.GroupLayout(JLogin.getContentPane());
        JLogin.getContentPane().setLayout(JLoginLayout);
        JLoginLayout.setHorizontalGroup(
            JLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JLoginLayout.createSequentialGroup()
                .addGroup(JLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JLoginLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JLoginLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(paneChampsLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        JLoginLayout.setVerticalGroup(
            JLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paneChampsLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ATELIER SUIVI ACTION 3");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        dpContent.setBackground(new java.awt.Color(204, 204, 204));

        lbl_test.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        lbl_test.setForeground(java.awt.Color.white);

        lbl_type_operation.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        lbl_type_operation.setForeground(java.awt.Color.white);

        j_label_texte_loading.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        j_label_texte_loading.setForeground(java.awt.Color.white);
        j_label_texte_loading.setText("Veuillez Patientez ....");

        j_label_loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ressources/Eclipse-0.3s-197px.gif"))); // NOI18N
        j_label_loading.setText(" ");

        dpContent.setLayer(lbl_test, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dpContent.setLayer(lbl_type_operation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dpContent.setLayer(jProgressBar_home, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dpContent.setLayer(j_label_texte_loading, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dpContent.setLayer(j_label_loading, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dpContentLayout = new javax.swing.GroupLayout(dpContent);
        dpContent.setLayout(dpContentLayout);
        dpContentLayout.setHorizontalGroup(
            dpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dpContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dpContentLayout.createSequentialGroup()
                        .addComponent(lbl_type_operation, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 749, Short.MAX_VALUE)
                        .addGroup(dpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_test, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBar_home, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dpContentLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(dpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dpContentLayout.createSequentialGroup()
                                .addComponent(j_label_loading, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dpContentLayout.createSequentialGroup()
                                .addComponent(j_label_texte_loading, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        dpContentLayout.setVerticalGroup(
            dpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dpContentLayout.createSequentialGroup()
                .addContainerGap(344, Short.MAX_VALUE)
                .addComponent(j_label_loading, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(j_label_texte_loading, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jProgressBar_home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_test, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_type_operation, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jMenuBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuBar1MouseEntered(evt);
            }
        });

        j_menu_fichier.setText("Fichier");
        j_menu_fichier.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N

        j_menu_item_deconnexion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        j_menu_item_deconnexion.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_deconnexion.setText("D??connexion");
        j_menu_item_deconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_deconnexionActionPerformed(evt);
            }
        });
        j_menu_fichier.add(j_menu_item_deconnexion);
        j_menu_fichier.add(jSeparator3);

        j_menu_item_quitter_application.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        j_menu_item_quitter_application.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_quitter_application.setText("Quitter");
        j_menu_item_quitter_application.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_quitter_applicationActionPerformed(evt);
            }
        });
        j_menu_fichier.add(j_menu_item_quitter_application);

        jMenuBar1.add(j_menu_fichier);

        j_menu_formatages.setText(" Formatage des donn??es");
        j_menu_formatages.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N

        j_menu_item_formate_nom.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formate_nom.setText("Nom des demandeurs");
        j_menu_item_formate_nom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formate_nomActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formate_nom);

        j_menu_item_formate_prenom.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        j_menu_item_formate_prenom.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formate_prenom.setText("Pr??nom(s) des demandeurss");
        j_menu_item_formate_prenom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formate_prenomActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formate_prenom);

        j_menu_item_formate_lieu_naissance_.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formate_lieu_naissance_.setText("Lieu Niassance");
        j_menu_item_formate_lieu_naissance_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formate_lieu_naissance_ActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formate_lieu_naissance_);

        j_menu_item_nom_parents.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_nom_parents.setText("Nom des Parents");
        j_menu_item_nom_parents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_nom_parentsActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_nom_parents);

        j_menu_item_adresse_demandeur.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        j_menu_item_adresse_demandeur.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_adresse_demandeur.setText("Adresse(s) demandeurs");
        j_menu_item_adresse_demandeur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_adresse_demandeurActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_adresse_demandeur);

        j_menu_item_lieu_dit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        j_menu_item_lieu_dit.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_lieu_dit.setText("Lieu Dit");
        j_menu_item_lieu_dit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_lieu_ditActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_lieu_dit);

        j_menu_item_formte_voisins.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        j_menu_item_formte_voisins.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formte_voisins.setText("Voisinnages");
        j_menu_item_formte_voisins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formte_voisinsActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formte_voisins);

        j_menu_item_formte_charges.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formte_charges.setText("Charges");
        j_menu_item_formte_charges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formte_chargesActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formte_charges);

        j_menu_item_formte_consistance_.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formte_consistance_.setText("Consistance");
        j_menu_item_formte_consistance_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formte_consistance_ActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formte_consistance_);

        j_menu_item_formte_lieu_cin.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formte_lieu_cin.setText("Lieu CIN");
        j_menu_item_formte_lieu_cin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formte_lieu_cinActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formte_lieu_cin);

        j_menu_item_formte_leiu_acte_naissance.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_formte_leiu_acte_naissance.setText("Lieu Acte De Naissance");
        j_menu_item_formte_leiu_acte_naissance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_formte_leiu_acte_naissanceActionPerformed(evt);
            }
        });
        j_menu_formatages.add(j_menu_item_formte_leiu_acte_naissance);

        jMenuBar1.add(j_menu_formatages);

        j_menu_exports.setText("Exports");
        j_menu_exports.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N

        j_menu_export_listes_demandeurs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        j_menu_export_listes_demandeurs.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_demandeurs.setText("Listes des demandeurs");
        j_menu_export_listes_demandeurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_export_listes_demandeursActionPerformed(evt);
            }
        });
        j_menu_exports.add(j_menu_export_listes_demandeurs);
        j_menu_exports.add(jSeparator4);

        j_menu_export_listes_anomalies.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        j_menu_export_listes_anomalies.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_anomalies.setText("Registres Anomalies Saisies ( Fiche 9 )");
        j_menu_export_listes_anomalies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_export_listes_anomaliesActionPerformed(evt);
            }
        });
        j_menu_exports.add(j_menu_export_listes_anomalies);

        j_menu_export_listes_anomalies_vecto_saisie.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_anomalies_vecto_saisie.setText("Registres Anomalies Vecto");
        j_menu_exports.add(j_menu_export_listes_anomalies_vecto_saisie);

        j_menu_export_listes_anomalies_csv_lola_.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_anomalies_csv_lola_.setText("Anomalies CSV ( Lola ) ( Pour rapport hebdo )");
        j_menu_export_listes_anomalies_csv_lola_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_export_listes_anomalies_csv_lola_ActionPerformed(evt);
            }
        });
        j_menu_exports.add(j_menu_export_listes_anomalies_csv_lola_);
        j_menu_exports.add(jSeparator5);

        j_menu_export_listes_dossiers_pret_cqe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        j_menu_export_listes_dossiers_pret_cqe.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_dossiers_pret_cqe.setText("Registre Parcellaire Provisoire (Pr??t CQE)");
        j_menu_export_listes_dossiers_pret_cqe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_export_listes_dossiers_pret_cqeActionPerformed(evt);
            }
        });
        j_menu_exports.add(j_menu_export_listes_dossiers_pret_cqe);
        j_menu_exports.add(jSeparator6);

        j_menu_export_listes_cf_editer_par_commune.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        j_menu_export_listes_cf_editer_par_commune.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_cf_editer_par_commune.setText("Listes CF ??diter");
        j_menu_export_listes_cf_editer_par_commune.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_export_listes_cf_editer_par_communeActionPerformed(evt);
            }
        });
        j_menu_exports.add(j_menu_export_listes_cf_editer_par_commune);
        j_menu_exports.add(jSeparator2);

        j_menu_export_listes_saisie_sans_vecto.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_saisie_sans_vecto.setText("Saisie Sans Vecto");
        j_menu_export_listes_saisie_sans_vecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_export_listes_saisie_sans_vectoActionPerformed(evt);
            }
        });
        j_menu_exports.add(j_menu_export_listes_saisie_sans_vecto);

        j_menu_export_listes_vecto_sans_saisie.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_export_listes_vecto_sans_saisie.setText("Vectorisation sans saisie");
        j_menu_export_listes_vecto_sans_saisie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_export_listes_vecto_sans_saisieActionPerformed(evt);
            }
        });
        j_menu_exports.add(j_menu_export_listes_vecto_sans_saisie);

        jMenuBar1.add(j_menu_exports);

        j_menu_stats.setText("Statistique(s)");
        j_menu_stats.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_stats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_statsActionPerformed(evt);
            }
        });

        j_menu_stat_vectorisation_par_communes.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_stat_vectorisation_par_communes.setText("Vectorisation par commune(s)");
        j_menu_stat_vectorisation_par_communes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_stat_vectorisation_par_communesActionPerformed(evt);
            }
        });
        j_menu_stats.add(j_menu_stat_vectorisation_par_communes);

        j_menu_stat_saisie_par_operateur.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_stat_saisie_par_operateur.setText("Saisies par commune(s)");
        j_menu_stat_saisie_par_operateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_stat_saisie_par_operateurActionPerformed(evt);
            }
        });
        j_menu_stats.add(j_menu_stat_saisie_par_operateur);

        j_menu_stat_anomalies_par_commune.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_stat_anomalies_par_commune.setText("Anomalie Bloquantes Saisie par Commune(s)");
        j_menu_stat_anomalies_par_commune.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_stat_anomalies_par_communeActionPerformed(evt);
            }
        });
        j_menu_stats.add(j_menu_stat_anomalies_par_commune);
        j_menu_stats.add(jSeparator1);

        j_menu_rapport_cf_editable.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_rapport_cf_editable.setText("Rapport CF ??ditable");
        j_menu_rapport_cf_editable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_rapport_cf_editableActionPerformed(evt);
            }
        });
        j_menu_stats.add(j_menu_rapport_cf_editable);

        j_menu_item_rapport_saisie.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_rapport_saisie.setText("Rapport Saisie par commune(s)");
        j_menu_item_rapport_saisie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_rapport_saisieActionPerformed(evt);
            }
        });
        j_menu_stats.add(j_menu_item_rapport_saisie);

        j_menu_item_rapports_sig.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_item_rapports_sig.setText("Rapport vectorisation par commune(s)");
        j_menu_item_rapports_sig.setName("setEnabled"); // NOI18N
        j_menu_item_rapports_sig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_item_rapports_sigActionPerformed(evt);
            }
        });
        j_menu_stats.add(j_menu_item_rapports_sig);
        j_menu_stats.add(jSeparator8);

        j_menu_itemStat_Nbre_pret_CQE.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_itemStat_Nbre_pret_CQE.setText("Dossier(s) prt??t CQE");
        j_menu_itemStat_Nbre_pret_CQE.setName("test"); // NOI18N
        j_menu_itemStat_Nbre_pret_CQE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_itemStat_Nbre_pret_CQEActionPerformed(evt);
            }
        });
        j_menu_stats.add(j_menu_itemStat_Nbre_pret_CQE);

        jMenuBar1.add(j_menu_stats);

        j_menu_controllesSaisies.setText("Contr??les Traitement(s)");
        j_menu_controllesSaisies.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_controllesSaisies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_controllesSaisiesActionPerformed(evt);
            }
        });

        j_menu_controles_saisie.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_controles_saisie.setText("Contr??les des saisies");
        j_menu_controles_saisie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_controles_saisieActionPerformed(evt);
            }
        });
        j_menu_controllesSaisies.add(j_menu_controles_saisie);

        jMenuBar1.add(j_menu_controllesSaisies);

        j_menu_parametres.setText("Param??tres");
        j_menu_parametres.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N

        j_menu_config_bdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        j_menu_config_bdd.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_config_bdd.setText("Configuration base de donn??es");
        j_menu_config_bdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_config_bddActionPerformed(evt);
            }
        });
        j_menu_parametres.add(j_menu_config_bdd);

        jMenuBar1.add(j_menu_parametres);

        jMenu_Consultations.setText("Consultations");
        jMenu_Consultations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu_ConsultationsMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu_Consultations);

        jMenu2.setText("Saisie crois??");
        jMenu2.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N

        j_menu_importation_saisie_croise.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        j_menu_importation_saisie_croise.setText("Importation");
        j_menu_importation_saisie_croise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_menu_importation_saisie_croiseActionPerformed(evt);
            }
        });
        jMenu2.add(j_menu_importation_saisie_croise);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dpContent)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(dpContent)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void j_menu_item_quitter_applicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_quitter_applicationActionPerformed

        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment quitter l'application ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            //System.out.println("click sur OK");
            System.exit(0);
        }
    }//GEN-LAST:event_j_menu_item_quitter_applicationActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.JLogin.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void j_menu_statsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_statsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_j_menu_statsActionPerformed

    private void j_menu_stat_saisie_par_operateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_stat_saisie_par_operateurActionPerformed
        SaisieParOperateur s_par_op = new SaisieParOperateur(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD, this.type_operation);
        this.dpContent.add(s_par_op);
        s_par_op.show();

    }//GEN-LAST:event_j_menu_stat_saisie_par_operateurActionPerformed
    
    private String formats(String id_table, String nameOfTable, String col_updated, String operation){
        
        Map<String, String> tabPrenoms = new HashMap<>();
        String newFirstName = "";
        String idPersphys = "";
        Boolean etat = false;
        String filtreOperation = " ";
        
        if (nameOfTable.toLowerCase().equals("demande")) {
            if (operation.toLowerCase().equals("ogcf")) {
                filtreOperation = " WHERE type_op = 'ogcf' AND "+col_updated + " IS NOT NULL AND id_certificat IS NULL ";
            }else{
                filtreOperation = " WHERE type_op = 'ocm' AND "+col_updated + " IS NOT NULL AND id_certificat IS NULL ";
            }
        }

        try {
            String q = " SELECT "+id_table+" , "+col_updated+" FROM "+nameOfTable + filtreOperation+" ";

            
            st = connectDatabase.prepareStatement(q);    
            rs = st.executeQuery();
            
            System.out.println("REQ : " +  st);
            
            while(rs.next()){
                tabPrenoms.put(rs.getString(id_table), rs.getString(col_updated));
            }
              
            st.close();
            rs.close();
            
            for (Map.Entry mapentry : tabPrenoms.entrySet()) {
                
                if(mapentry.getValue() != null){
                    
                    idPersphys = mapentry.getKey().toString();
                    //newFirstName = Formats.formatFirtsName(mapentry.getValue().toString());
                    newFirstName = Formats.formatFirtsName(mapentry.getValue().toString());
                    
                    // MISE A JOUR LA BASE DE DONNEES
                    
                    String q2 = "UPDATE "+nameOfTable+" SET "+col_updated+" = ? WHERE "+id_table+" = ? ;";
                    
                    st = connectDatabase.prepareStatement(q2);
                     
                    st.setString(1, newFirstName.trim());
                    st.setString(2, idPersphys);
                    st.executeUpdate();
                    
                    
                    retour = "ok";
                    newFirstName = "";
                    idPersphys = "";
                }
            }
            
            
            st.close();
            rs.close();
            //System.out.println("Fin formatages.");
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return retour;
    }
    

private String formatsToUpper(String id_table, String nameOfTable, String col_updated){
        
        Map<String, String> tabData = new HashMap<>();
        String newFirstName = "";
        String idTable = "";
        Boolean etatUpper = false;

        
        try {
            String q_get = " SELECT "+id_table+" , "+col_updated+" FROM "+nameOfTable+" ";
            st = connectDatabase.prepareStatement(q_get);    
            rs = st.executeQuery();
            

            
            while(rs.next()){
                tabData.put(rs.getString(id_table), rs.getString(col_updated));
            }
            
            st.close();
            rs.close();
            
            for (Map.Entry mapentry : tabData.entrySet()) {
                
                if(mapentry.getValue() != null){
                    
                   // System.out.println("cl??: "+mapentry.getKey()  + " | valeur: " + mapentry.getValue());
                   
                    //System.out.println("cl??: "+mapentry.getKey()  + " | valeur: " + mapentry.getValue().toString().trim());
                    idTable = mapentry.getKey().toString();
                    newFirstName = Formats.formatFirtsName(mapentry.getValue().toString().trim()).toUpperCase().trim();
                    
                    // IL FAUT METTRE A JOUR LA BASE DE DONNEES
                    
                    String q_update = "UPDATE "+nameOfTable+" SET "+col_updated+" = ? WHERE "+id_table+" = ? ";
                    st = connectDatabase.prepareStatement(q_update);
                    st.setString(1, newFirstName);
                    st.setString(2, idTable);
                    st.executeUpdate();
                    //Persphys.setPrenom(idPersphys, newFirstName);
                    retour = "ok";
                    newFirstName = "";
                    idTable = "";
                }
            }
            
            st.close();
            rs.close();
            //System.out.println("Fin formatages to upper case.");
            
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retour;
    }
    
    
    
    private void j_menu_export_listes_demandeursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_export_listes_demandeursActionPerformed
         
    }//GEN-LAST:event_j_menu_export_listes_demandeursActionPerformed

    private void j_menu_item_formate_prenomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formate_prenomActionPerformed

        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage des prenom(s) des demandeurs  ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            
            
                new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatages pr??nom(s) des demandeurs en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatages nom prenoms ...");
                    
                    if(formats("id_persphys", "persphys", "prenom", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatages pr??nom(s) des demandeurs Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
            
            
        }
        
        this.j_label_texte_loading.setVisible(false);
        this.j_label_loading.setVisible(false);
    }//GEN-LAST:event_j_menu_item_formate_prenomActionPerformed

    private void j_menu_item_lieu_ditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_lieu_ditActionPerformed
        
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage des lieu dit ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            
           
                new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage lieu dit en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage lieu dit ...");
                    
                    if(formats("id_demande", "demande", "lieu_dit", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage lieu dit Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
            

        }
    }//GEN-LAST:event_j_menu_item_lieu_ditActionPerformed

    private void j_menu_item_deconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_deconnexionActionPerformed
        
        JOptionPane jop = new JOptionPane();
             
       // CreateUI.CreateDialogForm(this, "D??connection", "Voulez-vous vraiment se deconnecter ?", JOptionPane.QUESTION_MESSAGE);    
        
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment se deconnecter ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(option == JOptionPane.OK_OPTION){
            
            // recuperation des d??marches
            Iterator it = new Querry(this.BDD_HOST,this.BDD_PORT,this.BDD_DBNAME,this.BDD_USER,this.BDD_PWD, this.type_operation).getAllDemarche().entrySet().iterator();
            List<String> demarches = new ArrayList<String>();

	    while (it.hasNext()) {
	        Map.Entry<String, String> val = (Map.Entry)it.next();
                demarches.add(val.getValue().toString());  
	    }
            
            this.setVisible(false);
            
            UserFormDialog userForm = new UserFormDialog(this.BDD_HOST,this.BDD_PORT,this.BDD_DBNAME,this.BDD_USER,this.BDD_PWD, demarches);
            userForm.setLocationRelativeTo(null);
            userForm.setVisible(true);
        }
        

    }//GEN-LAST:event_j_menu_item_deconnexionActionPerformed
//String retourFormatage = "";
    private void j_menu_item_formte_voisinsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formte_voisinsActionPerformed
        JOptionPane jop = new JOptionPane();
        
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage des voisin(s) des demandeurs ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
       
        if(option == JOptionPane.OK_OPTION){
            
            new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatages des voisin(s) en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatages des voisins...");
                    
                    if(formats("id_demande", "demande", "v_nord", type_operation).equals("ok") && formats("id_demande", "demande", "v_sud", type_operation).equals("ok") && formats("id_demande", "demande", "v_est", type_operation).equals("ok")&& formats("id_demande", "demande", "v_ouest", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatages des voisin(s) Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
            
        }
    }//GEN-LAST:event_j_menu_item_formte_voisinsActionPerformed

    private void j_menu_item_nom_parentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_nom_parentsActionPerformed

        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage du nom des parents ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            
            
                new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage nom des parent(s) (p??re et m??re) en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage nom des parent(s) (p??re et m??re) ...");
                    
                    if(formats("id_persphys", "persphys", "nom_pere", type_operation).equals("ok") && formats("id_persphys", "persphys", "nom_mere", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage nom des parent(s) (p??re et m??re) Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
            

        }
        this.j_label_texte_loading.setVisible(false);
        this.j_label_loading.setVisible(false);
    }//GEN-LAST:event_j_menu_item_nom_parentsActionPerformed

    private void j_menu_item_formte_chargesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formte_chargesActionPerformed
        
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage des charges sur les demandes des demandeurs ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){

            new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage charge(s) en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage charge(s) ...");
                    
                    if(formats("id_demande", "demande", "charges", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage charge(s) Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
        }
        
    }//GEN-LAST:event_j_menu_item_formte_chargesActionPerformed

    private void j_menu_item_formte_lieu_cinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formte_lieu_cinActionPerformed
        
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage des lieux de d??livrance du CIN des demandeurs  ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            
            new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage Lieu CIN en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage Lieu CIN ...");
                    
                    if(formats("id_persphys", "persphys", "cni_lieu", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage Lieu CIN Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            

        }
    }//GEN-LAST:event_j_menu_item_formte_lieu_cinActionPerformed

    private void j_menu_item_formte_leiu_acte_naissanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formte_leiu_acte_naissanceActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage des lieux de d??livrance de l'acte de naisance des demandeurs  ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){

            
            new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage Lieu Acte de Naissance en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage Lieu Acte de Naissance ...");
                    
                    if(formats("id_persphys", "persphys", "acn_lieu", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage Lieu Acte de Naissance Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            

        }
    }//GEN-LAST:event_j_menu_item_formte_leiu_acte_naissanceActionPerformed

    private void j_menu_export_listes_dossiers_pret_cqeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_export_listes_dossiers_pret_cqeActionPerformed
        
        ExportRegistreParcellaire rp_prov = new ExportRegistreParcellaire(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER,this.type_operation);
        this.dpContent.add(rp_prov);
        rp_prov.show();
               
    }//GEN-LAST:event_j_menu_export_listes_dossiers_pret_cqeActionPerformed

    private void j_menu_export_listes_anomaliesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_export_listes_anomaliesActionPerformed

        ExportRegistreAnomalie reg_anomaie = new ExportRegistreAnomalie(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(reg_anomaie);
        reg_anomaie.show();
        
    }//GEN-LAST:event_j_menu_export_listes_anomaliesActionPerformed

    private void j_menu_item_adresse_demandeurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_adresse_demandeurActionPerformed
        
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage l'adresse des demandeurs  ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            
            this.j_label_texte_loading.setVisible(true);
            this.j_label_loading.setVisible(true);
        

                new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage adresse des demandeurs en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage adresse des demandeurs ...");
                    
                    if(formats("id_persphys", "persphys", "adresse", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage adresse des demandeurs Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
            
        }
        
            this.j_label_texte_loading.setVisible(false);
            this.j_label_loading.setVisible(false);
        
    }//GEN-LAST:event_j_menu_item_adresse_demandeurActionPerformed

    private void j_menu_item_formate_nomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formate_nomActionPerformed

        
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage de nom(s) des demandeurs  ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            

                new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatages nom des demandeurs en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatages nom demandeurs ...");
                    
                    if(formatsToUpper("id_persphys", "persphys", "nom").equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatages nom des demandeurs Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
        
            
        }
        
    }//GEN-LAST:event_j_menu_item_formate_nomActionPerformed

    private void j_menu_item_formate_lieu_naissance_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formate_lieu_naissance_ActionPerformed

        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage du lieu de naissance des demandeurs ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){

            
                new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage lieu de naissance des demandeurs en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage lieu de naissance ...");
                    
                    if(formats("id_persphys", "persphys", "naissance_lieu", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage lieu de naissance des demandeurs Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
            
            
        }
        
        this.j_label_texte_loading.setVisible(false);
        this.j_label_loading.setVisible(false);
    }//GEN-LAST:event_j_menu_item_formate_lieu_naissance_ActionPerformed

    private void j_menu_item_formte_consistance_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_formte_consistance_ActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous vraiment lancer le formatage des consistance(s) des demandes ?","" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            
            new SwingWorker(){
                @Override
                protected String doInBackground() throws Exception{
                    j_menu_formatages.setEnabled(false);
                    j_menu_fichier.setEnabled(false);
                    
                    // rendre visible le boutton et texte chargement formatages des donnn??es
                    j_label_loading.setVisible(true);
                    j_label_texte_loading.setText("Formatage consistance(s) en cours ...");
                    j_label_texte_loading.setVisible(true);
                    

                    System.out.println("D??but formatage consistance ...");
                    
                    if(formats("id_demande", "demande", "consistance", type_operation).equals("ok")) return "ok-formatages";
                    
                    return null;
                }
                
                @Override
                protected void done(){
                    
                    try {
                        

                        if (get().toString().equals("ok-formatages")) {
                            j_menu_formatages.setEnabled(true);
                            j_menu_fichier.setEnabled(true);
                            
                            // rendre visible le boutton et texte chargement formatages des donnn??es
                            j_label_loading.setVisible(false);
                            j_label_texte_loading.setText("Formatage consistance(s) Termin?? !");

                        }
                        //System.out.println(get());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
            
            
        }
    }//GEN-LAST:event_j_menu_item_formte_consistance_ActionPerformed

    private void j_menu_controles_saisieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_controles_saisieActionPerformed
        
        ControlleDesSaisies ctrl_saisie = new ControlleDesSaisies(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(ctrl_saisie);
        ctrl_saisie.show();
    }//GEN-LAST:event_j_menu_controles_saisieActionPerformed

    private void j_menu_controllesSaisiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_controllesSaisiesActionPerformed
        System.out.println("click sur btn ");
    }//GEN-LAST:event_j_menu_controllesSaisiesActionPerformed

    private void jMenuBar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MouseEntered
        this.lbl_test.setText("");
    }//GEN-LAST:event_jMenuBar1MouseEntered

    private void j_menu_export_listes_cf_editer_par_communeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_export_listes_cf_editer_par_communeActionPerformed
        
        ExportCFEditerParCommunes cf_editer = new ExportCFEditerParCommunes(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD, type_operation);
        this.dpContent.add(cf_editer);
        cf_editer.show();

    }//GEN-LAST:event_j_menu_export_listes_cf_editer_par_communeActionPerformed

    private void j_menu_item_rapports_sigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_rapports_sigActionPerformed
        RapportSIG v_rapport_sig = new RapportSIG(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(v_rapport_sig);
        v_rapport_sig.show();
    }//GEN-LAST:event_j_menu_item_rapports_sigActionPerformed

    private void j_menu_item_rapport_saisieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_item_rapport_saisieActionPerformed
        RapportSAISIE v_rapport_saisie = new RapportSAISIE(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(v_rapport_saisie);
        v_rapport_saisie.show();
    }//GEN-LAST:event_j_menu_item_rapport_saisieActionPerformed

    private void j_menu_rapport_cf_editableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_rapport_cf_editableActionPerformed
        RapportCfEditableParCommune v_rapport_saisie_par_commune = new RapportCfEditableParCommune(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(v_rapport_saisie_par_commune);
        v_rapport_saisie_par_commune.show();
    }//GEN-LAST:event_j_menu_rapport_cf_editableActionPerformed

    private void j_menu_stat_anomalies_par_communeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_stat_anomalies_par_communeActionPerformed
        RapportAnomalieSaisieParCommune v_rapport_anomalies_saisie_par_commune = new RapportAnomalieSaisieParCommune(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(v_rapport_anomalies_saisie_par_commune);
        v_rapport_anomalies_saisie_par_commune.show();
    }//GEN-LAST:event_j_menu_stat_anomalies_par_communeActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.isControlDown() && evt.isShiftDown() && evt.getKeyChar() != 'p'&& evt.getKeyChar() != 'P' && evt.getKeyCode() == 80) {
                System.out.println("CTRL + SHIFT + p"); 
                
                this.j_menu_parametres.setEnabled(true);
                
                ///this.j_menu_parametres.set
        }
    }//GEN-LAST:event_formKeyPressed

    private void j_menu_config_bddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_config_bddActionPerformed

        //System.out.println("THREAD HORS invokeLater menu conf = " + Thread.currentThread().getName());
                SwingUtilities.invokeLater(() -> {
                    
                    //System.out.println("THREAD EN COURS DANS invokeLater menu conf = " + Thread.currentThread().getName());
                    ModificationConfigurationBaseDeDonnees v_rapport_anomalies_saisie_par_commune = new ModificationConfigurationBaseDeDonnees(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD);
                    this.dpContent.add(v_rapport_anomalies_saisie_par_commune);
                    v_rapport_anomalies_saisie_par_commune.show();

                });
        
                
                //this.j_menu_parametres.setEnabled(false);
                
                //formKeyPressed();

    }//GEN-LAST:event_j_menu_config_bddActionPerformed

    private void j_menu_itemStat_Nbre_pret_CQEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_itemStat_Nbre_pret_CQEActionPerformed
        // NombreDossiersPretCQEParCommune
        
        NombreDossiersPretCQEParCommune stats_pret_cqe_par_commune = new NombreDossiersPretCQEParCommune(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(stats_pret_cqe_par_commune);
        stats_pret_cqe_par_commune.show();
    }//GEN-LAST:event_j_menu_itemStat_Nbre_pret_CQEActionPerformed

    private void j_menu_stat_vectorisation_par_communesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_stat_vectorisation_par_communesActionPerformed
        // VectorisationParCommune
        VectorisationParCommune vecto_par_commune = new VectorisationParCommune(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD, this.type_operation);
        this.dpContent.add(vecto_par_commune);
        vecto_par_commune.show();
    }//GEN-LAST:event_j_menu_stat_vectorisation_par_communesActionPerformed

    private void j_menu_export_listes_saisie_sans_vectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_export_listes_saisie_sans_vectoActionPerformed
        // ExportSaisieSansVectorisation
        ExportSaisieSansVectorisation saisieSansVecto = new ExportSaisieSansVectorisation(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD, this.type_operation);
        this.dpContent.add(saisieSansVecto);
        saisieSansVecto.show();
    }//GEN-LAST:event_j_menu_export_listes_saisie_sans_vectoActionPerformed

    private void j_menu_export_listes_vecto_sans_saisieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_export_listes_vecto_sans_saisieActionPerformed
        // TODO add your handling code here:
        ExportVectorisationSansSaisie vectoSansSaisie = new ExportVectorisationSansSaisie(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD, this.type_operation);
        this.dpContent.add(vectoSansSaisie);
        vectoSansSaisie.show();
    }//GEN-LAST:event_j_menu_export_listes_vecto_sans_saisieActionPerformed

    private void j_menu_importation_saisie_croiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_importation_saisie_croiseActionPerformed
                SwingUtilities.invokeLater(() -> {
                    
                    //System.out.println("THREAD EN COURS DANS invokeLater menu conf = " + Thread.currentThread().getName());
                    ImportationSaisieCroise importCroise = new ImportationSaisieCroise(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_USER, this.BDD_PWD, type_operation);
                    this.dpContent.add(importCroise);
                    importCroise.show();

                });
    }//GEN-LAST:event_j_menu_importation_saisie_croiseActionPerformed

    private void j_menu_export_listes_anomalies_csv_lola_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_menu_export_listes_anomalies_csv_lola_ActionPerformed
        // TODO add your handling code here:
        
        ExportRegistreAnomalieCSVLola r_anomalie_lola = new ExportRegistreAnomalieCSVLola(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(r_anomalie_lola);
        r_anomalie_lola.show();
        
    }//GEN-LAST:event_j_menu_export_listes_anomalies_csv_lola_ActionPerformed

    private void jMenu_ConsultationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu_ConsultationsMouseClicked
        Consultations Consultations = new Consultations(this.BDD_HOST, this.BDD_PORT, this.BDD_DBNAME, this.BDD_PWD, this.BDD_USER, type_operation);
        this.dpContent.add(Consultations);
        Consultations.show();
    }//GEN-LAST:event_jMenu_ConsultationsMouseClicked

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                
                //new Home(userName, pswd, type_operation).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog JLogin;
    private javax.swing.JDesktopPane dpContent;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenu_Consultations;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar_home;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel j_label_loading;
    private javax.swing.JLabel j_label_texte_loading;
    private javax.swing.JMenuItem j_menu_config_bdd;
    private javax.swing.JMenuItem j_menu_controles_saisie;
    private javax.swing.JMenu j_menu_controllesSaisies;
    private javax.swing.JMenuItem j_menu_export_listes_anomalies;
    private javax.swing.JMenuItem j_menu_export_listes_anomalies_csv_lola_;
    private javax.swing.JMenuItem j_menu_export_listes_anomalies_vecto_saisie;
    private javax.swing.JMenuItem j_menu_export_listes_cf_editer_par_commune;
    private javax.swing.JMenuItem j_menu_export_listes_demandeurs;
    private javax.swing.JMenuItem j_menu_export_listes_dossiers_pret_cqe;
    private javax.swing.JMenuItem j_menu_export_listes_saisie_sans_vecto;
    private javax.swing.JMenuItem j_menu_export_listes_vecto_sans_saisie;
    private javax.swing.JMenu j_menu_exports;
    private javax.swing.JMenu j_menu_fichier;
    private javax.swing.JMenu j_menu_formatages;
    private javax.swing.JMenuItem j_menu_importation_saisie_croise;
    private javax.swing.JMenuItem j_menu_itemStat_Nbre_pret_CQE;
    private javax.swing.JMenuItem j_menu_item_adresse_demandeur;
    private javax.swing.JMenuItem j_menu_item_deconnexion;
    private javax.swing.JMenuItem j_menu_item_formate_lieu_naissance_;
    private javax.swing.JMenuItem j_menu_item_formate_nom;
    private javax.swing.JMenuItem j_menu_item_formate_prenom;
    private javax.swing.JMenuItem j_menu_item_formte_charges;
    private javax.swing.JMenuItem j_menu_item_formte_consistance_;
    private javax.swing.JMenuItem j_menu_item_formte_leiu_acte_naissance;
    private javax.swing.JMenuItem j_menu_item_formte_lieu_cin;
    private javax.swing.JMenuItem j_menu_item_formte_voisins;
    private javax.swing.JMenuItem j_menu_item_lieu_dit;
    private javax.swing.JMenuItem j_menu_item_nom_parents;
    private javax.swing.JMenuItem j_menu_item_quitter_application;
    private javax.swing.JMenuItem j_menu_item_rapport_saisie;
    private javax.swing.JMenuItem j_menu_item_rapports_sig;
    private javax.swing.JMenu j_menu_parametres;
    private javax.swing.JMenuItem j_menu_rapport_cf_editable;
    private javax.swing.JMenuItem j_menu_stat_anomalies_par_commune;
    private javax.swing.JMenuItem j_menu_stat_saisie_par_operateur;
    private javax.swing.JMenuItem j_menu_stat_vectorisation_par_communes;
    private javax.swing.JMenu j_menu_stats;
    private javax.swing.JLabel lbl_test;
    private javax.swing.JLabel lbl_type_operation;
    private javax.swing.JPanel paneChampsLogin;
    // End of variables declaration//GEN-END:variables
}
