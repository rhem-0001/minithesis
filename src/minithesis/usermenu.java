package minithesis;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;



public class usermenu extends javax.swing.JFrame {
    
    private HashMap<Integer, Integer> cartStockDeductions = new HashMap<>();
    private static final Logger logger = Logger.getLogger(usermenu.class.getName());
    
    public static usermenu instance;
    
    Color DefaultColor, ClickedColor;
    
    public usermenu() {
        instance = this; 
        initComponents(); 
        loadCategories();
        
         // Create and add the usercategory internal frame
        usercategory uc = new usercategory();
        desktoppane.add(uc);
        uc.setVisible(true);
      
        try {
        uc.setMaximum(true);
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
        
        uc.loadAllProducts(); 
        
        startDateTime();
        
        DefaultColor = new Color(255,255,255);
        ClickedColor = new Color(204,0,0); 
        
    }
    
    public class CategoryComboItem {
    private int id;
    private String name;
    
    public CategoryComboItem(int id, String name) {  // <-- Fix this line
        this.id = id;
        this.name = name;
    }
    // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
     
    public int getId() { return id; }
    @Override
    public String toString() { return name; }
    }
     // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    public Object getSelectedCategory() {
    return cmbusercategory.getSelectedItem();
    }
    
   public void loadCategories() {
    try {
        Connection con = sqlconnector.getConnection();
        String sql = "SELECT category_id, category_name FROM category ORDER BY category_name ASC";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        cmbusercategory.removeAllItems();
        cmbusercategory.addItem(new CategoryComboItem(0, "-- Select Category --"));
        
        while(rs.next()) {
            cmbusercategory.addItem(new CategoryComboItem(
                rs.getInt("category_id"), 
                rs.getString("category_name")
            ));
        }
        con.close();
    } catch(Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage());                                                                                                                                                 
    }
}
   
   public void loadProductsByCategory(int categoryId) {
    try {
        Connection con = sqlconnector.getConnection();
        
        // FIX: Use DISTINCT to get unique products only
        String sql = "SELECT DISTINCT p.product_id, p.product_name " +
                     "FROM product p " +
                     "JOIN product_variant pv ON p.product_id = pv.product_id " +
                     "WHERE p.category_id = ? " +
                     "ORDER BY p.product_name ASC";
        
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, categoryId);
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
        model.setRowCount(0);
        
        while(rs.next()) {
            // Only add ID and Name (matching your 2 columns in the screenshot)
            model.addRow(new Object[]{
                rs.getInt("product_id"),        // Col 0: ID
                rs.getString("product_name")    // Col 1: Name
            });
        }
        con.close();
        
    } catch(Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        e.printStackTrace();
    }
}

   public javax.swing.JDesktopPane getDesktopPane() {
    return desktoppane;
   }
    
    
    
    // Add new row if item doesn't exist
    
    
    
    

    // Also add a focus listener or document listener if you want real-time update
    
    
    
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel21 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        Logoutpanel = new javax.swing.JPanel();
        lbllogout = new javax.swing.JLabel();
        resetpanel = new javax.swing.JPanel();
        lblreset = new javax.swing.JLabel();
        recordpanel = new javax.swing.JPanel();
        lbltotal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        lblDateTime = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblttl = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblcash = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblchange = new javax.swing.JLabel();
        desktoppane = new javax.swing.JDesktopPane();
        txtTotal = new javax.swing.JTextField();
        txtCash = new javax.swing.JTextField();
        txtChange = new javax.swing.JTextField();
        cmbusercategory = new javax.swing.JComboBox<>();
        reportspanel = new javax.swing.JPanel();
        lblreports = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel21.setBackground(new java.awt.Color(229, 45, 45));
        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, null));

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Yoyi's Cakes & Pastries");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 1472, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
        );

        Logoutpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutpanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LogoutpanelMousePressed(evt);
            }
        });

        lbllogout.setText("LogOut");

        javax.swing.GroupLayout LogoutpanelLayout = new javax.swing.GroupLayout(Logoutpanel);
        Logoutpanel.setLayout(LogoutpanelLayout);
        LogoutpanelLayout.setHorizontalGroup(
            LogoutpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogoutpanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lbllogout)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        LogoutpanelLayout.setVerticalGroup(
            LogoutpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogoutpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbllogout, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        resetpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetpanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                resetpanelMousePressed(evt);
            }
        });

        lblreset.setText("Reset");

        javax.swing.GroupLayout resetpanelLayout = new javax.swing.GroupLayout(resetpanel);
        resetpanel.setLayout(resetpanelLayout);
        resetpanelLayout.setHorizontalGroup(
            resetpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resetpanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblreset)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        resetpanelLayout.setVerticalGroup(
            resetpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resetpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        recordpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordpanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                recordpanelMousePressed(evt);
            }
        });

        lbltotal.setText("Record");
        lbltotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbltotalMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout recordpanelLayout = new javax.swing.GroupLayout(recordpanel);
        recordpanel.setLayout(recordpanelLayout);
        recordpanelLayout.setHorizontalGroup(
            recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lbltotal)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        recordpanelLayout.setVerticalGroup(
            recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbltotal, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Size", "Quantity", "Price", "SubTotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblProductsMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblProducts);

        lblDateTime.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lblDateTime.setText("00:00:00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblDateTime)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(lblDateTime)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblttl.setText("TOTAL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblttl)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblttl, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        lblcash.setText("CASH");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblcash, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblcash, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblchange.setText("CHANGE");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblchange)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblchange, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );

        desktoppane.setRequestFocusEnabled(false);

        javax.swing.GroupLayout desktoppaneLayout = new javax.swing.GroupLayout(desktoppane);
        desktoppane.setLayout(desktoppaneLayout);
        desktoppaneLayout.setHorizontalGroup(
            desktoppaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1002, Short.MAX_VALUE)
        );
        desktoppaneLayout.setVerticalGroup(
            desktoppaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        txtTotal.addActionListener(this::txtTotalActionPerformed);

        txtCash.addActionListener(this::txtCashActionPerformed);

        txtChange.addActionListener(this::txtChangeActionPerformed);

        cmbusercategory.addActionListener(this::cmbusercategoryActionPerformed);

        reportspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportspanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                reportspanelMousePressed(evt);
            }
        });

        lblreports.setText("Reports");

        javax.swing.GroupLayout reportspanelLayout = new javax.swing.GroupLayout(reportspanel);
        reportspanel.setLayout(reportspanelLayout);
        reportspanelLayout.setHorizontalGroup(
            reportspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportspanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblreports)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        reportspanelLayout.setVerticalGroup(
            reportspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblreports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(Logoutpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(resetpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(recordpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(reportspanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(desktoppane)
                            .addComponent(cmbusercategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtChange))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTotal)
                                    .addComponent(txtCash))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(cmbusercategory, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(desktoppane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Logoutpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resetpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(recordpanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reportspanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTotal)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCash))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(108, 108, 108))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    

    
    private void LogoutpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutpanelMousePressed
        // TODO add your handling code here:

        Logoutpanel.setBackground(ClickedColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
        reportspanel.setBackground(DefaultColor);
        
    }//GEN-LAST:event_LogoutpanelMousePressed

    private void LogoutpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutpanelMouseClicked
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to Log Out?", "Confirmation", JOptionPane.YES_NO_OPTION);
        
        if(result == JOptionPane.YES_OPTION){
            loginform login = new loginform();
            login.setVisible(true);

            this.dispose();
        }
    }//GEN-LAST:event_LogoutpanelMouseClicked

    private void resetpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetpanelMousePressed
        // TODO add your handling code here:

        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(ClickedColor);
        recordpanel.setBackground(DefaultColor);
        reportspanel.setBackground(DefaultColor);
    }//GEN-LAST:event_resetpanelMousePressed

    private void recordpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordpanelMousePressed
        // TODO add your handling code here:

        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(ClickedColor);
        reportspanel.setBackground(DefaultColor);
    }//GEN-LAST:event_recordpanelMousePressed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void lbltotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbltotalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbltotalMouseClicked

    private void recordpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordpanelMouseClicked
        if (tblProducts.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, 
            "Cart is empty! Please add items first.", 
            "Empty Cart", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (txtCash.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Please enter cash amount!", 
            "Missing Payment", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        double total = Double.parseDouble(txtTotal.getText().replace("₱", "").replace(",", ""));
        double cash = Double.parseDouble(txtCash.getText().replace("₱", "").replace(",", ""));
        double change = cash - total;
        
        if (change < 0) {
            JOptionPane.showMessageDialog(this, 
                "Insufficient cash amount!", 
                "Payment Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Build receipt text - RIGHT ALIGNED
        StringBuilder receipt = new StringBuilder();
        receipt.append("        YOYI'S CAKES & PASTRIES\n");
        receipt.append("              CASH RECEIPT\n");
        receipt.append("**************************************\n");
        receipt.append("Date: ").append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())).append("\n");
        receipt.append("Order ID: #").append(generateOrderId()).append("\n");
        receipt.append("**************************************\n\n");

// Header - Description left, Price right
        receipt.append(String.format("%-30s %10s\n", "Description", "Price"));
        receipt.append("--------------------------------------\n");

        DefaultTableModel cartModel = (DefaultTableModel) tblProducts.getModel();
        for (int i = 0; i < cartModel.getRowCount(); i++) {
        String name = cartModel.getValueAt(i, 1).toString();
        String size = cartModel.getValueAt(i, 2).toString();
        String qty = cartModel.getValueAt(i, 3).toString();
        String price = cartModel.getValueAt(i, 4).toString();
        String subTotal = cartModel.getValueAt(i, 5).toString();
    
    // Format: Product Name (Size) x Qty
        String description = name + " (" + size + ") x" + qty;
    
        receipt.append(String.format("%-30s %10s\n", description, subTotal));
        }

        receipt.append("\n**************************************\n");
        receipt.append(String.format("%-30s %10s\n", "TOTAL:", txtTotal.getText()));
        receipt.append(String.format("%-30s %10s\n", "CASH:", "₱" + String.format("%.2f", cash)));
        receipt.append(String.format("%-30s %10s\n", "CHANGE:", "₱" + String.format("%.2f", change)));
        receipt.append("**************************************\n");
        receipt.append("\n        Thank you for your order! ♡\n");
        receipt.append("           Please come again!\n");

// Show receipt confirmation
        int confirm = JOptionPane.showConfirmDialog(this, 
            receipt.toString(), 
            "Confirm Purchase", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            saveOrderToDatabase(total, cash, change);
            
            cartModel.setRowCount(0);
            txtTotal.setText("");
            txtCash.setText("");
            txtChange.setText("");
            
            JOptionPane.showMessageDialog(this, 
                "✓ Sale completed successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    
    }//GEN-LAST:event_recordpanelMouseClicked

    private int generateOrderId() {
    try {
        Connection con = sqlconnector.getConnection();
        String sql = "SELECT COALESCE(MAX(order_id), 0) + 1 as new_id FROM orders";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int orderId = 1;
        if (rs.next()) {
            orderId = rs.getInt("new_id");
        }
        rs.close();
        st.close();
        con.close();
        return orderId;
    } catch (Exception e) {
        return (int)(System.currentTimeMillis() % 100000); // Fallback
    }
    }
    
    private void saveOrderToDatabase(double total, double cash, double change) {
    try {
        Connection con = sqlconnector.getConnection();
        
        // Get Order ID
        int orderId = generateOrderId();
        
        // Insert into orders table
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insertOrder = "INSERT INTO orders (order_id, order_date, total_amount, order_status) VALUES (?, ?, ?, ?)";
        PreparedStatement pstOrder = con.prepareStatement(insertOrder);
        pstOrder.setInt(1, orderId);
        pstOrder.setString(2, sdf.format(new java.util.Date()));
        pstOrder.setDouble(3, total);
        pstOrder.setString(4, "Completed"); // Set order status
        pstOrder.executeUpdate();
        pstOrder.close();
        
        // Insert into order_items table
        String insertItem = "INSERT INTO order_items (order_id, product_name, size_name, quantity, price, total) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstItem = con.prepareStatement(insertItem);
        
        DefaultTableModel cartModel = (DefaultTableModel) tblProducts.getModel();
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String productName = cartModel.getValueAt(i, 1).toString();
            String size = cartModel.getValueAt(i, 2).toString();
            int quantity = Integer.parseInt(cartModel.getValueAt(i, 3).toString());
            double price = Double.parseDouble(cartModel.getValueAt(i, 4).toString().replace("₱", "").replace(",", ""));
            double itemTotal = Double.parseDouble(cartModel.getValueAt(i, 5).toString().replace("₱", "").replace(",", ""));
            
            pstItem.setInt(1, orderId);
            pstItem.setString(2, productName);
            pstItem.setString(3, size);
            pstItem.setInt(4, quantity);
            pstItem.setDouble(5, price);
            pstItem.setDouble(6, itemTotal);
            pstItem.addBatch();
        }
        pstItem.executeBatch();
        pstItem.close();
        
        // Insert into payment table - FIXED to match your database structure
        String insertPayment = "INSERT INTO payment (payment_date, payment_method, payment_status, amount_paid, order_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstPayment = con.prepareStatement(insertPayment);
        pstPayment.setString(1, sdf.format(new java.util.Date()));
        pstPayment.setString(2, "Cash"); // Payment method
        pstPayment.setString(3, "Paid"); // Payment status
        pstPayment.setDouble(4, cash);   // amount_paid
        pstPayment.setInt(5, orderId);   // order_id
        pstPayment.executeUpdate();
        pstPayment.close();
        
        con.close();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
        e.printStackTrace();
    }
    }
    
    private void resetpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetpanelMouseClicked
        // TODO add your handling code here:
        if (tblProducts.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Cart is already empty!");
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Clear cart and restore stock?\n\nAll items will be removed and stock will be restored.", 
        "Confirm Reset", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            // Restore stock for all items in cart
            Connection con = sqlconnector.getConnection();
            
            for(int i = 0; i < tblProducts.getRowCount(); i++) {
                int productId = Integer.parseInt(tblProducts.getValueAt(i, 0).toString());
                int quantity = Integer.parseInt(tblProducts.getValueAt(i, 3).toString());
                String sizeName = tblProducts.getValueAt(i, 2).toString();
                
                // Get variant_id
                String varSql = "SELECT variant_id FROM product_variant pv " +
                               "JOIN size s ON pv.size_id = s.size_id " +
                               "WHERE pv.product_id = ? AND s.size_name = ?";
                PreparedStatement varPst = con.prepareStatement(varSql);
                varPst.setInt(1, productId);
                varPst.setString(2, sizeName);
                ResultSet varRs = varPst.executeQuery();
                
                if(varRs.next()) {
                    int variantId = varRs.getInt("variant_id");
                    
                    // Restore stock
                    String restoreSql = "UPDATE product_variant SET stock_quantity = stock_quantity + ? WHERE variant_id = ?";
                    PreparedStatement restorePst = con.prepareStatement(restoreSql);
                    restorePst.setInt(1, quantity);
                    restorePst.setInt(2, variantId);
                    restorePst.executeUpdate();
                    restorePst.close();
                }
                varRs.close();
                varPst.close();
            }
            con.close();
            
            // Clear the table
            DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
            model.setRowCount(0);
            
            // Clear total
            txtTotal.setText("");
            
            // Refresh stocks table
            if(stocks.instance != null) {
                stocks.instance.populatetable();
            }
            
            JOptionPane.showMessageDialog(this, "Cart cleared and stock restored!");
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_resetpanelMouseClicked

    private void startDateTime() {
    Timer timer = new Timer(1000, e -> {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MM-dd-yyyy");
        
        String time = timeFormat.format(new Date());
        String date = dateFormat.format(new Date());
        
        lblDateTime.setText(time + "  |  " + date);
    });
    timer.start();
}
    
    private void txtCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCashActionPerformed
        // TODO add your handling code here:
        try {
            // 1. Get the Total amount
            String totalStr = txtTotal.getText().trim();
            if (totalStr.isEmpty()) {
                txtChange.setText("0.00");
                return;
            }
            double totalAmount = Double.parseDouble(totalStr);

            // 2. Get the Cash amount
            String cashStr = txtCash.getText().trim();
            if (cashStr.isEmpty()) {
                txtChange.setText("0.00");
                return;
            }
            double cashAmount = Double.parseDouble(cashStr);

            // 3. Calculate Change
            double changeAmount = cashAmount - totalAmount;

            // 4. Display the result
            if (changeAmount < 0) {
                // Not enough cash
                txtChange.setText("Insufficient");
                // Optional: Change text color to red to warn user
                txtChange.setForeground(Color.RED);
            } else {
                // Sufficient cash
                txtChange.setText(String.format("%.2f", changeAmount));
                // Optional: Change text color to black (or green)
                txtChange.setForeground(Color.BLACK);
            }
            
        } catch (NumberFormatException e) {
            // If user types letters instead of numbers
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Cash.");
        }
    }//GEN-LAST:event_txtCashActionPerformed

    private void txtChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChangeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChangeActionPerformed

    private void cmbusercategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbusercategoryActionPerformed
        // TODO add your handling code here:
        Object selected = cmbusercategory.getSelectedItem();

    if (selected == null || !(selected instanceof CategoryComboItem)) return;
    
    CategoryComboItem item = (CategoryComboItem) selected;
    if (item.getId() == 0) return;

    // Load products into the LEFT table (inside usercategory internal frame)
    for (javax.swing.JInternalFrame frame : desktoppane.getAllFrames()) {
        if (frame instanceof usercategory) {
            ((usercategory) frame).loadProductsForCategory(); 
            break;
        }
    }
    }//GEN-LAST:event_cmbusercategoryActionPerformed

    private void tblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProductsMouseClicked

    private void tblProductsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProductsMouseEntered

    private void reportspanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportspanelMousePressed
        // TODO add your handling code here:
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
        reportspanel.setBackground(ClickedColor);
        
    }//GEN-LAST:event_reportspanelMousePressed
  
    private void reportspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportspanelMouseClicked
        // TODO add your handling code here:
        userreport ur = new userreport();
        desktoppane.removeAll();
        desktoppane.add(ur).setVisible(true);
    }//GEN-LAST:event_reportspanelMouseClicked
    
  
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new usermenu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Logoutpanel;
    javax.swing.JComboBox<CategoryComboItem> cmbusercategory;
    private javax.swing.JDesktopPane desktoppane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblcash;
    private javax.swing.JLabel lblchange;
    private javax.swing.JLabel lbllogout;
    private javax.swing.JLabel lblreports;
    private javax.swing.JLabel lblreset;
    private javax.swing.JLabel lbltotal;
    private javax.swing.JLabel lblttl;
    private javax.swing.JPanel recordpanel;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JPanel resetpanel;
    public javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtChange;
    javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
