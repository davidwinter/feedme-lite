package feedmelite.helpers;

import java.util.*;
import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel
{
    public NonEditableTableModel(Vector data, Vector columnNames)
    {
        super(data, columnNames);
    }
    
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
}