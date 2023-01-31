import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

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
    public static JTable More_Table;

    public Browse(){
        super("Browse");
        this.setContentPane(Browse_Panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500,600);

        String[] Column_Names = {"Title","Author","Genre","Year"};

        DefaultTableModel Shelf = new DefaultTableModel(Column_Names,0);
        Book_Table.setModel(Shelf);


        Object[] book1 = {"Ślepa wierzba i płacząca kobieta", "Haruki Murakami", "literatura piękna", 2008};
        Object[] book2 = {"Żywot Bazylego Fiwejskiego", "Leonid Andrejew", "literatura piękna", 1906};
        Object[] book3 = {"Pieśni Maldorora", "Comte de Lautréamont", "poemat", 1869};
        Object[] book4 = {"Inny świat", "Gustaw Herling-Grudziński", "biografia", 1951};
        Object[] book5 = {"Lalka", "Bolesław Prus", "powieść społeczno-obyczajowa", 1889};
        Object[] book6 = {"Bohater naszych czasów", "Michaił Lermontow", "powieść psychologiczna", 1840};
        Object[] book7 = {"Wilk stepowy", "Hermann Hesse", "powieść egzystencjalna", 1927};
        Object[] book8 = {"Nieznośna lekkość bytu", "Milan Kundera", "powieść filozoficzna", 1984};
        Object[] book9 = {"Pani Bovary", "Gustave Flaubert", "powieść realistyczno-psychologiczna", 1856};
        Object[] book10 = {"Ojciec Goriot", "Honoré de Balzac", "powieść obyczajowa", 1835};
        Object[] book11 = {"Jeśli zimową nocą podróżny", "Italo Calvino", "powieść postmodernistyczna", 1979};
        Object[] book12 = {"Czarnobylska modlitwa. Kronika przyszłości", "Swietłana Aleksijewicz", "reportaż", 2012};
        Object[] book13 = {"Głód", "Knut Hamsun", "powieść psychologiczna", 1890};
        Object[] book14 = {"Mały Bies", "Fiodor Sołogub", "powieść psychologiczna", 1905};
        Object[] book15 = {"Mleko i miód", "Rupi Kaur", "poezja", 2014};
        Object[] book16 = {"Prowadź swój pług przez kości umarłych", "Olga Tokarczuk", "powieść kryminalna", 2009};
        Object[] book17 = {"Solaris", "Stanisław Lem", "powieść fantastyczno-naukowa", 1961};
        Object[] book18 = {"Mag", "John Fowles", "powieść postmodernistyczna", 1965};
        Object[] book19 = {"Boska komedia", "Dante Alighieri", "poezja", 1472};
        Object[] book20 = {"Maska czerwonego moru", "Edgar Allan Poe", "powieść gotycka", 1842};
        Object[] book21 = {"Śnieg", "Orhan Pamuk", "powieść", 2002};
        Object[] book22 = {"Portret Doriana Graya", "Oscar Wilde", "powieść gotycka", 1890};
        Object[] book23 = {"Golem", "Gustav Meyrink", "powieść grozy", 1915};
        Object[] book24 = {"Kusiciel", "Hermann Broch", "powieść fikcyjna", 1953};
        Object[] book25 = {"Tajemnice zamku Udolpho", "Ann Radcliffe", "powieść gotycka", 1794};
        Object[] book26 = {"Puste niebo", "Radek Rak", "powieść fikcyjna", 2016};


        for (Object[] objects : Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10, book11, book12, book13, book14, book15, book16, book17, book18, book19, book20, book21, book22, book23, book24, book25, book26)) {
            Shelf.addRow(objects);
        }

        More_Table=Book_Table;


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
                    Object[] book = {title,author,genre,year};
                    Shelf.addRow(book);
                    Text_Title.setText("");
                    Text_Genre.setText("");
                    Text_Author.setText("");
                    Combo_Year.setSelectedIndex(0);
                }
            }
            }
        });


        Button_Remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Login.per==0) {
                    JOptionPane.showMessageDialog(Browse_Panel, "You lack administrative permission");
                }
                else {
                    int selectedItem = Book_Table.getSelectedRow();
                    Shelf.removeRow(selectedItem);
                }



            }
        });


        Button_Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main_Menu menu = new Main_Menu();
                menu.setVisible(true);
                dispose();
            }
        });
    }

}
