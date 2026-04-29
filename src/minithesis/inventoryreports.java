/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package minithesis;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.text.SimpleDateFormat;  // Add this
import java.util.Date;  
/**
 *
 * @author Roged Martin
 */
public class inventoryreports extends javax.swing.JInternalFrame {

    /**
     * Creates new form inventoryreports
     */
    
    public inventoryreports() {
        initComponents();
        loadInventoryReport();
    }
    public void loadInventoryReport() {
        try {
        Connection con = sqlconnector.getConnection();
        
        // FIXED: Calculate "Quantity" (Start of Day) by adding back today's activity
        String query = "SELECT " +
                      "p.product_code as Code, " +
                      "p.product_name as Name, " +
                      "c.category_name as Category, " +
                      "s.size_name as Size, " +
                      "pv.stock_quantity as UpdatedQuantity, " +  // This is the End of Day / Current Stock
                      "pv.variant_id, " +
                      "COALESCE(SUM(CASE WHEN DATE(o.order_date) = CURDATE() THEN oi.quantity ELSE 0 END), 0) as SoldQuantity, " +
                      "COALESCE(SUM(CASE WHEN DATE(qr.pullout_date) = CURDATE() THEN qr.quantity_pulled ELSE 0 END), 0) as PullOuts " +
                      "FROM product p " +
                      "JOIN product_variant pv ON p.product_id = pv.product_id " +
                      "LEFT JOIN category c ON p.category_id = c.category_id " +
                      "LEFT JOIN size s ON pv.size_id = s.size_id " +
                      "LEFT JOIN order_items oi ON p.product_name = oi.product_name AND s.size_name = oi.size_name " +
                      "LEFT JOIN orders o ON oi.order_id = o.order_id " +
                      "LEFT JOIN tblquantityreason qr ON pv.variant_id = qr.variant_id " +
                      "GROUP BY pv.variant_id, p.product_code, p.product_name, " +
                      "c.category_name, s.size_name, pv.stock_quantity " +
                      "ORDER BY p.product_code, s.size_name";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        DefaultTableModel model = (DefaultTableModel) tblinventoryreports.getModel();
        model.setRowCount(0);
        
        while(rs.next()) {
            Vector<Object> coldata = new Vector<>();
            
            String code = rs.getString("Code");
            String name = rs.getString("Name");
            String category = rs.getString("Category");
            String size = rs.getString("Size");
            
            int currentStock = rs.getInt("UpdatedQuantity"); // The actual DB value (e.g., 19)
            int soldQty = rs.getInt("SoldQuantity");
            int pullOuts = rs.getInt("PullOuts");
            
            // CRITICAL FIX:
            // Calculate "Quantity" (Start of Day) = Current Stock + Sold + Pull Outs
            // Example: 19 + 0 + 1 = 20.
            int startOfDayQty = currentStock + soldQty + pullOuts;
            
            coldata.add(getCurrentDate());             // Date
            coldata.add(code);                         // Code
            coldata.add(name);                         // Name
            coldata.add(category);                     // Category
            coldata.add(size);                         // Size
            coldata.add(startOfDayQty);                // Quantity (Start of Day)
            coldata.add(soldQty);                      // Sold Quantity (Today)
            coldata.add(pullOuts);                     // Pull Outs (Today)
            coldata.add(currentStock);                 // Updated Quantity (End of Day / Current)
            
            model.addRow(coldata);
        }
        
        rs.close();
        st.close();
        con.close();
        
    } catch(SQLException e) {
        JOptionPane.showMessageDialog(null, "Error loading inventory report: " + e.getMessage());
        e.printStackTrace();
    }
    }
    
    public void loadInventoryReportByDateRange(String fromDate, String toDate) {
        try {
        Connection con = sqlconnector.getConnection();
        
        // FIXED: Use o.order_date (from orders table)
        String query = "SELECT " +
                      "p.product_code as Code, " +
                      "p.product_name as Name, " +
                      "c.category_name as Category, " +
                      "s.size_name as Size, " +
                      "pv.stock_quantity as Quantity, " +
                      "pv.variant_id, " +
                      "COALESCE(SUM(CASE WHEN o.order_date BETWEEN ? AND ? THEN oi.quantity ELSE 0 END), 0) as SoldQuantity, " +
                      "COALESCE(SUM(CASE WHEN DATE(qr.pullout_date) BETWEEN ? AND ? THEN qr.quantity_pulled ELSE 0 END), 0) as PullOuts " +
                      "FROM product p " +
                      "JOIN product_variant pv ON p.product_id = pv.product_id " +
                      "LEFT JOIN category c ON p.category_id = c.category_id " +
                      "LEFT JOIN size s ON pv.size_id = s.size_id " +
                      "LEFT JOIN order_items oi ON p.product_name = oi.product_name AND s.size_name = oi.size_name " +
                      "LEFT JOIN orders o ON oi.order_id = o.order_id " +
                      "LEFT JOIN tblquantityreason qr ON pv.variant_id = qr.variant_id " +
                      "GROUP BY pv.variant_id, p.product_code, p.product_name, " +
                      "c.category_name, s.size_name, pv.stock_quantity " +
                      "ORDER BY p.product_code, s.size_name";
        
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, fromDate);
        pst.setString(2, toDate);
        pst.setString(3, fromDate);
        pst.setString(4, toDate);
        ResultSet rs = pst.executeQuery();
        
        DefaultTableModel model = (DefaultTableModel) tblinventoryreports.getModel();
        model.setRowCount(0);
        
        while(rs.next()) {
            Vector<Object> coldata = new Vector<>();
            
            String code = rs.getString("Code");
            String name = rs.getString("Name");
            String category = rs.getString("Category");
            String size = rs.getString("Size");
            int currentStock = rs.getInt("Quantity");
            int soldQty = rs.getInt("SoldQuantity");
            int pullOuts = rs.getInt("PullOuts");
            
            int updatedQty = currentStock - soldQty - pullOuts;
            
            coldata.add(fromDate + " to " + toDate);
            coldata.add(code);
            coldata.add(name);
            coldata.add(category);
            coldata.add(size);
            coldata.add(currentStock);
            coldata.add(soldQty);
            coldata.add(pullOuts);
            coldata.add(updatedQty);
            
            model.addRow(coldata);
        }
        
        rs.close();
        pst.close();
        con.close();
        
    } catch(SQLException e) {
        JOptionPane.showMessageDialog(null, "Error filtering report: " + e.getMessage());
        e.printStackTrace();
    }
    }
    
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
 
    private void performEndOfDayReset() {
        Connection con = null;
        try {
            con = sqlconnector.getConnection();
            con.setAutoCommit(false); // Start transaction
            
            // Get all variants with today's activity
            String getQuery = "SELECT pv.variant_id, pv.stock_quantity, " +
                             "COALESCE(SUM(CASE WHEN DATE(oi.order_date) = CURDATE() THEN oi.quantity ELSE 0 END), 0) as sold, " +
                             "COALESCE(SUM(CASE WHEN DATE(qr.pullout_date) = CURDATE() THEN qr.quantity_pulled ELSE 0 END), 0) as pulled " +
                             "FROM product_variant pv " +
                             "LEFT JOIN order_items oi ON pv.variant_id = oi.variant_id " +
                             "LEFT JOIN tblquantityreason qr ON pv.variant_id = qr.variant_id " +
                             "GROUP BY pv.variant_id, pv.stock_quantity";
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getQuery);
            
            int updatedCount = 0;
            
            while (rs.next()) {
                int variantId = rs.getInt("variant_id");
                int currentStock = rs.getInt("stock_quantity");
                int soldToday = rs.getInt("sold");
                int pulledToday = rs.getInt("pulled");
                
                // Calculate remaining stock
                int remainingStock = currentStock - soldToday - pulledToday;
                
                // Update product_variant: set stock to remaining amount
                String updateSql = "UPDATE product_variant SET stock_quantity = ? WHERE variant_id = ?";
                PreparedStatement pst = con.prepareStatement(updateSql);
                pst.setInt(1, remainingStock);
                pst.setInt(2, variantId);
                pst.executeUpdate();
                pst.close();
                
                updatedCount++;
            }
            
            rs.close();
            st.close();
            
            // Commit the transaction
            con.commit();
            
            // Reload report to show reset values (all counters now 0 for today)
            loadInventoryReport();
            
            JOptionPane.showMessageDialog(
                this,
                "✓ End of Day Reset Complete!\n" +
                "Updated " + updatedCount + " products.\n" +
                "Stock carried over. Daily counters reset.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (SQLException e) {
            // Rollback on error
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            JOptionPane.showMessageDialog(
                this,
                "Error during reset: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (Exception e) {}
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblinventoryreports = new javax.swing.JTable();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        datefrom = new javax.swing.JLabel();
        dateto = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        btnFilter = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(204, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));

        tblinventoryreports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Code", "Name", "Category", "Size", "Quantity", "Sold Quantity", "Pull Outs", "Updated Quantity"
            }
        ));
        jScrollPane1.setViewportView(tblinventoryreports);

        datefrom.setText("From : ");

        dateto.setText("To :");

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        btnFilter.setText("Filter");
        btnFilter.addActionListener(this::btnFilterActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(dateto)
                                .addGap(65, 65, 65)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(datefrom)
                                .addGap(47, 47, 47)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFilter)))
                .addGap(298, 298, 298))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(datefrom)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateto)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFilter)
                            .addComponent(btnReset))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        
        // Reload all data
        loadInventoryReport();
        
        JOptionPane.showMessageDialog(this, 
            "Filter cleared - showing all inventory data",
            "Reset Complete",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        try {
            // Get dates from JDateChooser components
            if (jDateChooser1.getDate() == null || jDateChooser2.getDate() == null) {
                JOptionPane.showMessageDialog(this, 
                    "Please select both FROM and TO dates!", 
                    "Missing Dates", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = sdf.format(jDateChooser1.getDate());
            String toDate = sdf.format(jDateChooser2.getDate());
            
            // Validate date range
            if (jDateChooser1.getDate().after(jDateChooser2.getDate())) {
                JOptionPane.showMessageDialog(this, 
                    "FROM date cannot be after TO date!", 
                    "Invalid Date Range", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Load filtered report
            loadInventoryReportByDateRange(fromDate, toDate);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnFilterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel datefrom;
    private javax.swing.JLabel dateto;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblinventoryreports;
    // End of variables declaration//GEN-END:variables
}
