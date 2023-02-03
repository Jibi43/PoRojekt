import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Browse extends JFrame{
    private JPanel Browse_Panel;
    private JButton Button_Return;
    private JButton Button_Add;
    private JButton Button_Remove;
    public JTable Book_Table;
    private JTabbedPane tabbedPane1;
    private JTextField Text_Title;
    private JTextField Text_Genre;
    private JTextField Text_Author;
    private JComboBox Combo_Year;
    private JTextField Search_Field;
    private JButton Search_Button;
    public static JTable More_Table;

    public Browse(){
        super("Browse");
        this.setContentPane(Browse_Panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500,600);

        String[] Column_Names = {"Title","Author","Genre","Year"};
        DefaultTableModel Shelf = new DefaultTableModel(Column_Names,0);
        Book_Table.setModel(Shelf);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM Books";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Object[] book = {resultSet.getString("Title"), resultSet.getString("Author"), resultSet.getString("Genre"), resultSet.getInt("Year")};
                Shelf.addRow(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        Button_Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Login.per==0) {
                    JOptionPane.showMessageDialog(Browse_Panel, "You lack administrative permission");
                }
                else{
                    String title = Text_Title.getText();
                    String author = Text_Author.getText();
                    String genre = Text_Genre.getText();
                    String year = String.valueOf(Combo_Year.getSelectedIndex()+1900);
                    if(title.isBlank() || author.isBlank() || genre.isBlank()) {
                        JOptionPane.showMessageDialog(Browse_Panel,"You need to enter more information");
                    }
                    else {
                        try {
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");
                            PreparedStatement addBook = con.prepareStatement("INSERT INTO books (title, author, genre, year) VALUES (?, ?, ?, ?)");
                            addBook.setString(1, title);
                            addBook.setString(2, author);
                            addBook.setString(3, genre);
                            addBook.setInt(4, Integer.parseInt(year));
                            addBook.executeUpdate();
                            con.close();
                            Object[] book = {title,author,genre,year};
                            Shelf.addRow(book);
                            Text_Title.setText("");
                            Text_Genre.setText("");
                            Text_Author.setText("");
                            Combo_Year.setSelectedIndex(0);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        Button_Remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Login.per == 0) {
                    JOptionPane.showMessageDialog(Browse_Panel, "You lack administrative permission");
                } else {
                    int selectedRow = Book_Table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(Browse_Panel, "Please select a row to remove");
                    } else {
                        try {
                            String title = (String) Book_Table.getValueAt(selectedRow, 0);
                            String author = (String) Book_Table.getValueAt(selectedRow, 1);
                            String genre = (String) Book_Table.getValueAt(selectedRow, 2);
                            String year = (String) Book_Table.getValueAt(selectedRow, 3);
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate("DELETE FROM Books WHERE Title='" + title + "' AND Author='" + author + "' AND Genre='" + genre + "' AND Year='" + year + "'");
                            Shelf.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(Browse_Panel, "Book removed successfully");
                            con.close();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(Browse_Panel, "Error while removing book");
                            ex.printStackTrace();
                        }
                    }
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
        Search_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) Book_Table.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
                Book_Table.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + Search_Field.getText().trim()));
            }
        });
    }

}
