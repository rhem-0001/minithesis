package minithesis;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class signup extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(signup.class.getName());

    public signup() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcategory = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jpassword = new javax.swing.JPasswordField();
        btnpassword = new javax.swing.JButton();
        jusername = new javax.swing.JTextField();
        cbxpassword = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jLabel1.setText("Username: ");

        jcategory.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jcategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jLabel2.setText("Password: ");

        jpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));

        btnpassword.setBackground(new java.awt.Color(255, 51, 51));
        btnpassword.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnpassword.setText("Sign Up");
        btnpassword.addActionListener(this::btnpasswordActionPerformed);

        jusername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jusername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jusername.addActionListener(this::jusernameActionPerformed);

        cbxpassword.setFont(new java.awt.Font("Perpetua", 1, 12)); // NOI18N
        cbxpassword.setText("show password");
        cbxpassword.addActionListener(this::cbxpasswordActionPerformed);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("WELCOME!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jcategory, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jpassword, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                        .addComponent(cbxpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jusername)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(398, 398, 398)
                .addComponent(btnpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(416, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(147, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jusername, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxpassword)
                .addGap(31, 31, 31)
                .addComponent(btnpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void btnpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpasswordActionPerformed
        String username = jusername.getText().trim(); 
        String userType = jcategory.getSelectedItem().toString();
        String password = new String(jpassword.getPassword());
    
        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields!", "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 6 characters long!", "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Hash the password
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            JOptionPane.showMessageDialog(this, 
                "Error processing password!", "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Connection conn = null;
        PreparedStatement checkPst = null;
        PreparedStatement insertPst = null;
        ResultSet rs = null;
        
        try {
            conn = sqlconnector.getConnection();
            
            // Check if username already exists
            String checkSql = "SELECT username FROM users WHERE username = ?";
            checkPst = conn.prepareStatement(checkSql);
            checkPst.setString(1, username);
            rs = checkPst.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, 
                    "This username is already taken!\nPlease choose another one.", 
                    "Username Exists", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Insert new user with hashed password
            String insertSql = "INSERT INTO users (username, userpassword, user_type) VALUES (?, ?, ?)";
            insertPst = conn.prepareStatement(insertSql);
            insertPst.setString(1, username);
            insertPst.setString(2, hashedPassword);  // Store hashed password
            insertPst.setString(3, userType);
            
            int rowsAffected = insertPst.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Account created successfully!\nUsername: " + username + "\nRole: " + userType, 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                jusername.setText("");
                jcategory.setSelectedIndex(0);
                jpassword.setText("");
                
                // Close signup and open login
                this.dispose();
                new loginform().setVisible(true);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Database Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            logger.severe("Signup error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources properly
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (checkPst != null) checkPst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (insertPst != null) insertPst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

    }//GEN-LAST:event_btnpasswordActionPerformed

    private void jusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jusernameActionPerformed

    private void cbxpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxpasswordActionPerformed
        // TODO add your handling code here:
         if(cbxpassword.isSelected()){
            jpassword.setEchoChar((char)0);
        }else{
            jpassword.setEchoChar('*');
        }
    }//GEN-LAST:event_cbxpasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnpassword;
    private javax.swing.JCheckBox cbxpassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> jcategory;
    private javax.swing.JPasswordField jpassword;
    private javax.swing.JTextField jusername;
    // End of variables declaration//GEN-END:variables
}
