package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Person;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProfileInformationGUI extends JDialog {
    private Person person;
    private JPanel contentPane;
    private JTextField textFieldFirstName;
    private JTextField textFieldPhoneNumber;
    private JTextField textFieldLastName;
    private JTextField textFieldNIC;
    private JTextField textFieldAddress;
    private JTextField textFieldBirthday;
    private JButton btnUpdate;

    public ProfileInformationGUI(DashboardGUI dashboardGUI, Person person) {
        super(dashboardGUI, "Profile Information", true);
        this.person = person;
        //
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 265, 320);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("First Name");

        JLabel lblNewLabel_1 = new JLabel("Last Name");

        JLabel lblNewLabel_1_1 = new JLabel("NIC");

        JLabel lblNewLabel_1_2 = new JLabel("Address");

        JLabel lblNewLabel_1_3 = new JLabel("Birthday");

        JLabel lblNewLabel_1_4 = new JLabel("Phone Number");

        textFieldFirstName = new JTextField(person.getFirstName());
        textFieldFirstName.setColumns(10);

        textFieldPhoneNumber = new JTextField(person.getPhoneNumber());
        textFieldPhoneNumber.setColumns(10);

        textFieldLastName = new JTextField(person.getLastName());
        textFieldLastName.setColumns(10);

        textFieldNIC = new JTextField(person.getNic());
        textFieldNIC.setColumns(10);

        textFieldAddress = new JTextField(person.getAddress());
        textFieldAddress.setColumns(10);

        textFieldBirthday = new JTextField(person.getBirthday().toString());
        textFieldBirthday.setColumns(10);

        btnUpdate = new JButton("Update");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel_1_4)
                                        .addComponent(lblNewLabel)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(lblNewLabel_1_1)
                                        .addComponent(lblNewLabel_1_2)
                                        .addComponent(lblNewLabel_1_3))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnUpdate)
                                        .addComponent(textFieldBirthday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textFieldLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textFieldFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textFieldPhoneNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(textFieldAddress, Alignment.LEADING)
                                                .addComponent(textFieldNIC, Alignment.LEADING)))
                                .addContainerGap(189, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(textFieldFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(textFieldLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1_1)
                                        .addComponent(textFieldNIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1_2)
                                        .addComponent(textFieldAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1_3)
                                        .addComponent(textFieldBirthday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1_4)
                                        .addComponent(textFieldPhoneNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(btnUpdate)
                                .addContainerGap(39, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
        //
        initActionsListener();
        //
        setLocationRelativeTo(dashboardGUI);
        setVisible(true);
    }

    private void initActionsListener() {
        btnUpdate.addActionListener(e -> {
            if (textFieldLastName.getText().length() == 0
                    || textFieldFirstName.getText().length() == 0
                    || textFieldNIC.getText().length() == 0
                    || textFieldBirthday.getText().length() == 0
                    || textFieldAddress.getText().length() == 0
                    || textFieldPhoneNumber.getText().length() == 0
            ) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate birthDay;
            try {
                birthDay = LocalDate.parse(textFieldBirthday.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException exception) {
                JOptionPane.showMessageDialog(this, "Invalid format for date!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            EntityManager entityManager = EntityManagerInstance.getNewInstance();
            Long count = entityManager
                    .createQuery("select count(p) from Person p where p.nic = :nic and p.id != :id", Long.class)
                    .setParameter("nic", textFieldNIC.getText())
                    .setParameter("id", person.getId())
                    .getSingleResult();
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "NIC already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            entityManager.getTransaction().begin();
            person.setFirstName(textFieldFirstName.getText());
            person.setLastName(textFieldLastName.getText());
            person.setNic(textFieldNIC.getText());
            person.setBirthday(birthDay);
            person.setAddress(textFieldAddress.getText());
            person.setPhoneNumber(textFieldPhoneNumber.getText());
            entityManager.merge(person);
            entityManager.getTransaction().commit();
            entityManager.close();
            JOptionPane.showMessageDialog(this, "Information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
    }
}
