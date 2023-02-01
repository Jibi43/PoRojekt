import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Clients extends JFrame{
    private JPanel Client_Panel;
    private JTabbedPane tabbedPane1;
    private JPanel Client_List;
    private JPanel Client_Details;
    private JTable Client_Table;
    private JTextField Client_Username;
    private JTextField Name_Field;
    private JTextField Surname_Field;
    private JTextField City_Field;
    private JButton Button_Mod;
    private JTextField Password_Field;
    private JButton Button_Return;

    public Clients() {
        super("Browse");
        this.setContentPane(Client_Panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500,600);

        String[] Column_Names = {"Username","Name","Surname","City"};
        DefaultTableModel Use = new DefaultTableModel(Column_Names,0);
        Client_Table.setModel(Use);
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM Clients";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Object[] user = {resultSet.getString("Username"), resultSet.getString("Name"), resultSet.getString("Surname"), resultSet.getString("City")};
                Use.addRow(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Button_Mod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Login.per == 1 || Login.per == Integer.parseInt(Client_Username.getText())) {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");
                        Statement statement = connection.createStatement();
                        String sql = "UPDATE Clients SET Name = '" + Name_Field.getText() + "', Surname = '" + Surname_Field.getText() + "', City = '" + City_Field.getText() + "', Password = '" + Password_Field.getText() + "' WHERE username = " + Client_Username.getText();
                        statement.executeUpdate(sql);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You lack administrative permission");
                }
            }
        });

        Client_Table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = Client_Table.getSelectedRow();
                if (selectedRow != -1) {
                    Client_Username.setText(String.valueOf(Client_Table.getValueAt(selectedRow, 0)));
                    Name_Field.setText(String.valueOf(Client_Table.getValueAt(selectedRow, 1)));
                    Surname_Field.setText(String.valueOf(Client_Table.getValueAt(selectedRow, 2)));
                    City_Field.setText(String.valueOf(Client_Table.getValueAt(selectedRow, 3)));
                }
            }
        });


        Button_Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main_Menu menu = new Main_Menu();
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);
                dispose();

            }
        });
    }
}
