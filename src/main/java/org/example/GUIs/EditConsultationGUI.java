package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Consultation;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class EditConsultationGUI extends JDialog {
    private Consultation consultation;
    private JPanel contentPane;
    private JCheckBox chckbxNextTime;
    private JTextArea textAreaConsultationDescription;
    private JButton btnUpdateConsultation;
    private AppointmentsManagementGUI appointmentsManagementGUI;

    public EditConsultationGUI(DashboardGUI dashboardGUI, Consultation consultation) {
        super(dashboardGUI, "Edit Consultation", true);
        this.consultation = consultation;
        initComponents(dashboardGUI);
    }

    public EditConsultationGUI(AppointmentsManagementGUI appointmentsManagementGUI, Consultation consultation) {
        super(appointmentsManagementGUI, "Edit Consultation", true);
        this.appointmentsManagementGUI = appointmentsManagementGUI;
        this.consultation = consultation;
        //
        initComponents(null);
    }

    private void initComponents(DashboardGUI dashboardGUI) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 500, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        chckbxNextTime = new JCheckBox("Follow Up");
        chckbxNextTime.setSelected(consultation.isNextTime());

        JLabel lblNewLabel = new JLabel("Record Description");
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        consultation.getPatient().setRecord(entityManager.merge(consultation.getPatient().getRecord()));
        entityManager.refresh(consultation.getPatient().getRecord());
        entityManager.close();
        JTextArea textAreaRecordDescription = new JTextArea(consultation.getPatient().getRecord().getDescription());
        textAreaRecordDescription.setEditable(false);
        JScrollPane scrollPaneRecordDescription = new JScrollPane(textAreaRecordDescription);

        JLabel lblNewLabel_1 = new JLabel("Consultation Description");

        btnUpdateConsultation = new JButton("Update");

        textAreaConsultationDescription = new JTextArea(consultation.getDescription());
        JScrollPane scrollPaneConsultationDescription = new JScrollPane(textAreaConsultationDescription);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(scrollPaneConsultationDescription, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                                        .addComponent(scrollPaneRecordDescription, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                                        .addComponent(lblNewLabel, Alignment.LEADING)
                                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                                .addComponent(lblNewLabel_1)
                                                .addPreferredGap(ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                                                .addComponent(chckbxNextTime)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnUpdateConsultation)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblNewLabel)
                                .addGap(18)
                                .addComponent(scrollPaneRecordDescription, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(chckbxNextTime)
                                        .addComponent(btnUpdateConsultation))
                                .addGap(18)
                                .addComponent(scrollPaneConsultationDescription, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        //
        initActionsListener();
        //
        if (dashboardGUI != null) {
            setLocationRelativeTo(dashboardGUI);
        } else if (appointmentsManagementGUI != null) {
            setLocationRelativeTo(appointmentsManagementGUI);
        }
        setVisible(true);
    }

    private void initActionsListener() {
        btnUpdateConsultation.addActionListener(e -> {
            EntityManager entityManager = EntityManagerInstance.getNewInstance();
            entityManager.getTransaction().begin();
            consultation.setNextTime(chckbxNextTime.isSelected());
            consultation.setDescription(textAreaConsultationDescription.getText());
            entityManager.merge(consultation);
            entityManager.getTransaction().commit();
            entityManager.close();
            JOptionPane.showMessageDialog(this, "Consultation updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (appointmentsManagementGUI != null) {
                appointmentsManagementGUI.fillTableConsultation();
            }
            dispose();
        });
    }
}
