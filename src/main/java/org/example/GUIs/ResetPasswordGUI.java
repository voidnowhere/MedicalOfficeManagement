package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Doctor;
import org.example.Entities.Person;
import org.example.Entities.Receptionist;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import java.util.Arrays;

public class ResetPasswordGUI extends JDialog {
    private JPanel contentPane;
    private JPasswordField passwordFieldNew;
    private JPasswordField passwordFieldPassword;
    private JPasswordField passwordFieldConfirmation;
    private JButton btnReset;
    private Person person;

    public ResetPasswordGUI(DashboardGUI dashboardGUI, Person person) {
        super(dashboardGUI, "Reset Password", true);
        this.person = person;
        //
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 240, 210);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Password");

        JLabel lblNewLabel_1 = new JLabel("New Password");

        JLabel lblNewLabel_2 = new JLabel("Confirmation");

        passwordFieldNew = new JPasswordField();

        passwordFieldPassword = new JPasswordField();

        passwordFieldConfirmation = new JPasswordField();

        btnReset = new JButton("Reset");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(btnReset)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_1)
                                                        .addComponent(lblNewLabel)
                                                        .addComponent(lblNewLabel_2))
                                                .addGap(18)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(passwordFieldConfirmation, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(passwordFieldPassword, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(passwordFieldNew, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(242, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(passwordFieldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(passwordFieldNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(passwordFieldConfirmation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(btnReset)
                                .addContainerGap(103, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
        //
        initActionsListener();
        //
        setLocationRelativeTo(dashboardGUI);
        setVisible(true);
    }

    private void initActionsListener() {
        btnReset.addActionListener(e -> {
            if (passwordFieldPassword.getPassword().length == 0
                    || passwordFieldNew.getPassword().length == 0
                    || passwordFieldConfirmation.getPassword().length == 0
            ) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Arrays.equals(passwordFieldNew.getPassword(), passwordFieldConfirmation.getPassword())) {
                JOptionPane.showMessageDialog(this, "Confirmation doesn't match new password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            EntityManager entityManager = EntityManagerInstance.getNewInstance();
            person = entityManager.merge(person);
            entityManager.refresh(person);
            if (person instanceof Doctor doctor) {
                if (!doctor.getPassword().equals(new String(passwordFieldPassword.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                entityManager.getTransaction().begin();
                doctor.setPassword(new String(passwordFieldConfirmation.getPassword()));
                entityManager.merge(doctor);
                entityManager.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (person instanceof Receptionist receptionist) {
                if (!receptionist.getPassword().equals(new String(passwordFieldPassword.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                entityManager.getTransaction().begin();
                receptionist.setPassword(new String(passwordFieldConfirmation.getPassword()));
                entityManager.merge(receptionist);
                entityManager.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            entityManager.close();
            dispose();
        });
    }
}
