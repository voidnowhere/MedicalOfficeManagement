package org.example.Models;

import org.example.Entities.Consultation;
import org.example.Entities.Receptionist;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AppointmentsCalendarTableModel extends AbstractTableModel {
    private List<Consultation> consultations;
    private String[] columns;

    public AppointmentsCalendarTableModel(List<Consultation> consultations) {
        columns = new String[]{"Patient", "NIC", "Paid", "Price", "Time", "Receptionist"};
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
            case 0 -> consultations.get(rowIndex).getPatient().getFullName();
            case 1 -> consultations.get(rowIndex).getPatient().getNic();
            case 2 -> (consultations.get(rowIndex).isPaid()) ? "Yes" : "No";
            case 3 -> consultations.get(rowIndex).getPrice();
            case 4 -> consultations.get(rowIndex).getDateTime().toLocalTime().toString();
            case 5 -> (receptionist != null) ? receptionist.getFullName() : "Doctor";
            default -> null;
        };
    }

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
