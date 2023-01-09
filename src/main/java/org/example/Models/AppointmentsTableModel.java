package org.example.Models;

import org.example.Entities.Consultation;
import org.example.Entities.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AppointmentsTableModel  extends AbstractTableModel {
    private List<Consultation> consultations;
    private String[] columns;

    public AppointmentsTableModel(List<Consultation> consultations) {
        columns = new String[]{"When", "Paid","Price"};
        this.consultations = consultations;
    }
    @Override
    public int getRowCount() {
        return consultations.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> consultations.get(rowIndex).getDateTime();
            case 1 -> consultations.get(rowIndex).isPaid();
            case 2 -> consultations.get(rowIndex).getPrice();
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public Consultation getConsultations(int rowIndex) {
        return consultations.get(rowIndex);
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
