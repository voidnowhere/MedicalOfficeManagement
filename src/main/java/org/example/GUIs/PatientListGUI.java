package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Patient;
import org.example.Models.EntityManagerInstance;
import org.example.Models.PatientManagementModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PatientListGUI extends JFrame {
    private JPanel contentPane;
    private JTable tablePatients;
    private JScrollPane scrollPanePatients;
    private JLabel lblPatientList;
   PatientListGUI patientListGUI;
    PatientManagementModel patientManagementModel;

    public PatientListGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(52, 50, 531, 337);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JButton btnADDPatientToList = new JButton(" ADD Patient");
        btnADDPatientToList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnADDPatientToList.setFont(new Font("Tahoma", Font.BOLD, 12));
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        lblPatientList = new JLabel("Patient List");
        lblPatientList.setFont(new Font("Tahoma", Font.BOLD, 15));
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        patientManagementModel = new PatientManagementModel(entityManager
                .createQuery("select p from Patient p", Patient.class)
                .getResultList());
        entityManager.close();
        tablePatients = new JTable(patientManagementModel);
        scrollPanePatients = new JScrollPane(tablePatients);

        JButton btnEditPatient = new JButton(" Edit Patient");
        btnEditPatient.setFont(new Font("Tahoma", Font.BOLD, 12));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(scrollPanePatients, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblPatientList, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                                                .addComponent(btnEditPatient)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnADDPatientToList, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                                .addGap(8))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblPatientList, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(btnADDPatientToList, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnEditPatient, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPanePatients, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
        setLocationRelativeTo(null);
        setTitle("Patients list");
        setResizable(false);
        setVisible(true);
        // ADD button
        btnADDPatientToList.addActionListener(e -> {
            new PatientManagementGUI(this);
        });
        // Edit button
        btnEditPatient.addActionListener(e -> {
            if (tablePatients.getSelectedRow() < 0){
                JOptionPane.showMessageDialog(this , "please select patient to edit" );
                return;
            }
            new EditPtientGUI(patientManagementModel.getPatient(tablePatients.getSelectedRow()), this);
            tablePatients.clearSelection();
        });
    }
    // Fill patient table
    public void fillPatientsTable(){

        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        patientManagementModel.setPatients(entityManager
                .createQuery("select p from Patient p", Patient.class)
                .getResultList()
        );

        entityManager.close();
        patientManagementModel.fireTableDataChanged();
    }
}
