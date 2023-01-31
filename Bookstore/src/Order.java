import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Order(JTable Books) {
        super("Order");
        this.setContentPane(OrderPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 600);

        DefaultTableModel model = (DefaultTableModel) Books.getModel();
        Make_Order_Table.setModel(model);
        String[] Column_Names = {"Ordered"};
        DefaultTableModel Orders = new DefaultTableModel(Column_Names,0);
        Order_Table.setModel(Orders);




        Search_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) Books.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
                Make_Order_Table.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(Search_Field.getText().trim()));
            }
        });

        Button_Order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = Make_Order_Table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) Order_Table.getModel();
                model.addRow(new Object[] { selectedItem });
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
                    Orders.removeRow(selectedItem);
                }
            }
        });
        Return_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main_Menu menu = new Main_Menu();
                menu.setVisible(true);
                dispose();

            }
        });
    }
}