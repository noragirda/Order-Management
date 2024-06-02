package presentation;

import dataAccess.DataAccess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
/**
 * This class is responsible for displaying the data in a table format.
 * @param <T> The type of the data to be displayed.
 */
public class TableView<T> extends JFrame {

    /**
     * Creates a new instance of the TableView class.
     * @param clazz The class of the data to be displayed.
     */
    public TableView(Class<T> clazz) {
        setTitle(clazz.getSimpleName() + " Data");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        DataAccess<T> dataAccess = new DataAccess<T>() {};

        List<T> dataList = dataAccess.findAll(clazz);
        JTable table = new JTable(createTableModel(dataList));

        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
    }
    /**
     * Creates a table model for the data to be displayed.
     * @param dataList The list of data to be displayed.
     * @return The table model for the data to be displayed.
     */
    private DefaultTableModel createTableModel(List<T> dataList) {
        if (dataList.isEmpty()) {
            return new DefaultTableModel();
        }

        String[] columnNames = getColumnNames(dataList.get(0).getClass());
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (T obj : dataList) {
            model.addRow(getRowData(obj));
        }

        return model;
    }
    /**
     * Gets the column names of the table.
     * @param clazz The class of the data to be displayed.
     * @return The column names of the table.
     */
    private String[] getColumnNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        return columnNames;
    }
    /**
     * Gets the row data of the table.
     * @param obj The object to be displayed in the table.
     * @return The row data of the table.
     */
    private Object[] getRowData(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Object[] rowData = new Object[fields.length];
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                rowData[i] = fields[i].get(obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return rowData;
    }
}
