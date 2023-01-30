import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Order extends JFrame {
    private JPanel OrderPanel;
    private JTabbedPane Panel_Order;
    private JButton Button_Order;
    private JComboBox Book_Box;
    private JTable Order_Table;
    private JButton Button_Remove;
    private JButton Return_Button;

    public Order(JTable Books) {
        super("Order");
        this.setContentPane(OrderPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);

        DefaultTableModel BookTable = (DefaultTableModel) Books.getModel();
        int rowCount = BookTable.getRowCount();
        String[] Column_Names = {"Ordered"};
        DefaultTableModel Orders = new DefaultTableModel(Column_Names,0);
        Order_Table.setModel(Orders);

        for(int i=0;i<rowCount;i++) {
            String title = String.valueOf(BookTable.getValueAt(i,0));
            String author = String.valueOf(BookTable.getValueAt(i,1));
            String genre = String.valueOf(BookTable.getValueAt(i,2));
            String year = String.valueOf(BookTable.getValueAt(i,3));
            Object value = title+"-"+author+"-"+genre+"-"+year;
            Book_Box.addItem(value);
        }


        Button_Order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = Book_Box.getSelectedItem();
                DefaultTableModel model = (DefaultTableModel) Order_Table.getModel();
                model.addRow(new Object[] { selectedItem });
        }
        });
        Button_Remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedItem = Order_Table.getSelectedRow();
                Orders.removeRow(selectedItem);
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