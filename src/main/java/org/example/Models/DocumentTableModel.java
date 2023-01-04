package org.example.Models;

import org.example.Entities.Document;
import org.example.Entities.MedicalCertificate;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DocumentTableModel extends AbstractTableModel {
    private List<Document> documents;
    private String[] columns;

    public DocumentTableModel(List<Document> documents) {
        columns = new String[]{"When", "Type", "Description"};
        this.documents = documents;
    }

    @Override
    public int getRowCount() {
        return documents.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> documents.get(rowIndex).getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            case 1 -> (documents.get(rowIndex) instanceof MedicalCertificate) ? "Medical Certificate" : "Prescription";
            case 2 -> documents.get(rowIndex).getDescription();
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

    public Document getDocument(int rowIndex) {
        return documents.get(rowIndex);
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
