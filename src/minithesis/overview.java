/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package minithesis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author janxt
 */
public class overview extends javax.swing.JInternalFrame {

    /**
     * Creates new form overview
     */
    public overview() {
        initComponents();
        loadDashboardData();
        loadRecentTransactions();
    }

    private void loadDashboardData() {
        Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    try {
        conn = sqlconnector.getConnection();
        
        // 1. Total Sales (All time)
        String salesQuery = "SELECT COALESCE(SUM(total_amount), 0) as total FROM orders";
        pst = conn.prepareStatement(salesQuery);
        rs = pst.executeQuery();
        if (rs.next()) {
            double totalSales = rs.getDouble("total");
            txtoverviewsales.setText(String.format("₱%,.2f", totalSales));
        }
        rs.close();
        pst.close();
        
        // 2. Total Orders Count
        String ordersQuery = "SELECT COUNT(*) as count FROM orders";
        pst = conn.prepareStatement(ordersQuery);
        rs = pst.executeQuery();
        if (rs.next()) {
            int totalOrders = rs.getInt("count");
            txttotalorders.setText(String.valueOf(totalOrders));
        }
        rs.close();
        pst.close();
        
        // 3. Best Selling Product (by quantity from order_items)
        String bestSellerQuery = "SELECT product_name, SUM(quantity) as total_qty " +
                                 "FROM order_items " +
                                 "GROUP BY product_name " +
                                 "ORDER BY total_qty DESC " +
                                 "LIMIT 1";
        pst = conn.prepareStatement(bestSellerQuery);
        rs = pst.executeQuery();
        if (rs.next()) {
            String bestSeller = rs.getString("product_name");
            int qty = rs.getInt("total_qty");
            txtbestseller.setText(bestSeller);
        } else {
            txtbestseller.setText("No sales data yet");
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading dashboard: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (pst != null) pst.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
    }
    
    private void loadRecentTransactions() {
            try {
        Connection con = sqlconnector.getConnection();
        
        // FIXED: Join orders and order_items to get Product Name, Size, Qty, etc.
        String query = "SELECT o.order_date, o.order_id, oi.product_name, oi.size_name, " +
                      "oi.quantity, oi.price, oi.total " +
                      "FROM orders o " +
                      "JOIN order_items oi ON o.order_id = oi.order_id " +
                      "ORDER BY o.order_date DESC, o.order_id DESC LIMIT 10";
        
        // Use PreparedStatement instead of Statement (better practice)
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        
        // Clear the table before adding new data
        DefaultTableModel model = (DefaultTableModel) tbloverview.getModel();
        model.setRowCount(0);
        
        while (rs.next()) {
            Vector<Object> coldata = new Vector<>();
            
            // 1. Date
            String dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("order_date"));
            coldata.add(dateStr);
            
            // 2. Order ID
            coldata.add(rs.getInt("order_id"));
            
            // 3. Name (Product Name)
            coldata.add(rs.getString("product_name"));
            
            // 4. Size
            coldata.add(rs.getString("size_name"));
            
            // 5. Quantity
            coldata.add(rs.getInt("quantity"));
            
            // 6. Price (Formatted with Peso sign)
            coldata.add(String.format("₱%.2f", rs.getDouble("price")));
            
            // 7. Total (Formatted with Peso sign)
            coldata.add(String.format("₱%.2f", rs.getDouble("total")));
            
            model.addRow(coldata);
        }
        
        // Close resources
        rs.close();
        pst.close();
        con.close();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading transactions: " + e.getMessage());
        e.printStackTrace();
    }
    }
    
    private String formatDateTime(java.sql.Timestamp timestamp) {
        if (timestamp == null) return "-";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        return sdf.format(timestamp);
    }

    private String formatStatus(String status) {
        if (status == null) return "Unknown";
        switch (status.toLowerCase()) {
            case "completed": return "✓ Completed";
            case "pending": return "⏳ Pending";
            case "cancelled": return "✗ Cancelled";
            default: return status;
        }
    }
    
    private void applyTableStyling() {
        tbloverview.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Center align Order ID, Date, Payment, Status
                if (column == 0 || column == 1 || column == 4 || column == 5) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                
                // Bold header row styling is automatic, style data rows
                setFont(getFont().deriveFont(Font.PLAIN));
                
                // Alternate row colors for readability
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                
                // Color-code status column
                if (column == 5 && value != null) {
                    String status = value.toString().toLowerCase();
                    if (status.contains("completed")) {
                        setForeground(new Color(46, 204, 113)); // Green
                    } else if (status.contains("pending")) {
                        setForeground(new Color(241, 196, 15)); // Yellow/Orange
                    } else if (status.contains("cancelled")) {
                        setForeground(new Color(231, 76, 60)); // Red
                    } else {
                        setForeground(Color.BLACK);
                    }
                } else {
                    setForeground(Color.BLACK);
                }
                
                return c;
            }
        });
        
        tbloverview.setRowHeight(30);
        tbloverview.setShowGrid(false);
        tbloverview.getTableHeader().setReorderingAllowed(false);
        tbloverview.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblrecenttransaction = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbloverview = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblbestseller = new javax.swing.JLabel();
        txtbestseller = new javax.swing.JTextField();
        lbltotalsales = new javax.swing.JLabel();
        txtoverviewsales = new javax.swing.JTextField();
        lbltotalorders = new javax.swing.JLabel();
        txttotalorders = new javax.swing.JTextField();
        OVERVIEW = new javax.swing.JLabel();
        btnrefresh = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(974, 664));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblrecenttransaction.setFont(new java.awt.Font("Gill Sans Ultra Bold", 1, 18)); // NOI18N
        lblrecenttransaction.setForeground(new java.awt.Color(102, 0, 0));
        lblrecenttransaction.setText("Recent Transaction: ");
        getContentPane().add(lblrecenttransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, -1));

        tbloverview.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Order ID", "Name", "Size", "Quantity", "Price", "Total"
            }
        ));
        jScrollPane1.setViewportView(tbloverview);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 940, 240));

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblbestseller.setFont(new java.awt.Font("Gill Sans Ultra Bold", 1, 14)); // NOI18N
        lblbestseller.setForeground(new java.awt.Color(102, 0, 0));
        lblbestseller.setText("Best Selling Product: ");
        jPanel1.add(lblbestseller, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, -1, 20));

        txtbestseller.setBackground(new java.awt.Color(102, 0, 0));
        txtbestseller.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        txtbestseller.setForeground(new java.awt.Color(255, 255, 255));
        txtbestseller.addActionListener(this::txtbestsellerActionPerformed);
        jPanel1.add(txtbestseller, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 420, 60));

        lbltotalsales.setFont(new java.awt.Font("Gill Sans Ultra Bold", 1, 14)); // NOI18N
        lbltotalsales.setForeground(new java.awt.Color(102, 0, 0));
        lbltotalsales.setText("Total Sales: ");
        jPanel1.add(lbltotalsales, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        txtoverviewsales.setBackground(new java.awt.Color(102, 0, 0));
        txtoverviewsales.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        txtoverviewsales.setForeground(new java.awt.Color(255, 255, 255));
        txtoverviewsales.setText("0.00");
        jPanel1.add(txtoverviewsales, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 360, 60));

        lbltotalorders.setFont(new java.awt.Font("Gill Sans Ultra Bold", 1, 14)); // NOI18N
        lbltotalorders.setForeground(new java.awt.Color(102, 0, 0));
        lbltotalorders.setText("Total Orders: ");
        jPanel1.add(lbltotalorders, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        txttotalorders.setBackground(new java.awt.Color(102, 0, 0));
        txttotalorders.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        txttotalorders.setForeground(new java.awt.Color(255, 255, 255));
        txttotalorders.setText("0");
        jPanel1.add(txttotalorders, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 360, 60));

        OVERVIEW.setFont(new java.awt.Font("Showcard Gothic", 1, 55)); // NOI18N
        OVERVIEW.setForeground(new java.awt.Color(102, 0, 0));
        OVERVIEW.setText("OVERVIEW");
        jPanel1.add(OVERVIEW, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 320, 80));

        btnrefresh.setText("Refresh");
        btnrefresh.addActionListener(this::btnrefreshActionPerformed);
        jPanel1.add(btnrefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtbestsellerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbestsellerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbestsellerActionPerformed

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        // TODO add your handling code here:
         loadDashboardData();
        loadRecentTransactions();
        JOptionPane.showMessageDialog(this, "Dashboard refreshed!", "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnrefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OVERVIEW;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblbestseller;
    private javax.swing.JLabel lblrecenttransaction;
    private javax.swing.JLabel lbltotalorders;
    private javax.swing.JLabel lbltotalsales;
    private javax.swing.JTable tbloverview;
    private javax.swing.JTextField txtbestseller;
    private javax.swing.JTextField txtoverviewsales;
    private javax.swing.JTextField txttotalorders;
    // End of variables declaration//GEN-END:variables
}
