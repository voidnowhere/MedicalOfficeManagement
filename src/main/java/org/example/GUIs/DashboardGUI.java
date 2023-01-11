package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Consultation;
import org.example.Entities.Doctor;
import org.example.Entities.Person;
import org.example.Models.AppointmentsCalendarTableModel;
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
import java.util.Objects;

public class DashboardGUI extends JFrame {
    private Person person;
    private JPanel contentPane;
    private JTextField textFieldDate;
    private JButton btnSearch, btnCancelAppointment, btnSetPayment, btnEditConsultation;
    private AppointmentsCalendarTableModel appointmentsCalendarTableModel;
    private JTable tableAppointments;
    private JMenuItem menuItemPatientsManagement,
            menuItemAppointmentsManagement,
            menuItemRecordsManagement,
            menuItemReceptionistsManagement,
            menuItemResetPassword,
            menuItemProfileInformation;
    private boolean isAdmin;

    public DashboardGUI(Person person) {
        this.person = person;
        isAdmin = this.person instanceof Doctor;
        setIconImage(new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo.png")).getPath()
        ).getImage());
        setTitle("Dashboard");
        setResizable(false);
        //
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnWhosConnected = new JMenu("Connected as: " + person.getFullName());
        mnWhosConnected.setEnabled(false);
        menuBar.add(mnWhosConnected);

        JMenu mnManagement = new JMenu("Management");
        menuBar.add(mnManagement);

        menuItemPatientsManagement = new JMenuItem("Patients");
        mnManagement.add(menuItemPatientsManagement);

        menuItemAppointmentsManagement = new JMenuItem("Appointments");
        mnManagement.add(menuItemAppointmentsManagement);

        menuItemRecordsManagement = new JMenuItem("Records");
        menuItemRecordsManagement.setVisible(isAdmin);
        mnManagement.add(menuItemRecordsManagement);

        menuItemReceptionistsManagement = new JMenuItem("Receptionists");
        menuItemReceptionistsManagement.setVisible(isAdmin);
        mnManagement.add(menuItemReceptionistsManagement);

        JMenu menuProfile = new JMenu("Profile");
        menuBar.add(menuProfile);

        menuItemProfileInformation = new JMenuItem("Information");
        menuItemProfileInformation.setVisible(isAdmin);
        menuProfile.add(menuItemProfileInformation);

        menuItemResetPassword = new JMenuItem("Reset Password");
        menuProfile.add(menuItemResetPassword);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Date");

        btnSearch = new JButton("Search");

        textFieldDate = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        textFieldDate.setColumns(10);

        btnCancelAppointment = new JButton("Cancel");

        btnSetPayment = new JButton("Pay");

        btnEditConsultation = new JButton("Consultation");
        btnEditConsultation.setVisible(isAdmin);

        appointmentsCalendarTableModel = new AppointmentsCalendarTableModel(new ArrayList<>());
        tableAppointments = new JTable(appointmentsCalendarTableModel);
        tableAppointments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneTableAppointments = new JScrollPane(tableAppointments);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(scrollPaneTableAppointments, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                                .addComponent(lblNewLabel)
                                                .addGap(18)
                                                .addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)
                                                .addComponent(btnSearch)
                                                .addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                                .addComponent(btnEditConsultation)
                                                .addGap(18)
                                                .addComponent(btnCancelAppointment)
                                                .addGap(18)
                                                .addComponent(btnSetPayment)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch)
                                        .addComponent(btnSetPayment)
                                        .addComponent(btnCancelAppointment)
                                        .addComponent(btnEditConsultation))
                                .addGap(18)
                                .addComponent(scrollPaneTableAppointments, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        //
        setLocationRelativeTo(null);
        setVisible(true);
        initActionsListener();
        fillTableAppointments();
    }

    private void initActionsListener() {
        btnSearch.addActionListener(e -> {
            if (textFieldDate.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Date is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                LocalDate.parse(textFieldDate.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException exception) {
                JOptionPane.showMessageDialog(this, "Invalid format for date!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            fillTableAppointments();
        });
        tableAppointments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                return;
            }
            if (tableAppointments.getSelectedRow() >= 0) {
                btnSetPayment.setEnabled(true);
                btnCancelAppointment.setEnabled(true);
                Consultation consultation = appointmentsCalendarTableModel.getConsultation(tableAppointments.getSelectedRow());
                btnSetPayment.setText((consultation.isPaid()) ? "Refund" : "Pay");
                if (consultation.getDateTime().toLocalDate().isBefore(LocalDate.now())) {
                    btnSetPayment.setEnabled(false);
                    btnCancelAppointment.setEnabled(false);
                }
            }
        });
        btnSetPayment.addActionListener(e -> {
            if (tableAppointments.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Select an appointment!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Consultation consultation = appointmentsCalendarTableModel.getConsultation(tableAppointments.getSelectedRow());
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Do you really want to confirm " + ((consultation.isPaid()) ? "refund?" : "payment?"),
                    "Confirmation",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION
            ) {
                EntityManager entityManager = EntityManagerInstance.getNewInstance();
                entityManager.getTransaction().begin();
                consultation.setPaid(!consultation.isPaid());
                entityManager.merge(consultation);
                entityManager.getTransaction().commit();
                entityManager.close();
                JOptionPane.showMessageDialog(
                        this,
                        (consultation.isPaid()) ? "Appointment has been paid!" : "Canceled payment for appointment!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                fillTableAppointments();
            }
        });
        btnCancelAppointment.addActionListener(e -> {
            if (tableAppointments.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Select an appointment!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Do you really want to cancel appointment?",
                    "Confirmation",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION
            ) {
                EntityManager entityManager = EntityManagerInstance.getNewInstance();
                entityManager.getTransaction().begin();
                Consultation consultation = appointmentsCalendarTableModel.getConsultation(tableAppointments.getSelectedRow());
                consultation.setCanceled(true);
                entityManager.merge(consultation);
                entityManager.getTransaction().commit();
                entityManager.close();
                JOptionPane.showMessageDialog(this, "Appointment has been canceled!", "Success", JOptionPane.INFORMATION_MESSAGE);
                fillTableAppointments();
            }
        });
        btnEditConsultation.addActionListener(e -> {
            if (!isAdmin) {
                JOptionPane.showMessageDialog(this, "Permission denied!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tableAppointments.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Select an appointment!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new EditConsultationGUI(this, appointmentsCalendarTableModel.getConsultation(tableAppointments.getSelectedRow()));
        });
        menuItemRecordsManagement.addActionListener(e -> {
            if (!isAdmin) {
                JOptionPane.showMessageDialog(this, "Permission denied!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new PatientRecordGUI(this);
        });
        menuItemResetPassword.addActionListener(e -> {
            new ResetPasswordGUI(this, person);
        });
        menuItemProfileInformation.addActionListener(e -> {
            if (!isAdmin) {
                JOptionPane.showMessageDialog(this, "Permission denied!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new ProfileInformationGUI(this, person);
        });
        menuItemAppointmentsManagement.addActionListener(e -> {
            new AppointmentsManagementGUI(this, person);
        });
        menuItemPatientsManagement.addActionListener(e -> {
            new PatientListGUI(this);
        });
        menuItemReceptionistsManagement.addActionListener(e -> {
            new ReceptionistListGUI(this);
        });
    }

    private void fillTableAppointments() {
        LocalDateTime dateTimeFrom = LocalDateTime.parse(textFieldDate.getText() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime dateTimeTo = LocalDateTime.parse(textFieldDate.getText() + " 23:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        appointmentsCalendarTableModel.setConsultations(entityManager
                .createQuery("select c from Consultation c " +
                                "left join fetch c.receptionist left join fetch c.patient " +
                                "where c.dateTime between :from and :to and c.isCanceled = false " +
                                "order by c.dateTime",
                        Consultation.class
                ).setParameter("from", dateTimeFrom)
                .setParameter("to", dateTimeTo)
                .getResultList()
        );
        entityManager.close();
        appointmentsCalendarTableModel.fireTableDataChanged();
    }
}
