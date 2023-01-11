package org.example.GUIs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.Entities.Document;
import org.example.Entities.Patient;
import org.example.Entities.Record;
import org.example.Models.DocumentTableModel;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.Objects;

public class PatientRecordGUI extends JDialog {
    private JPanel contentPane;
    private JTextField textFieldNIC;
    private JTable tableDocuments;
    private DocumentTableModel documentTableModel;
    private JButton btnAddDocument, btnSearchRecord, btnUpdateDescription;
    private JTextArea textAreaRecordDescription;
    private JLabel lblFullName;
    private Patient patient;
    private Record record;

    public PatientRecordGUI(DashboardGUI dashboardGUI) {
        super(dashboardGUI, "Patient Record", true);
        setIconImage(new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo.png")).getPath()
        ).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 520);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        textFieldNIC = new JTextField();
        textFieldNIC.setColumns(10);

        JLabel lblNewLabel = new JLabel("NIC");

        btnSearchRecord = new JButton("Search");

        textAreaRecordDescription = new JTextArea();
        textAreaRecordDescription.setEnabled(false);
        JScrollPane scrollPaneRecordDescription = new JScrollPane(textAreaRecordDescription);

        JLabel lblNewLabel_1 = new JLabel("Documents");

        documentTableModel = new DocumentTableModel(new ArrayList<>());
        tableDocuments = new JTable(documentTableModel);
        tableDocuments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneTableDocuments = new JScrollPane(tableDocuments);

        btnAddDocument = new JButton("New Document");
        btnAddDocument.setEnabled(false);

        btnUpdateDescription = new JButton("Update");
        btnUpdateDescription.setEnabled(false);

        JLabel lblNewLabel_2 = new JLabel("Description");

        lblFullName = new JLabel("Full Name");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(scrollPaneTableDocuments, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                        .addComponent(scrollPaneRecordDescription, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                                .addComponent(lblNewLabel_1)
                                                .addPreferredGap(ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
                                                .addComponent(btnAddDocument))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_2)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(lblNewLabel)
                                                                .addGap(18)
                                                                .addComponent(textFieldNIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(lblFullName)))
                                                .addPreferredGap(ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(btnSearchRecord, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnUpdateDescription, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(textFieldNIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearchRecord)
                                        .addComponent(lblFullName))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(btnUpdateDescription))
                                .addGap(18)
                                .addComponent(scrollPaneRecordDescription, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(btnAddDocument))
                                .addGap(18)
                                .addComponent(scrollPaneTableDocuments, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        //
        initActionsListener();
        //
        setLocationRelativeTo(dashboardGUI);
        setVisible(true);
    }

    private void initActionsListener() {
        btnSearchRecord.addActionListener(e -> {
            lblFullName.setText("Full Name");
            btnUpdateDescription.setEnabled(false);
            textAreaRecordDescription.setText("");
            textAreaRecordDescription.setEnabled(false);
            btnAddDocument.setEnabled(false);
            documentTableModel.setDocuments(new ArrayList<>());
            documentTableModel.fireTableDataChanged();
            //
            if (textFieldNIC.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "NIC is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            EntityManager entityManager = EntityManagerInstance.getNewInstance();
            try {
                patient = entityManager
                        .createQuery("select p from Patient p where p.nic = ?1", Patient.class)
                        .setParameter(1, textFieldNIC.getText())
                        .getSingleResult();
                lblFullName.setText(patient.getFullName());
            } catch (NoResultException exception) {
                JOptionPane.showMessageDialog(this, "Patient not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                record = entityManager
                        .createQuery("select r from Record r where r.patient.id = ?1", Record.class)
                        .setParameter(1, patient.getId())
                        .getSingleResult();
                //
                fillTableDocuments();
            } catch (NoResultException exception) {
                entityManager.getTransaction().begin();
                record = new Record("", patient);
                entityManager.persist(record);
                entityManager.getTransaction().commit();
            }
            textAreaRecordDescription.setEnabled(true);
            textAreaRecordDescription.setText(record.getDescription());
            btnUpdateDescription.setEnabled(true);
            btnAddDocument.setEnabled(true);
            entityManager.close();
        });
        btnUpdateDescription.addActionListener(e -> {
            if (record != null) {
                if (textAreaRecordDescription.getText().length() > 2500) {
                    JOptionPane.showMessageDialog(this, "Description should not exceed 2500 character!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EntityManager entityManager = EntityManagerInstance.getNewInstance();
                entityManager.getTransaction().begin();
                record.setDescription(textAreaRecordDescription.getText());
                entityManager.merge(record);
                entityManager.getTransaction().commit();
                entityManager.close();
                JOptionPane.showMessageDialog(this, "Description updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnAddDocument.addActionListener(e -> {
            new AddDocumentGUI(this, record);
        });
        tableDocuments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                return;
            }
            if (tableDocuments.getSelectedRow() >= 0) {
                new UpdateDocumentGUI(this, documentTableModel.getDocument(tableDocuments.getSelectedRow()), patient);
                tableDocuments.clearSelection();
            }
        });
    }

    public void fillTableDocuments() {
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        documentTableModel.setDocuments(entityManager
                .createQuery("select d from Document d where d.record.id = :rid order by dateTime desc", Document.class)
                .setParameter("rid", record.getId())
                .getResultList()
        );
        entityManager.close();
        documentTableModel.fireTableDataChanged();
    }
}
