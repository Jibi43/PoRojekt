import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    private JPanel Login_Panel;
    private JTextField Username_Field;
    private JPasswordField Password_Field;
    private JButton Button_Login;

    public static int per = 0;

    public static void main(String[] args) {
        Login login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);

    }
    public Login(){
        super("Login");
        this.setContentPane(Login_Panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(300, 350);


        Button_Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String log = Username_Field.getText();
                char[] pas = Password_Field.getPassword();
                String pass = new String(pas);
                if(log.isBlank() || pas.length==0){
                    JOptionPane.showMessageDialog(Login_Panel,"You need to enter more information");
                }
                else {
                    try {
                        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","root");
                        PreparedStatement checkStatement = con.prepareStatement("SELECT * FROM clients WHERE Username = ?");
                        checkStatement.setString(1, log);
                        ResultSet result = checkStatement.executeQuery();
                        if (result.next()) {
                            if (result.getString("Password").equals(pass)) {
                                int user_id = result.getInt("idclients");
                                per = user_id;
                                Main_Menu menu = new Main_Menu();
                                menu.setLocationRelativeTo(null);
                                menu.setVisible(true);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(Login_Panel,"Incorrect password. Please try again.");
                            }
                        } else {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO clients(Username, Password) values (?,?)");
                            statement.setString(1, log);
                            statement.setString(2, pass);
                            int row = statement.executeUpdate();
                            if (row > 0) {
                                PreparedStatement selectStatement = con.prepareStatement("SELECT idclients FROM clients WHERE Username = ?");
                                selectStatement.setString(1, log);
                                ResultSet selectResult = selectStatement.executeQuery();
                                if (selectResult.next()) {
                                    int user_id = selectResult.getInt("idclients");
                                    per = user_id;
                                    Main_Menu menu = new Main_Menu();
                                    menu.setLocationRelativeTo(null);
                                    menu.setVisible(true);
                                    dispose();
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


    }
}
