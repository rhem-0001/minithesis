package minithesis;
import java.sql.Connection;
import java.sql.*;
import javax.swing.JOptionPane;
public class sqlconnector {
    private static final String URL = "jdbc:mysql://localhost:3306/yoyis";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection(){
        Connection connect = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(URL, USER, PASSWORD);
            
        }catch(ClassNotFoundException | SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
        return connect;
    }
}
