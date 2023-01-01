package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Document;
import org.example.Models.HibernateUtil;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class UpdateDocumentGUI extends JFrame {
    private PatientRecordGUI patientRecordGUI;
    private Document document;
    private JPanel contentPane;
    private JTextArea textAreaDocumentDescription;
    private JButton btnUpdateDocument;

    public UpdateDocumentGUI(PatientRecordGUI patientRecordGUI, Document document) {
        this.patientRecordGUI = patientRecordGUI;
        this.document = document;
        //
        setResizable(false);
        setTitle("Update Document");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Description");

        textAreaDocumentDescription = new JTextArea(document.getDescription());
        JScrollPane scrollPaneDocumentDescription = new JScrollPane(textAreaDocumentDescription);

        btnUpdateDocument = new JButton("Update");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPaneDocumentDescription, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblNewLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                                                .addComponent(btnUpdateDocument)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(btnUpdateDocument))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(scrollPaneDocumentDescription, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        //
        setLocationRelativeTo(null);
        setVisible(true);
        //
        initActionsListener();
    }

    private void initActionsListener() {
        btnUpdateDocument.addActionListener(e -> {
            if (document != null) {
                if (textAreaDocumentDescription.getText().length() > 1000) {
                    JOptionPane.showMessageDialog(this, "Description should not exceed 1000 character!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
                entityManager.getTransaction().begin();
                document.setDescription(textAreaDocumentDescription.getText());
                entityManager.merge(document);
                entityManager.getTransaction().commit();
                entityManager.close();
                JOptionPane.showMessageDialog(this, "Document updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                patientRecordGUI.fillTableDocuments();
            }
        });
    }
}
