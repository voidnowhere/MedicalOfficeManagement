package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.example.Entities.Consultation;
import org.example.Models.AppointmentsCalendarTableModel;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppointmentsCalendarGUI extends JFrame {
    private JPanel contentPane;
    private JTable tableCalendar;
    private JTextField InputDate;
    private AppointmentsCalendarTableModel appointmentsCalendarTableModel;

    public AppointmentsCalendarGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        appointmentsCalendarTableModel = new AppointmentsCalendarTableModel(new ArrayList<>());
        tableCalendar = new JTable(appointmentsCalendarTableModel);
        tableCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneTableAppointments = new JScrollPane(tableCalendar);


        JButton btnSearch = new JButton("Search");

        InputDate = new JTextField(LocalDate.now().toString());
        InputDate.setColumns(10);

        JLabel lblDate = new JLabel("Date");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap(51, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(8)
                                                .addComponent(lblDate)
                                                .addGap(26)
                                                .addComponent(InputDate, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnSearch))
                                        .addComponent(scrollPaneTableAppointments, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
                                .addGap(48))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(20)
                                                .addComponent(btnSearch))
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(InputDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblDate))))
                                .addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addComponent(scrollPaneTableAppointments, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addGap(51))
        );
        contentPane.setLayout(gl_contentPane);

        //
        setVisible(true);
        //
        btnSearch.addActionListener(e -> {
            fillTableConsultation();
        });
        fillTableConsultation();
    }

    public void fillTableConsultation() {
        LocalDateTime dateTimeFrom = LocalDateTime.parse(InputDate.getText() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime dateTimeTo = LocalDateTime.parse(InputDate.getText() + " 23:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        appointmentsCalendarTableModel.setConsultations(entityManager
                .createQuery("select c from Consultation c LEFT JOIN FETCH Patient, Receptionist where c.dateTime between :from and :to", Consultation.class)
                .setParameter("from", dateTimeFrom)
                .setParameter("to", dateTimeTo)
                .getResultList()
        );
        entityManager.close();
        appointmentsCalendarTableModel.fireTableDataChanged();
    }
}
