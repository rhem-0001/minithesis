
package minithesis;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JFrame;


public class loginform extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(loginform.class.getName());

    public loginform() {
        initComponents(); 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbltitle = new javax.swing.JLabel();
        lblusername = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        lblpassword = new javax.swing.JLabel();
        txtpassword = new javax.swing.JPasswordField();
        cbxpassword = new javax.swing.JCheckBox();
        btnsignup = new javax.swing.JButton();
        btnlogin = new javax.swing.JButton();
        lblbg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Yoyi's Log-In Interface");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbltitle.setFont(new java.awt.Font("Perpetua Titling MT", 1, 50)); // NOI18N
        lbltitle.setForeground(new java.awt.Color(255, 255, 255));
        lbltitle.setText("Welcome");
        getContentPane().add(lbltitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 280, 80));

        lblusername.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        lblusername.setForeground(new java.awt.Color(255, 255, 255));
        lblusername.setText("Username: ");
        getContentPane().add(lblusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 150, -1));

        txtusername.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        txtusername.addActionListener(this::txtusernameActionPerformed);
        getContentPane().add(txtusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 210, 270, 30));

        lblpassword.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        lblpassword.setForeground(new java.awt.Color(255, 255, 255));
        lblpassword.setText("Password:");
        getContentPane().add(lblpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 150, -1));

        txtpassword.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        getContentPane().add(txtpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 270, 30));

        cbxpassword.setFont(new java.awt.Font("Perpetua", 0, 14)); // NOI18N
        cbxpassword.setText("Show Password");
        cbxpassword.addActionListener(this::cbxpasswordActionPerformed);
        getContentPane().add(cbxpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 310, 110, 20));

        btnsignup.setBackground(new java.awt.Color(153, 0, 0));
        btnsignup.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        btnsignup.setForeground(new java.awt.Color(255, 255, 255));
        btnsignup.setText("Sign Up");
        btnsignup.addActionListener(this::btnsignupActionPerformed);
        getContentPane().add(btnsignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 360, 120, 40));

        btnlogin.setBackground(new java.awt.Color(153, 0, 0));
        btnlogin.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        btnlogin.setForeground(new java.awt.Color(255, 255, 255));
        btnlogin.setText("Log In");
        btnlogin.addActionListener(this::btnloginActionPerformed);
        getContentPane().add(btnlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 110, 40));

        lblbg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/redbg.png"))); // NOI18N
        getContentPane().add(lblbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -170, 1050, 860));

        pack();
        setLocationRelativeTo(null);
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
    
    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        String username = txtusername.getText().trim();
        String password = new String(txtpassword.getPassword());
    
        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter username and password!", "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Hash the entered password
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            JOptionPane.showMessageDialog(this, 
                "Error processing password!", "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = sqlconnector.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND userpassword = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, username);
            pst.setString(2, hashedPassword);  // Compare hashed passwords
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                String userType = rs.getString("user_type");
                
                JOptionPane.showMessageDialog(this, 
                    "Login Successful!\nWelcome, " + username + "!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                logger.info("User logged in: " + username + " (Role: " + userType + ")");
                
                this.dispose(); // Close login form
                
                // Redirect based on user type
                if (userType.equalsIgnoreCase("Admin")) {
                    new Maintenance().setVisible(true);
                } else {
                    new usermenu().setVisible(true);
                }
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password!\nPlease check your credentials and try again.", 
                    "Login Failed", 
                    JOptionPane.ERROR_MESSAGE);
                
                logger.warning("Failed login attempt for username: " + username);
                
                // Clear the password field only
                txtpassword.setText("");
                txtusername.requestFocus();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            logger.severe("Login error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources properly
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (pst != null) pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

    }//GEN-LAST:event_btnloginActionPerformed

    private void cbxpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxpasswordActionPerformed
        // TODO add your handling code here:
        if(cbxpassword.isSelected()){
            txtpassword.setEchoChar((char)0);
        } else {
            txtpassword.setEchoChar('*');
        }
    }//GEN-LAST:event_cbxpasswordActionPerformed

    private void txtusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusernameActionPerformed

    private void btnsignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsignupActionPerformed
        // TODO add your handling code here:
        this.dispose(); // Close login form
        new signup().setVisible(true); // Open signup form
    }//GEN-LAST:event_btnsignupActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new loginform().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogin;
    private javax.swing.JButton btnsignup;
    private javax.swing.JCheckBox cbxpassword;
    private javax.swing.JLabel lblbg;
    private javax.swing.JLabel lblpassword;
    private javax.swing.JLabel lbltitle;
    private javax.swing.JLabel lblusername;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
