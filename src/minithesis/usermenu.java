package minithesis;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.*;
import java.math.BigDecimal;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class usermenu extends javax.swing.JFrame {
    
    private static final Logger logger = Logger.getLogger(usermenu.class.getName());

    Color DefaultColor, ClickedColor;
    private double totalAmount = 0;
    
    public usermenu() {
        initComponents();
        barscategory barsFrame = new barscategory(this);
        desktoppane.add(barsFrame);
        barsFrame.setVisible(true);

        try {
            barsFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        
        DefaultColor = new Color(255,255,255);
        ClickedColor = new Color(204,0,0);
        
        // Initialize table and fields
        txtTotal.setText("0.00");
        txtCash.setText("");
        txtChange.setText("0.00");
    }
    
     // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    private void calculateGrandTotal() {
        double total = 0.0;
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
        Object val = model.getValueAt(i, 4); // Column 4 = Line Total
        if (val != null) total += Double.parseDouble(val.toString());
    }
    txtTotal.setText(String.format("%.2f", total));
    calculateChange();
    }
    
    
    private void calculateChange() {
        try {
            double total = txtTotal.getText().isEmpty() ? 0.0 : Double.parseDouble(txtTotal.getText());
            double cash = txtCash.getText().isEmpty() ? 0.0 : Double.parseDouble(txtCash.getText());
            
            double change = cash - total;
            txtChange.setText(String.format("%.2f", change));
        } catch (NumberFormatException e) {
            txtChange.setText("0.00");
        }
    }
    
    
    public void addToCart(int variantId, String productId, String productIdByName, String productName, String sizeName, double price, int qty) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        // Check if item already exists
         boolean itemExists = false;
    
    // Check if this exact variant is already in cart
    for (int i = 0; i < model.getRowCount(); i++) {
        Object existingVariantObj = model.getValueAt(i, 0);
        Object existingProductObj = model.getValueAt(i, 1);
        Object existingSizeObj = model.getValueAt(i, 2);
        
        if (existingVariantObj != null && existingProductObj != null && existingSizeObj != null) {
            int existingVariantId = Integer.parseInt(existingVariantObj.toString());
            String existingProductName = existingProductObj.toString();
            String existingSize = existingSizeObj.toString();
            
            // Only combine if variantId AND product name AND size ALL match
            if (existingVariantId == variantId && 
                existingProductName.equals(productName) && 
                existingSize.equals(sizeName)) {
                
                // Update quantity
                int currentQty = Integer.parseInt(model.getValueAt(i, 3).toString());
                int newQty = currentQty + qty;
                model.setValueAt(newQty, i, 3);
                
                // Update subtotal (Price column)
                double subtotal = price * newQty;
                model.setValueAt(String.format("%.2f", subtotal), i, 4);
                
                itemExists = true;
                break;
            }
        }
    }
    
    // Add as NEW row if different product
    if (!itemExists) {
        double subtotal = price * qty;
        model.addRow(new Object[]{
            variantId,
            productName,
            sizeName,
            qty,
            String.format("%.2f", subtotal)  // This shows as "Price" in your UI
        });
    }
    
    calculateGrandTotal();
    deductStock(variantId, qty);
    }
    
    private void deductStock(int variantId, int quantity) {
        try {
            Connection con = sqlconnector.getConnection();
            
            String checkSQL = "SELECT stock_quantity FROM product_variant WHERE variant_id = ?";
            PreparedStatement pstCheck = con.prepareStatement(checkSQL);
            pstCheck.setInt(1, variantId);
            ResultSet rs = pstCheck.executeQuery();
            
            if (rs.next()) {
                int currentStock = rs.getInt("stock_quantity");
                if (currentStock < quantity) {
                    JOptionPane.showMessageDialog(this, 
                        "Insufficient stock! Only " + currentStock + " available.", 
                        "Stock Error", JOptionPane.ERROR_MESSAGE);
                    rs.close();
                    pstCheck.close();
                    con.close();
                    return;
                }
            }
            
            String sql = "UPDATE product_variant SET stock_quantity = stock_quantity - ? WHERE variant_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setInt(2, variantId);
            pst.executeUpdate();
            
            rs.close();
            pstCheck.close();
            pst.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating stock: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void refreshFoodMenuStock() {
    try {
        if (foodmenu.instance != null) {
            // Use SwingUtilities to ensure it runs on EDT
            javax.swing.SwingUtilities.invokeLater(() -> {
                foodmenu.instance.populatetable();
            });
        }
        
        if (stocks.instance != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                stocks.instance.populatetable();
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    private void refreshAllTables() {
        try {
            if (foodmenu.instance != null) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    foodmenu.instance.populatetable();
                });
            }
            
            if (stocks.instance != null) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    stocks.instance.populatetable();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void txtCashActionPerformed(java.awt.event.ActionEvent evt) {
    calculateChange();
    }

    // Also add a focus listener or document listener if you want real-time update
    private void txtCashFocusLost(java.awt.event.FocusEvent evt) {
    calculateChange();
    }
 
    private void clearCart() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        txtTotal.setText("0.00");
        txtCash.setText("");
        txtChange.setText("0.00");
    }
    
    class PurchaseItem {
        String productId;
        String productName;
        String size;
        double price;
        int quantity;
        
        PurchaseItem(String id, String name, String size, double price, int qty) {
            this.productId = id;
            this.productName = name;
            this.size = size;
            this.price = price;
            this.quantity = qty;
        }
        
        double getSubtotal() {
            return price * quantity;
        }
    }
    
    public void init(){
        
    }
    
    public boolean qtyIsZero(int qty){
        if(qty==0){
            JOptionPane.showMessageDialog(null, "Please increase the item quantity!");
            return false;
        }
        return true;
    }
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel21 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        barspanel = new javax.swing.JPanel();
        lblbars = new javax.swing.JLabel();
        cakespanel = new javax.swing.JPanel();
        lblcakes = new javax.swing.JLabel();
        cookiespanel = new javax.swing.JPanel();
        lblcookies = new javax.swing.JLabel();
        cupcakespanel = new javax.swing.JPanel();
        lblcupcakes = new javax.swing.JLabel();
        loavespanel = new javax.swing.JPanel();
        lblloaves = new javax.swing.JLabel();
        piespanel = new javax.swing.JPanel();
        lblpies = new javax.swing.JLabel();
        rollspanel = new javax.swing.JPanel();
        lblrolls = new javax.swing.JLabel();
        tartspanel = new javax.swing.JPanel();
        lbltarts = new javax.swing.JLabel();
        otherpanel = new javax.swing.JPanel();
        lblothers = new javax.swing.JLabel();
        Logoutpanel = new javax.swing.JPanel();
        lbllogout = new javax.swing.JLabel();
        resetpanel = new javax.swing.JPanel();
        lblreset = new javax.swing.JLabel();
        recordpanel = new javax.swing.JPanel();
        lbltotal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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

        barspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                barspanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barspanelMousePressed(evt);
            }
        });

        lblbars.setText("Bars");

        javax.swing.GroupLayout barspanelLayout = new javax.swing.GroupLayout(barspanel);
        barspanel.setLayout(barspanelLayout);
        barspanelLayout.setHorizontalGroup(
            barspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barspanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblbars)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        barspanelLayout.setVerticalGroup(
            barspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblbars, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        cakespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cakespanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cakespanelMousePressed(evt);
            }
        });

        lblcakes.setText("Cakes");

        javax.swing.GroupLayout cakespanelLayout = new javax.swing.GroupLayout(cakespanel);
        cakespanel.setLayout(cakespanelLayout);
        cakespanelLayout.setHorizontalGroup(
            cakespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cakespanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblcakes, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        cakespanelLayout.setVerticalGroup(
            cakespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cakespanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblcakes, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        cookiespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cookiespanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cookiespanelMousePressed(evt);
            }
        });

        lblcookies.setText("Cookies");

        javax.swing.GroupLayout cookiespanelLayout = new javax.swing.GroupLayout(cookiespanel);
        cookiespanel.setLayout(cookiespanelLayout);
        cookiespanelLayout.setHorizontalGroup(
            cookiespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cookiespanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblcookies)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        cookiespanelLayout.setVerticalGroup(
            cookiespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cookiespanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblcookies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        cupcakespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cupcakespanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cupcakespanelMousePressed(evt);
            }
        });

        lblcupcakes.setText("Cupcakes");

        javax.swing.GroupLayout cupcakespanelLayout = new javax.swing.GroupLayout(cupcakespanel);
        cupcakespanel.setLayout(cupcakespanelLayout);
        cupcakespanelLayout.setHorizontalGroup(
            cupcakespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cupcakespanelLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(lblcupcakes)
                .addGap(24, 24, 24))
        );
        cupcakespanelLayout.setVerticalGroup(
            cupcakespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cupcakespanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblcupcakes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        loavespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loavespanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                loavespanelMousePressed(evt);
            }
        });

        lblloaves.setText("Loaves");

        javax.swing.GroupLayout loavespanelLayout = new javax.swing.GroupLayout(loavespanel);
        loavespanel.setLayout(loavespanelLayout);
        loavespanelLayout.setHorizontalGroup(
            loavespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loavespanelLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(lblloaves, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        loavespanelLayout.setVerticalGroup(
            loavespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loavespanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblloaves, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        piespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                piespanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                piespanelMousePressed(evt);
            }
        });

        lblpies.setText("Pies");

        javax.swing.GroupLayout piespanelLayout = new javax.swing.GroupLayout(piespanel);
        piespanel.setLayout(piespanelLayout);
        piespanelLayout.setHorizontalGroup(
            piespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(piespanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(lblpies)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        piespanelLayout.setVerticalGroup(
            piespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(piespanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblpies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        rollspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rollspanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                rollspanelMousePressed(evt);
            }
        });

        lblrolls.setText("Rolls");

        javax.swing.GroupLayout rollspanelLayout = new javax.swing.GroupLayout(rollspanel);
        rollspanel.setLayout(rollspanelLayout);
        rollspanelLayout.setHorizontalGroup(
            rollspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollspanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblrolls)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        rollspanelLayout.setVerticalGroup(
            rollspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollspanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblrolls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tartspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tartspanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tartspanelMousePressed(evt);
            }
        });

        lbltarts.setText("Tarts");

        javax.swing.GroupLayout tartspanelLayout = new javax.swing.GroupLayout(tartspanel);
        tartspanel.setLayout(tartspanelLayout);
        tartspanelLayout.setHorizontalGroup(
            tartspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tartspanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lbltarts)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        tartspanelLayout.setVerticalGroup(
            tartspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tartspanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbltarts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        otherpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                otherpanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                otherpanelMousePressed(evt);
            }
        });

        lblothers.setText("Others");

        javax.swing.GroupLayout otherpanelLayout = new javax.swing.GroupLayout(otherpanel);
        otherpanel.setLayout(otherpanelLayout);
        otherpanelLayout.setHorizontalGroup(
            otherpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, otherpanelLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(lblothers, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        otherpanelLayout.setVerticalGroup(
            otherpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(otherpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblothers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Product ID", "Product Name", "Size", "Quantity", "Price"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
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
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(barspanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cakespanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cookiespanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cupcakespanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loavespanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(piespanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rollspanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tartspanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(otherpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(Logoutpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(resetpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(recordpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(desktoppane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(barspanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cakespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cookiespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cupcakespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loavespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(piespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rollspanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tartspanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(otherpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtChange))
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
      
    private void barspanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barspanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(ClickedColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
      
    }//GEN-LAST:event_barspanelMousePressed

    private void cakespanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cakespanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(ClickedColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_cakespanelMousePressed

    private void cookiespanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cookiespanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(ClickedColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_cookiespanelMousePressed

    private void cupcakespanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cupcakespanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(ClickedColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_cupcakespanelMousePressed

    private void loavespanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loavespanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(ClickedColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_loavespanelMousePressed

    private void piespanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_piespanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(ClickedColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_piespanelMousePressed

    private void rollspanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rollspanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(ClickedColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_rollspanelMousePressed

    private void tartspanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tartspanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(ClickedColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_tartspanelMousePressed

    private void otherpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_otherpanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(ClickedColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(DefaultColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_otherpanelMousePressed

    private void barspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barspanelMouseClicked
        // TODO add your handling code here:
        barscategory bc = new barscategory(this);
        desktoppane.removeAll();
        desktoppane.add(bc).setVisible(true);
    }//GEN-LAST:event_barspanelMouseClicked

    private void LogoutpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutpanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
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

    private void cakespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cakespanelMouseClicked
        // TODO add your handling code here:
        cakescategory cc = new cakescategory();
        desktoppane.removeAll();
        desktoppane.add(cc).setVisible(true);
    }//GEN-LAST:event_cakespanelMouseClicked

    private void cookiespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cookiespanelMouseClicked
        // TODO add your handling code here:
        cookiescategory coc = new cookiescategory();
        desktoppane.removeAll();
        desktoppane.add(coc).setVisible(true);
    }//GEN-LAST:event_cookiespanelMouseClicked

    private void cupcakespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cupcakespanelMouseClicked
        // TODO add your handling code here:
        cupcakescategory cupc = new cupcakescategory();
        desktoppane.removeAll();
        desktoppane.add(cupc).setVisible(true);
    }//GEN-LAST:event_cupcakespanelMouseClicked

    private void loavespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loavespanelMouseClicked
        // TODO add your handling code here:
        loavescategory lc = new loavescategory();
        desktoppane.removeAll();
        desktoppane.add(lc).setVisible(true);
    }//GEN-LAST:event_loavespanelMouseClicked

    private void piespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_piespanelMouseClicked
        // TODO add your handling code here:
        piescategory piec = new piescategory();
        desktoppane.removeAll();
        desktoppane.add(piec).setVisible(true);
    }//GEN-LAST:event_piespanelMouseClicked

    private void rollspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rollspanelMouseClicked
        // TODO add your handling code here:
        rollscategory rc = new rollscategory();
        desktoppane.removeAll();
        desktoppane.add(rc).setVisible(true);
    }//GEN-LAST:event_rollspanelMouseClicked

    private void tartspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tartspanelMouseClicked
        // TODO add your handling code here:
        tartscategory tc = new tartscategory();
        desktoppane.removeAll();
        desktoppane.add(tc).setVisible(true);
    }//GEN-LAST:event_tartspanelMouseClicked

    private void otherpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_otherpanelMouseClicked
        // TODO add your handling code here:
        otherscategory oc = new otherscategory();
        desktoppane.removeAll();
        desktoppane.add(oc).setVisible(true);
    }//GEN-LAST:event_otherpanelMouseClicked

    private void resetpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetpanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
        Logoutpanel.setBackground(DefaultColor);
        resetpanel.setBackground(ClickedColor);
        recordpanel.setBackground(DefaultColor);
    }//GEN-LAST:event_resetpanelMousePressed

    private void recordpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordpanelMousePressed
        // TODO add your handling code here:
        barspanel.setBackground(DefaultColor);
        cakespanel.setBackground(DefaultColor);
        cookiespanel.setBackground(DefaultColor);
        cupcakespanel.setBackground(DefaultColor);
        loavespanel.setBackground(DefaultColor);
        piespanel.setBackground(DefaultColor);
        rollspanel.setBackground(DefaultColor);
        tartspanel.setBackground(DefaultColor);
        otherpanel.setBackground(DefaultColor);
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
        if (jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Cart is empty! Please add items first.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    double total = txtTotal.getText().isEmpty() ? 0.0 : Double.parseDouble(txtTotal.getText());
    double cash = txtCash.getText().isEmpty() ? 0.0 : Double.parseDouble(txtCash.getText());
    
    if (cash < total) {
        JOptionPane.showMessageDialog(this, "Insufficient cash!\nTotal: ₱" + String.format("%.2f", total), "Payment Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        Connection con = sqlconnector.getConnection();
        con.setAutoCommit(false);
        
        // 1. Insert Order (without order_details)
        String orderSQL = "INSERT INTO orders (order_date, order_status, total_amount) VALUES (CURDATE(), 'Completed', ?)";
        PreparedStatement pstOrder = con.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
        pstOrder.setDouble(1, total);
        pstOrder.executeUpdate();
        
        // Get Order ID
        ResultSet rsKeys = pstOrder.getGeneratedKeys();
        int orderId = 0;
        if (rsKeys.next()) {
            orderId = rsKeys.getInt(1);
        }
        
        // 2. Insert Payment
        String paySQL = "INSERT INTO payment (payment_date, payment_method, payment_status, amount_paid, order_id) VALUES (CURDATE(), 'Cash', 'Paid', ?, ?)";
        PreparedStatement pstPay = con.prepareStatement(paySQL);
        pstPay.setDouble(1, cash);
        pstPay.setInt(2, orderId);
        pstPay.executeUpdate();
        
        // 3. Update Daily Sales
        String salesSQL = "INSERT INTO daily_sales (sales_date, total_orders, total_sales_amount) VALUES (CURDATE(), 1, ?) ON DUPLICATE KEY UPDATE total_orders = total_orders + 1, total_sales_amount = total_sales_amount + ?";
        PreparedStatement pstSales = con.prepareStatement(salesSQL);
        pstSales.setDouble(1, total);
        pstSales.setDouble(2, total);
        pstSales.executeUpdate();
        
        con.commit();
        
        double change = cash - total;
        JOptionPane.showMessageDialog(this, 
            "✅ Transaction Successful!\nOrder ID: " + orderId + 
            "\nTotal: ₱" + String.format("%.2f", total) +
            "\nChange: ₱" + String.format("%.2f", change), "Success", JOptionPane.INFORMATION_MESSAGE);
        
        clearCart();
        refreshFoodMenuStock();
        
        pstOrder.close();
        pstPay.close();
        pstSales.close();
        con.close();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage(), "Transaction Failed", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_recordpanelMouseClicked

    private void resetpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetpanelMouseClicked
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear the cart?", 
            "Confirm Reset", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            clearCart();
            JOptionPane.showMessageDialog(this, "Cart cleared successfully!");
        }
    }//GEN-LAST:event_resetpanelMouseClicked

  
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
    private javax.swing.JPanel barspanel;
    private javax.swing.JPanel cakespanel;
    private javax.swing.JPanel cookiespanel;
    private javax.swing.JPanel cupcakespanel;
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
    private javax.swing.JLabel lblbars;
    private javax.swing.JLabel lblcakes;
    private javax.swing.JLabel lblcash;
    private javax.swing.JLabel lblchange;
    private javax.swing.JLabel lblcookies;
    private javax.swing.JLabel lblcupcakes;
    private javax.swing.JLabel lblloaves;
    private javax.swing.JLabel lbllogout;
    private javax.swing.JLabel lblothers;
    private javax.swing.JLabel lblpies;
    private javax.swing.JLabel lblreset;
    private javax.swing.JLabel lblrolls;
    private javax.swing.JLabel lbltarts;
    private javax.swing.JLabel lbltotal;
    private javax.swing.JLabel lblttl;
    private javax.swing.JPanel loavespanel;
    private javax.swing.JPanel otherpanel;
    private javax.swing.JPanel piespanel;
    private javax.swing.JPanel recordpanel;
    private javax.swing.JPanel resetpanel;
    private javax.swing.JPanel rollspanel;
    private javax.swing.JPanel tartspanel;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
