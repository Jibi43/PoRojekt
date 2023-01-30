import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel Login_Panel;
    private JTextField Username_Field;
    private JPasswordField Password_Field;
    private JButton Button_Login;

    public static void main(String[] args) {
        Login login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        boolean permission = false;
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
                else if(log.equals("Admin") && pass.equals("Password")) {
                    Main_Menu menu = new Main_Menu();
                    menu.setVisible(true);
                    dispose();

                } else {
                    Main_Menu menu = new Main_Menu();
                    menu.setVisible(true);
                    dispose();
                }

            }
        });
    }
}
