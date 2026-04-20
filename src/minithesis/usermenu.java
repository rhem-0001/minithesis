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

public class usermenu extends javax.swing.JFrame {
    
    private HashMap<Integer, Integer> cartStockDeductions = new HashMap<>();
    private static final Logger logger = Logger.getLogger(usermenu.class.getName());

    
    Color DefaultColor, ClickedColor;
    public usermenu() {
        initComponents();
        
        usercategory uc = new usercategory();
        desktoppane.add(uc);
        uc.setVisible(true);
        
        startDateTime();
        
        DefaultColor = new Color(255,255,255);
        ClickedColor = new Color(204,0,0); 
        
    }
    
     // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    
    
    
    
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
        jTable1 = new javax.swing.JTable();
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Size", "Quantity", "Price", "SubTotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

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

        cmbusercategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                                .addComponent(recordpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Logoutpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resetpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(recordpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
      
    private void LogoutpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutpanelMousePressed
        // TODO add your handling code here:

        Logoutpanel.setBackground(ClickedColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
        
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
    }//GEN-LAST:event_resetpanelMousePressed

    private void recordpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordpanelMousePressed
        // TODO add your handling code here:

        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(ClickedColor);
    }//GEN-LAST:event_recordpanelMousePressed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void lbltotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbltotalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbltotalMouseClicked

    private void recordpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordpanelMouseClicked
        // TODO add your handling code here:
//        if (jTable1.getRowCount() == 0) {
//        JOptionPane.showMessageDialog(this, "Cart is empty! Please add items first.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
//        return;
//    }
//    
//    if (txtCash.getText().trim().isEmpty()) {
//        JOptionPane.showMessageDialog(this, 
//            "💵 Please input cash amount first!", 
//            "Missing Payment", 
//            JOptionPane.WARNING_MESSAGE);
//        txtCash.requestFocus();
//        return;
//    }
//    
//    double total = 0.0;
//    double cash = 0.0;
//    
//    try {
//        total = Double.parseDouble(txtTotal.getText());
//        cash = Double.parseDouble(txtCash.getText());
//    } catch (NumberFormatException e) {
//        JOptionPane.showMessageDialog(this, 
//            "Invalid cash amount! Please enter a valid number.", 
//            "Invalid Input", 
//            JOptionPane.ERROR_MESSAGE);
//        return;
//    }
//    
//    if (cash <= 0) {
//        JOptionPane.showMessageDialog(this, 
//            "💵 Please enter a valid cash amount greater than 0!\n\n" +
//            "Total Amount Due: ₱" + String.format("%.2f", total), 
//            "Invalid Payment", 
//            JOptionPane.WARNING_MESSAGE);
//        txtCash.requestFocus();
//        txtCash.selectAll();
//        return;
//    }
//    
//    if (cash < total) {
//        JOptionPane.showMessageDialog(this, 
//            "❌ Insufficient cash!\n\n" +
//            "Total Amount: ₱" + String.format("%.2f", total) + "\n" +
//            "Cash Given:   ₱" + String.format("%.2f", cash) + "\n" +
//            "Short by:     ₱" + String.format("%.2f", (total - cash)) + "\n\n" +
//            "Please enter at least ₱" + String.format("%.2f", total), 
//            "Insufficient Payment", 
//            JOptionPane.ERROR_MESSAGE);
//        txtCash.requestFocus();
//        txtCash.selectAll();
//        return;
//    }
//    
//    try {
//        Connection con = sqlconnector.getConnection();
//        con.setAutoCommit(false);
//        
//        // 1. Insert Order
//        String orderSQL = "INSERT INTO orders (order_id, order_date, order_status, total_amount) VALUES (NULL, NOW(), 'Completed', ?)";
//        PreparedStatement pstOrder = con.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
//        pstOrder.setDouble(1, total);
//        pstOrder.executeUpdate();
//        
//        // Get Order ID
//        ResultSet rsKeys = pstOrder.getGeneratedKeys();
//        int orderId = 0;
//        if (rsKeys.next()) {
//            orderId = rsKeys.getInt(1);
//        }
//        
//        // 2. Save each item from cart to order_items table
//        for (int i = 0; i < jTable1.getRowCount(); i++) {
//            // Get data from jTable1 (your cart table)
//            // Adjust column indexes based on YOUR table structure
//            int variantId = Integer.parseInt(jTable1.getValueAt(i, 0).toString()); // Column 0
//            String productName = jTable1.getValueAt(i, 1).toString(); // Column 1
//            String sizeName = jTable1.getValueAt(i, 2).toString(); // Column 2
//            int quantity = Integer.parseInt(jTable1.getValueAt(i, 3).toString()); // Column 3
//            double price = Double.parseDouble(jTable1.getValueAt(i, 4).toString()); // Column 4 (Price)
//            double subtotal = Double.parseDouble(jTable1.getValueAt(i, 5).toString()); // Column 5 (if needed)
//    
//    // ... rest of your insert code
//            
//            // Insert into order_items
//            String itemSQL = "INSERT INTO order_items (order_id, product_name, size_name, quantity, price, total) VALUES (?, ?, ?, ?, ?, ?)";
//            PreparedStatement pstItem = con.prepareStatement(itemSQL);
//            pstItem.setInt(1, orderId);
//            pstItem.setString(2, productName);
//            pstItem.setString(3, sizeName);
//            pstItem.setInt(4, quantity);
//            pstItem.setDouble(5, price);
//            pstItem.setDouble(6, subtotal);
//            pstItem.executeUpdate();
//        }
//        
//        // 3. Insert Payment
//        String paySQL = "INSERT INTO payment (payment_date, payment_method, payment_status, amount_paid, order_id) VALUES (CURDATE(), 'Cash', 'Paid', ?, ?)";
//        PreparedStatement pstPay = con.prepareStatement(paySQL);
//        pstPay.setDouble(1, cash);
//        pstPay.setInt(2, orderId);
//        pstPay.executeUpdate();
//        
//        // 4. Update Daily Sales
//        String salesSQL = "INSERT INTO daily_sales (sales_date, total_orders, total_sales_amount) VALUES (CURDATE(), 1, ?) ON DUPLICATE KEY UPDATE total_orders = total_orders + 1, total_sales_amount = total_sales_amount + ?";
//        PreparedStatement pstSales = con.prepareStatement(salesSQL);
//        pstSales.setDouble(1, total);
//        pstSales.setDouble(2, total);
//        pstSales.executeUpdate();
//        
//        con.commit();
//        
//        double change = cash - total;
//        JOptionPane.showMessageDialog(this, 
//            "✅ Transaction Successful!\n\n" +
//            "Order ID: " + orderId + 
//            "\nTotal: ₱" + String.format("%.2f", total) +
//            "\nCash: ₱" + String.format("%.2f", cash) +
//            "\nChange: ₱" + String.format("%.2f", change), 
//            "Success", JOptionPane.INFORMATION_MESSAGE);
//
//        // Clear cart
//        cartStockDeductions.clear();
//        clearCart();
//        refreshFoodMenuStock();
//        
//        pstOrder.close();
//        pstPay.close();
//        pstSales.close();
//        con.close();
//        
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage(), "Transaction Failed", JOptionPane.ERROR_MESSAGE);
//        e.printStackTrace();
//    }
    
    }//GEN-LAST:event_recordpanelMouseClicked

    private void resetpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetpanelMouseClicked
        // TODO add your handling code here:
        if (jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Cart is already empty!");
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Clear cart and restore stock?\n\nAll items will be removed and stock will be restored.", 
        "Confirm Reset", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
//        clearCart();
        JOptionPane.showMessageDialog(this, "Cart cleared and stock restored!");
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
    }//GEN-LAST:event_txtCashActionPerformed

    private void txtChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChangeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChangeActionPerformed

  
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
    private javax.swing.JComboBox<String> cmbusercategory;
    private javax.swing.JDesktopPane desktoppane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblcash;
    private javax.swing.JLabel lblchange;
    private javax.swing.JLabel lbllogout;
    private javax.swing.JLabel lblreset;
    private javax.swing.JLabel lbltotal;
    private javax.swing.JLabel lblttl;
    private javax.swing.JPanel recordpanel;
    private javax.swing.JPanel resetpanel;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
