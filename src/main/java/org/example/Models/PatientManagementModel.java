package org.example.Models;

import jakarta.persistence.Column;
import org.example.Entities.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PatientManagementModel extends AbstractTableModel {
    private List<Patient> patients;
    private String[] columns;

    public PatientManagementModel(List<Patient> patients) {
        columns = new String[]{"firstName","lastName","nic","address","birthday","phoneNumber"};
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
            case 0 -> patients.get(rowIndex).getId();
            case 1 -> patients.get(rowIndex).getFirstName();
            case 3 -> patients.get(rowIndex).getLastName();
            case 4 -> patients.get(rowIndex).getNic();
            case 5 -> patients.get(rowIndex).getAddress();
            case 6 -> patients.get(rowIndex).getBirthday();
            case 7 -> patients.get(rowIndex).getPhoneNumber();
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
