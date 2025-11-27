package ui.admin;

import model.User;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private final List<User> users;
    private final String[] columns = {"Username", "Role"};

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        User user = users.get(row);
        return switch (col) {
            case 0 -> user.getUsername();
            case 1 -> user.getRole();
            default -> null;
        };
    }
}
