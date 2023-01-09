package org.example.GUIs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.Entities.Doctor;
import org.example.Entities.Person;
import org.example.Entities.Receptionist;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import java.util.Objects;

public class AuthGUI extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldNIC;
    private JPasswordField passwordField;
    private JButton btnLogin;

    /**
     * Create the frame.
     */
    public AuthGUI() {
        setTitle("Auth");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 250, 240);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("NIC");

        JLabel lblNewLabel_1 = new JLabel("Password");

        textFieldNIC = new JTextField();
        textFieldNIC.setColumns(10);

        passwordField = new JPasswordField();

        btnLogin = new JButton("Login");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(36)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_1)
                                                        .addComponent(lblNewLabel))
                                                .addGap(18)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(passwordField)
                                                        .addComponent(textFieldNIC, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(79)
                                                .addComponent(btnLogin)))
                                .addContainerGap(38, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(32)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(textFieldNIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(32)
                                .addComponent(btnLogin)
                                .addContainerGap(46, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
        //
        setLocationRelativeTo(null);
        setVisible(true);
        //
        initActionsListener();
    }

    public void initActionsListener() {
        btnLogin.addActionListener(e -> {
            if (textFieldNIC.getText().length() == 0 || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //
            EntityManager entityManager = EntityManagerInstance.getNewInstance();
            // Person should always be a doctor or a receptionist
            Person person;
            String password = new String(passwordField.getPassword());
            // Querying the doctor or the receptionist based on NIC
            try {
                person = entityManager
                        .createQuery("select p from Person p where p.nic = ?1 and p.type in ('Doctor', 'Receptionist')", Doctor.class)
                        .setParameter(1, textFieldNIC.getText())
                        .getSingleResult();
            } catch (NoResultException doctorException) {
                JOptionPane.showMessageDialog(this, "Verify your NIC!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Verifying password
            if ((person instanceof Receptionist && !Objects.equals(((Receptionist) person).getPassword(), password))
                    ||
                (person instanceof Doctor && !Objects.equals(((Doctor) person).getPassword(), password))
            ) {
                JOptionPane.showMessageDialog(this, "Verify your password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Verifying if receptionist account is active
            if (person instanceof Receptionist && !((Receptionist) person).isActive()) {
                JOptionPane.showMessageDialog(this, "You account is deactivated contact administration!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Redirect to dashboard
        });
    }
}
