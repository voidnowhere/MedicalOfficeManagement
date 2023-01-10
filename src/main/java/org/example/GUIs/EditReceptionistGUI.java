package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Patient;
import org.example.Entities.Receptionist;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditReceptionistGUI extends JFrame{

    private JPanel contentPane;
    private JTextField textField_FirstName;
    private JTextField textField_LastName;
    private JTextField textField_Address;
    private JTextField textField_PhoneNumber;
    private JTextField textField_CIN;

    public EditReceptionistGUI(Receptionist receptionist, ReceptionistListGUI receptionistListGUI) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(52, 50, 337, 337);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);

        JLabel lblCIN = new JLabel("CIN");
        lblCIN.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JLabel lbladdress = new JLabel("Addresse");
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JLabel lblBirthday = new JLabel("Birthday");
        lblBirthday.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JLabel lblPhoneNumber = new JLabel("Phone Number");
        lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));

        textField_FirstName = new JTextField();
        textField_FirstName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textField_FirstName.setColumns(10);

        textField_LastName = new JTextField();
        textField_LastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textField_LastName.setColumns(10);

        textField_Address = new JTextField();
        textField_Address.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textField_Address.setColumns(10);

        textField_PhoneNumber = new JTextField();
        textField_PhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textField_PhoneNumber.setColumns(10);

        JButton btnEditPateient = new JButton("Edit");

        btnEditPateient.setFont(new Font("Tahoma", Font.BOLD, 12));

        textField_CIN = new JTextField();
        textField_CIN.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textField_CIN.setColumns(10);
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        JFormattedTextField dateField = new JFormattedTextField(df);
        dateField.setToolTipText("yyyy-mm-dd");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(35)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lblLastName)
                                                        .addComponent(lblFirstName, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                                        .addComponent(lblCIN, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lbladdress, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblBirthday, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                                .addGap(33))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblPhoneNumber, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)))
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(btnEditPateient, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                                .addComponent(textField_Address, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(textField_LastName, 109, 109, Short.MAX_VALUE))
                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(textField_FirstName, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                                .addComponent(textField_PhoneNumber, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                                .addComponent(dateField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                        .addComponent(textField_CIN, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
                                .addGap(177))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addGap(25)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblFirstName)
                                        .addComponent(textField_FirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblLastName)
                                        .addComponent(textField_LastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCIN)
                                        .addComponent(textField_CIN, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbladdress)
                                        .addComponent(textField_Address, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblBirthday)
                                        .addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField_PhoneNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPhoneNumber))
                                .addGap(18)
                                .addComponent(btnEditPateient, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setTitle("Edit Receptionist");
        // Fill text fields
        textField_FirstName.setText(receptionist.getFirstName());
        textField_LastName.setText(receptionist.getLastName());
        textField_CIN.setText(receptionist.getNic());
        textField_Address.setText(receptionist.getAddress());
        dateField.setText(receptionist.getBirthday().toString());
        textField_PhoneNumber.setText(receptionist.getPhoneNumber());
        // Edit button
        btnEditPateient.addActionListener(e -> {
            if (textField_FirstName.getText().length() == 0
                    || textField_LastName.getText().length() == 0
                    || textField_Address.getText().length() == 0
                    || dateField.getText().length() == 0
                    || textField_CIN.getText().length() == 0
                    || textField_PhoneNumber.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Update Database
            EntityManager entityManager = EntityManagerInstance.getNewInstance();
            entityManager.getTransaction().begin();
            receptionist.setFirstName(textField_FirstName.getText());
            receptionist.setLastName(textField_LastName.getText());
            receptionist.setNic(textField_CIN.getText());
            receptionist.setAddress(textField_Address.getText());
            receptionist.setBirthday(LocalDate.parse(dateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            receptionist.setPhoneNumber(textField_PhoneNumber.getText());
            entityManager.merge(receptionist);
            entityManager.getTransaction().commit();
            entityManager.close();
            JOptionPane.showMessageDialog(this, "Receptionist updated successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            receptionistListGUI.fillReceptionistTable();
        });
    }
}

