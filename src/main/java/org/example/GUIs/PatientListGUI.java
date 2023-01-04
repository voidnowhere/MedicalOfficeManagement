package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Patient;
import org.example.Models.HibernateUtil;
import org.example.Models.PatientManagementModel;

import javax.swing.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class PatientListGUI extends JFrame {
    private JPanel contentPane;
    private JTable tablePatients;
    private JScrollPane scrollPanePatients;

    private JLabel lblPatientList;


private PatientManagementModel patientManagementModel;


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
        EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
        patientManagementModel = new PatientManagementModel(entityManager
                .createQuery("select p from Patient p", Patient.class)
                .getResultList());
        tablePatients = new JTable(patientManagementModel);
        scrollPanePatients = new JScrollPane(tablePatients);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblPatientList, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                                                .addComponent(btnADDPatientToList, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(scrollPanePatients, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                                .addGap(29))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblPatientList, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnADDPatientToList, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPanePatients, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        setLocationRelativeTo(null);
        setTitle("Patients list");
        setResizable(false);
        setVisible(true);
        btnADDPatientToList.addActionListener(e -> {
            new PatientManagementGUI(this);

        });

    }
    public void fillPatientsTable(){

        EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
        patientManagementModel.setPatients(entityManager
                .createQuery("select p from Patient p", Patient.class)
                .getResultList()
        );
        entityManager.close();
        patientManagementModel.fireTableDataChanged();

    }
}
