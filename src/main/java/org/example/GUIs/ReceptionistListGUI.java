package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Receptionist;
import org.example.Models.EntityManagerInstance;
import org.example.Models.ReceptionistTableModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReceptionistListGUI extends JDialog {
    private JPanel contentPane;
    private JTable tableReceptionists;
    private JScrollPane scrollPaneReceptionists;
    private JLabel lblReceptioniststList;
    ReceptionistTableModel receptionistTableModel;

    public ReceptionistListGUI(DashboardGUI dashboardGUI) {
        super(dashboardGUI, "Receptionists Management", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(52, 50, 598, 340);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JButton btnADDReceptionistToList = new JButton(" ADD Receptionist");
        btnADDReceptionistToList.setFont(new Font("Tahoma", Font.BOLD, 12));

        lblReceptioniststList = new JLabel("Receptionists List");
        lblReceptioniststList.setFont(new Font("Tahoma", Font.BOLD, 15));
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        receptionistTableModel = new ReceptionistTableModel(entityManager
                .createQuery("select r from Receptionist r", Receptionist.class)
                .getResultList());
        tableReceptionists = new JTable(receptionistTableModel);
        scrollPaneReceptionists = new JScrollPane(tableReceptionists);

        JButton btnEditReceptionist = new JButton(" Edit Receptionist");
        btnEditReceptionist.setFont(new Font("Tahoma", Font.BOLD, 12));
        JButton btnActivate = new JButton("Activation");
        btnActivate.setFont(new Font("Tahoma", Font.BOLD, 12));

        JButton btnResetPassword = new JButton("Reset Password");
        btnResetPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(22)
                                                .addComponent(lblReceptioniststList, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                                .addGap(90)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                                        .addComponent(btnActivate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnEditReceptionist, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(btnADDReceptionistToList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnResetPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(scrollPaneReceptionists, GroupLayout.PREFERRED_SIZE, 552, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(btnEditReceptionist, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnADDReceptionistToList, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(btnActivate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnResetPassword, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                                .addGap(7))
                                        .addComponent(lblReceptioniststList, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPaneReceptionists, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
        );
        contentPane.setLayout(gl_contentPane);
        //
        setLocationRelativeTo(dashboardGUI);
        setResizable(false);
        //
        btnADDReceptionistToList.addActionListener(e -> {
            new AddReceptionistGUI(this);
        });
        // Edit button
        btnEditReceptionist.addActionListener(e -> {
            if (tableReceptionists.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "please select Receptionist to edit", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new EditReceptionistGUI(receptionistTableModel.getReceptionist(tableReceptionists.getSelectedRow()), this);
            tableReceptionists.clearSelection();
        });
        // Activation btn change
        tableReceptionists.getSelectionModel().addListSelectionListener(e -> {
            if (tableReceptionists.getSelectedRow() < 0) {
                btnActivate.setText("Activation");
                return;
            }
            btnActivate.setText(receptionistTableModel.getReceptionist(tableReceptionists.getSelectedRow()).isActive() ? "Deactivate" : "Activate");
        });
        // Activation button
        btnActivate.addActionListener(e -> {
            if (tableReceptionists.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "please select receptionist to edit", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Receptionist receptionist = receptionistTableModel.getReceptionist(tableReceptionists.getSelectedRow());
            entityManager.getTransaction().begin();
            receptionist.setActive(!receptionist.isActive());
            entityManager.merge(receptionist);
            entityManager.getTransaction().commit();
            JOptionPane.showMessageDialog(this
                    , receptionist.isActive() ? "Receptionist ACTIVATED successfully" : "receptionist DEACTIVATED"
                    , "Done"
                    , JOptionPane.INFORMATION_MESSAGE);
            tableReceptionists.clearSelection();
            fillReceptionistTable();
        });
        // Reset Password button
        btnResetPassword.addActionListener(e -> {
            if (tableReceptionists.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "please select receptionist to Reset Password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Receptionist receptionist = receptionistTableModel.getReceptionist(tableReceptionists.getSelectedRow());
            entityManager.getTransaction().begin();
            receptionist.setPassword("123456");
            entityManager.merge(receptionist);
            entityManager.getTransaction().commit();
            JOptionPane.showMessageDialog(this, "Password reset successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
            tableReceptionists.clearSelection();
            fillReceptionistTable();
        });
        //
        setVisible(true);
    }

    // Fill patient table
    public void fillReceptionistTable() {
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        receptionistTableModel.setReceptionists(entityManager
                .createQuery("select r from Receptionist r", Receptionist.class)
                .getResultList()
        );
        entityManager.close();
        receptionistTableModel.fireTableDataChanged();
    }
}
