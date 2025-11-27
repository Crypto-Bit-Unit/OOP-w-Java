package ui.admin;

import model.Book;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookTableModel extends AbstractTableModel {
    private final List<Book> books;
    private final String[] columns = {"ID", "Title", "Author"};

    public BookTableModel(List<Book> books) {
        this.books = books;
    }

    public int getRowCount() { return books.size(); }
    public int getColumnCount() { return columns.length; }
    public String getColumnName(int col) { return columns[col]; }

    public Object getValueAt(int row, int col) {
        Book book = books.get(row);
        return switch (col) {
            case 0 -> book.getId();
            case 1 -> book.getTitle();
            case 2 -> book.getAuthor();
            default -> null;
        };
    }
}
