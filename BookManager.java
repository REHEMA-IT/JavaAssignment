import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BookManager {
    private DefaultTableModel tableModel;
    private ArrayList<Book> books;

    public BookManager() {
        books = new ArrayList<>();
        String[] columnNames = { "Title", "Author", "Total Chapters", "Cover Image" };
        tableModel = new DefaultTableModel(columnNames, 0);
    }

    public JTable getTable() {
        JTable table = new JTable(tableModel);
        table.setRowHeight(120);
        return table;
    }

    public void addBook(Book book) {
        books.add(book);
        tableModel.addRow(new Object[] {
            book.getTitle(),
            book.getAuthor(),
            book.getTotalChapters(),
            new ImageIcon(book.getImagePath())
        });
    }

    public void clearAllBooks() {
        books.clear();
        tableModel.setRowCount(0);
    }
}