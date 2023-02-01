import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Menu extends JFrame {
    private JPanel Panel_Main;
    private JButton Button_Browse;
    private JButton Button_Order;
    private JButton Button_Logout;
    private JButton Button_Exit;
    private JButton Button_Client;


    public Main_Menu() {
        super("Main menu");
        this.setContentPane(Panel_Main);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 300);


        Button_Browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Browse browse = new Browse();
                browse.setLocationRelativeTo(null);
                browse.setVisible(true);
                dispose();
            }
        });


        Button_Order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Browse books = new Browse();
                Order order = new Order();
                order.setLocationRelativeTo(null);
                order.setVisible(true);
                dispose();


            }
        });

        Button_Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        Button_Logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setLocationRelativeTo(null);
                login.setVisible(true);
                dispose();

            }
        });
        Button_Client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clients client = new Clients();
                client.setLocationRelativeTo(null);
                client.setVisible(true);
                dispose();

            }
        });
    }

}
