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
    private JButton Button_Refresh;

    public Order() {
        super("Order");
        this.setContentPane(OrderPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 600);

        String[] Column_Names = {"Title","Author","Genre","Year"};
        DefaultTableModel order = new DefaultTableModel(Column_Names,0);
        Make_Order_Table.setModel(order);
        String[] Column_Names2 = {"Title","Username"};
        DefaultTableModel order2 = new DefaultTableModel(Column_Names2,0);
        Order_Table.setModel(order2);

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
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM Orders";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Object[] ord = {resultSet.getString("book_title"), resultSet.getString("client_username")};
                order2.addRow(ord);
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
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");
                        Statement stmt = conn.createStatement();
                        String Title = Make_Order_Table.getValueAt(selectedRow, 0).toString();
                        String clientUsername = "";
                        ResultSet rs2 = stmt.executeQuery("SELECT * FROM clients WHERE idClients = '" + Login.per + "'");
                        if (rs2.next()) {
                            clientUsername = rs2.getString("Username");
                        }
                        String sql = "INSERT INTO orders (book_title, client_username) VALUES ('" + Title + "', '" + clientUsername + "')";
                        stmt.executeUpdate(sql);
                        conn.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        Button_Remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Login.per == 0) {
                    JOptionPane.showMessageDialog(OrderPanel, "You lack administrative permission");
                } else {
                    int selectedRow = Order_Table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(OrderPanel, "Please select a row to remove");
                    } else {
                        try {
                            String title = (String) Order_Table.getValueAt(selectedRow, 0);
                            String username = (String) Order_Table.getValueAt(selectedRow, 3);
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Orders WHERE book_title='" + title + "AND client_username='" + username + "'");
                            order2.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(OrderPanel, "Book removed successfully");
                            con.close();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(OrderPanel, "Error while removing book");
                            ex.printStackTrace();
                        }
                    }
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
        Button_Refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order2.setRowCount(0);
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");

                    Statement statement = connection.createStatement();

                    String sql = "SELECT * FROM orders";
                    ResultSet resultSet = statement.executeQuery(sql);

                    while (resultSet.next()){
                        Object[] ord = {resultSet.getString("book_title"), resultSet.getString("client_username")};
                        order2.addRow(ord);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}