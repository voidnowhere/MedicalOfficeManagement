package org.example.GUIs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.Entities.*;
import org.example.Models.AppointmentsTableModel;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AppointmentsManagementGUI extends JDialog {
    private Person person;
    private boolean isAdmin;
    private Patient patient;
    private TypeConsultation typeConsultation;
    private Doctor doctor;
    private JPanel contentPane;
    private JTextField textFieldNIC;
    private JTextField textFieldDate;
    private JButton btnCreate, btnSearch, btnEditConsultation;
    private AppointmentsTableModel appointmentsTableModel;
    private JTable tableAppointments;
    private JLabel lblFullName;
    private JComboBox comboBoxTime;

    public AppointmentsManagementGUI(DashboardGUI dashboardGUI, Person person) {
        super(dashboardGUI, "Appointments Management", true);
        this.person = person;
        this.isAdmin = person instanceof Doctor;
        //
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        doctor = entityManager.createQuery("select d from Doctor d", Doctor.class).getSingleResult();
        typeConsultation = entityManager.createQuery("select t from TypeConsultation t", TypeConsultation.class).getSingleResult();
        entityManager.close();
        //
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 525, 375);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("NIC");

        lblFullName = new JLabel("Full Name");

        textFieldNIC = new JTextField();
        textFieldNIC.setColumns(10);

        btnSearch = new JButton("Search");

        btnEditConsultation = new JButton("Consultation");
        btnEditConsultation.setEnabled(false);

        JLabel lblNewLabel_1 = new JLabel("When");

        textFieldDate = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        textFieldDate.setColumns(10);

        comboBoxTime = new JComboBox();
        comboBoxTime.setModel(new DefaultComboBoxModel(new String[]{"Time", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"}));

        btnCreate = new JButton("Create");
        btnCreate.setEnabled(false);

        appointmentsTableModel = new AppointmentsTableModel(new ArrayList<>());
        tableAppointments = new JTable(appointmentsTableModel);
        JScrollPane scrollPaneTableAppointments = new JScrollPane(tableAppointments);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPaneTableAppointments, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_1)
                                                        .addComponent(lblNewLabel))
                                                .addGap(18)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(textFieldNIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(lblFullName)
                                                                .addPreferredGap(ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                                                                .addComponent(btnSearch))
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(comboBoxTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                                                                .addComponent(btnCreate))))
                                        .addComponent(btnEditConsultation, Alignment.TRAILING))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(textFieldNIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch)
                                        .addComponent(lblFullName))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCreate)
                                        .addComponent(comboBoxTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(btnEditConsultation)
                                .addGap(18)
                                .addComponent(scrollPaneTableAppointments, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        //
        initActionsListener();
        //
        setLocationRelativeTo(dashboardGUI);
        setVisible(true);
    }

    public void initActionsListener() {
        btnSearch.addActionListener(e -> {
            btnCreate.setEnabled(false);
            btnEditConsultation.setEnabled(false);
            appointmentsTableModel.setConsultations(new ArrayList<>());
            appointmentsTableModel.fireTableDataChanged();
            if (textFieldNIC.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "NIC is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            fillTableConsultation();
        });
        btnEditConsultation.addActionListener(e -> {
            if (!isAdmin) {
                JOptionPane.showMessageDialog(this, "Permission denied!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tableAppointments.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Select is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new EditConsultationGUI(this, appointmentsTableModel.getConsultation(tableAppointments.getSelectedRow()));
        });
        btnCreate.addActionListener(e -> {
            if (textFieldDate.getText().length() == 0 || comboBoxTime.getSelectedIndex() < 1) {
                JOptionPane.showMessageDialog(this, "When is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(
                        textFieldDate.getText() + " " + comboBoxTime.getSelectedItem(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                );
            } catch (DateTimeParseException exception) {
                JOptionPane.showMessageDialog(this, "Invalid format for when!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dateTime.toLocalDate().isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Invalid when!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            EntityManager entityManager = EntityManagerInstance.getNewInstance();
            // check if a consultation already exists in that dateTime
            Long countConsultations = entityManager
                    .createQuery("select count(c) from Consultation c " +
                            "where c.dateTime = :dateTime and c.isCanceled = false ", Long.class)
                    .setParameter("dateTime", dateTime)
                    .getSingleResult();
            if (countConsultations > 0) {
                entityManager.close();
                JOptionPane.showMessageDialog(this, "Chosen when already taken!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // check if patient has more than one consultation
            LocalDateTime from = LocalDateTime.parse(dateTime.toLocalDate().toString() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime to = LocalDateTime.parse(dateTime.toLocalDate().toString() + " 23:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            Long countPatientConsultations = entityManager
                    .createQuery("select count(c) from Consultation c " +
                            "where c.dateTime between :from and :to and c.isCanceled = false and c.patient.id = :pid ", Long.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .setParameter("pid", patient.getId())
                    .getSingleResult();
            if (countPatientConsultations > 0) {
                entityManager.close();
                JOptionPane.showMessageDialog(this, "Patient already has an appointment!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //
            Boolean isNextTime = entityManager
                    .createQuery("select c.nextTime from Consultation c " +
                            "where c.patient.id = :pid and c.isCanceled = false and c.isPaid = true " +
                            "order by dateTime desc limit 1", Boolean.class)
                    .setParameter("pid", patient.getId())
                    .getSingleResult();
            double price = (isNextTime) ? 0 : typeConsultation.getPrice();
            entityManager.getTransaction().begin();
            Consultation consultation;
            if (person instanceof Receptionist receptionist) {
                consultation = new Consultation(dateTime, "", false, price, doctor, receptionist, patient, typeConsultation);
            } else {
                consultation = new Consultation(dateTime, "", false, price, doctor, null, patient, typeConsultation);
            }
            entityManager.persist(consultation);
            entityManager.getTransaction().commit();
            entityManager.close();
            JOptionPane.showMessageDialog(this, "Appointment created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            fillTableConsultation();
        });
    }

    public void fillTableConsultation() {
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        try {
            patient = entityManager
                    .createQuery("select p from Patient p " +
                            "left join fetch p.record left join fetch p.consultations c left join fetch c.receptionist " +
                            "where p.nic = :nic order by c.dateTime desc", Patient.class)
                    .setParameter("nic", textFieldNIC.getText())
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            JOptionPane.showMessageDialog(this, "Patient not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lblFullName.setText(patient.getFullName());
        appointmentsTableModel.setConsultations(patient.getConsultations());
        entityManager.close();
        appointmentsTableModel.fireTableDataChanged();
        btnCreate.setEnabled(true);
        btnEditConsultation.setEnabled(isAdmin);
    }
}
