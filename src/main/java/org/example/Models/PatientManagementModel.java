package org.example.Models;

import jakarta.persistence.Column;
import org.example.Entities.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PatientManagementModel extends AbstractTableModel {
    private List<Patient> patients;
    private String[] columns;

    public PatientManagementModel(List<Patient> patients) {
        columns = new String[]{"First Name","Last Name","NIC","Address","Birthday","Phone Number","Minor"};
        this.patients = patients;
    }

    @Override
    public int getRowCount() {
        return patients.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> patients.get(rowIndex).getFirstName();
            case 1 -> patients.get(rowIndex).getLastName();
            case 2 -> patients.get(rowIndex).getNic();
            case 3 -> patients.get(rowIndex).getAddress();
            case 4 -> patients.get(rowIndex).getBirthday();
            case 5 -> patients.get(rowIndex).getPhoneNumber();
            case 6 -> patients.get(rowIndex).isMinor() ? "yes" : "no";
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column){
        return this.columns[column];
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}
