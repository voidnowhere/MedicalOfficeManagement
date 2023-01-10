package org.example.Models;

import org.example.Entities.Receptionist;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ReceptionistTableModel extends AbstractTableModel {
    private List<Receptionist> receptionists;
    private String[] columns;
    public ReceptionistTableModel(List<Receptionist> receptionists) {
        columns = new String[]{"First Name","Last Name","NIC","Address","Birthday","Phone Number","isActive"};
        this.receptionists = receptionists;
    }

    @Override
    public int getRowCount() {
        return receptionists.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> receptionists.get(rowIndex).getFirstName();
            case 1 -> receptionists.get(rowIndex).getLastName();
            case 2 -> receptionists.get(rowIndex).getNic();
            case 3 -> receptionists.get(rowIndex).getAddress();
            case 4 -> receptionists.get(rowIndex).getBirthday();
            case 5 -> receptionists.get(rowIndex).getPhoneNumber();
            case 6 -> receptionists.get(rowIndex).isActive() ? "yes" : "no";
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column){
        return this.columns[column];
    }

    public void setReceptionists(List<Receptionist> receptionists) {
        this.receptionists = receptionists;
    }

    public Receptionist getReceptionist(int rowIndex){
        return receptionists.get(rowIndex);
    }
}

