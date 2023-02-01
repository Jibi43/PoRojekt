import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Order extends JFrame {
    private JPanel OrderPanel;
    private JTabbedPane Panel_Order;
    private JButton Button_Order;
    private JTable Order_Table;
    private JButton Button_Remove;
    private JButton Return_Button;
    private JTextField Search_Field;
    private JTable Make_Order_Table;
    private JButton Search_Button;

    public Order() {
        super("Order");
        this.setContentPane(OrderPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 600);

        String[] Column_Names = {"Title","Author","Genre","Year"};
        DefaultTableModel order = new DefaultTableModel(Column_Names,0);
        Make_Order_Table.setModel(order);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM Books";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Object[] book = {resultSet.getString("Title"), resultSet.getString("Author"), resultSet.getString("Genre"), resultSet.getInt("Year")};
                order.addRow(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        Search_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) Make_Order_Table.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
                Make_Order_Table.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + Search_Field.getText().trim()));
            }
        });


        Button_Order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = Make_Order_Table.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) Order_Table.getModel();
                    model.addRow(new Object[] {
                            Make_Order_Table.getValueAt(selectedRow, 0),
                            Make_Order_Table.getValueAt(selectedRow, 1),
                            Make_Order_Table.getValueAt(selectedRow, 2),
                            Make_Order_Table.getValueAt(selectedRow, 3)
                    });
                }
            }
        });

        Button_Remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Login.per==0) {
                    JOptionPane.showMessageDialog(OrderPanel, "You lack administrative permission");
                }
                else {
                    int selectedItem = Order_Table.getSelectedRow();
                    order.removeRow(selectedItem);
                }
            }
        });
        Return_Button.addActionListener(new ActionListener() {
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