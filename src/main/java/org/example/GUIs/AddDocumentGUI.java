package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.MedicalCertificate;
import org.example.Entities.Prescription;
import org.example.Entities.Record;
import org.example.Models.HibernateUtil;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class AddDocumentGUI extends JFrame {
    private PatientRecordGUI patientRecordGUI;
    private Record record;
    private JPanel contentPane;
    private JButton btnAddDocument;
    private JComboBox comboBoxDocumentType;
    private JTextArea textAreaDocumentDescription;

    public AddDocumentGUI(PatientRecordGUI patientRecordGUI, Record record) {
        this.patientRecordGUI = patientRecordGUI;
        this.record = record;
        //
        setTitle("Add Document");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Description");

        JLabel lblNewLabel_1 = new JLabel("Type");

        comboBoxDocumentType = new JComboBox();
        comboBoxDocumentType.setModel(new DefaultComboBoxModel(new String[]{"Prescription", "Medical Certificate"}));

        textAreaDocumentDescription = new JTextArea();
        JScrollPane scrollPaneDocumentDescription = new JScrollPane(textAreaDocumentDescription);

        btnAddDocument = new JButton("Add");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(scrollPaneDocumentDescription, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(lblNewLabel_1)
                                                                .addPreferredGap(ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                                                                .addComponent(comboBoxDocumentType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(lblNewLabel))
                                                .addContainerGap())
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addComponent(btnAddDocument)
                                                .addContainerGap())))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(comboBoxDocumentType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(lblNewLabel)
                                .addGap(18)
                                .addComponent(scrollPaneDocumentDescription, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                .addGap(18)
                                .addComponent(btnAddDocument)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        //
        this.setLocationRelativeTo(this.patientRecordGUI);
        this.setVisible(true);
        //
        initActionsListener();
    }

    private void initActionsListener() {
        btnAddDocument.addActionListener(e -> {
            if (textAreaDocumentDescription.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Description is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (textAreaDocumentDescription.getText().length() > 1000) {
                JOptionPane.showMessageDialog(this, "Description should not exceed 1000 character!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
            entityManager.getTransaction().begin();
            if (comboBoxDocumentType.getSelectedIndex() == 0) { // Prescription
                entityManager.persist(new Prescription(textAreaDocumentDescription.getText(), record));

            } else { // Medical Certificate
                entityManager.persist(new MedicalCertificate(textAreaDocumentDescription.getText(), record));
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            JOptionPane.showMessageDialog(this, "Document created.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            patientRecordGUI.fillTableDocuments();
        });
    }
}
