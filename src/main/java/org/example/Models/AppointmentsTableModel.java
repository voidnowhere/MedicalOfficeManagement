package org.example.Models;

import org.example.Entities.Consultation;
import org.example.Entities.Receptionist;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentsTableModel extends AbstractTableModel {
    private List<Consultation> consultations;
    private String[] columns;

    public AppointmentsTableModel(List<Consultation> consultations) {
        columns = new String[]{"Receptionist", "When", "Paid", "Canceled", "Follow up", "Price"};
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
        Receptionist receptionist = consultations.get(rowIndex).getReceptionist();
        return switch (columnIndex) {
            case 0 -> (receptionist != null) ? receptionist.getFullName() : "Doctor";
            case 1 -> consultations.get(rowIndex).getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            case 2 -> (consultations.get(rowIndex).isPaid()) ? "Yes" : "No";
            case 3 -> (consultations.get(rowIndex).isCanceled()) ? "Yes" : "No";
            case 4 -> (consultations.get(rowIndex).isNextTime()) ? "Yes" : "No";
            case 5 -> consultations.get(rowIndex).getPrice();
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

    public Consultation getConsultation(int rowIndex) {
        return consultations.get(rowIndex);
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
