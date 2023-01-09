package org.example.GUIs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.Entities.*;
import org.example.Models.EntityManagerInstance;
import org.example.Models.AppointmentsTableModel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class AppointmentsManagementGUI extends JFrame {
    private JPanel contentPane;
    private JTable tableAppointments;
    private JTextField InputCIN;
    private JTextField InputDate;
    private JTextField InputYear;

    private JTextField InputMonth;
    private JTextField InputDay;

    private AppointmentsTableModel appointmentsTableModel;

    //mes variables
    private String YearSelected;
    private String MonthSelected;
    private String DaySelected;
    private String TimeSelected;
    private Doctor doctor;
    private Receptionist receptionist;
    private Patient patient;
    private TypeConsultation typeConsultation;

    public AppointmentsManagementGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        appointmentsTableModel = new AppointmentsTableModel(new ArrayList<>());
        tableAppointments = new JTable(appointmentsTableModel);
        tableAppointments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneTableAppointments = new JScrollPane(tableAppointments);

        InputCIN = new JTextField();
        InputCIN.setColumns(10);

        /*InputDate = new JTextField();
        InputDate.setColumns(10);
        InputDate.setEnabled(false);*/

        JButton btnSearch = new JButton("Search");

        JButton btnadd = new JButton("Add");
        btnadd.setEnabled(false);

        JLabel lblName = new JLabel("FullName");

        JLabel lblNIC = new JLabel("NIC");

        JLabel lblDate = new JLabel("Date");
        lblDate.setEnabled(false);

       /* InputYear = new JTextField();
        InputYear.setColumns(10);
        InputYear.setEnabled(false);

        InputMonth = new JTextField();
        InputMonth.setColumns(10);
        InputMonth.setEnabled(false);

        InputDay = new JTextField();
        InputDay.setColumns(10);
        InputDay.setEnabled(false);

        JLabel lblYear = new JLabel("Year");
        lblYear.setEnabled(false);

        JLabel lblMonth = new JLabel("Month");
        lblMonth.setEnabled(false);

        JLabel lblDay = new JLabel("Day");
        lblDay.setEnabled(false);*/

        int Cyear = LocalDateTime.now().getYear();
        String CurrentYear =  Integer.toString(Cyear);
        String[] YearC = {"Year",CurrentYear};
        JComboBox Yearpicker = new JComboBox(YearC);
        Yearpicker.setEnabled(false);

        String[] AllMonth = {"Month ","1","2","3","4","5","6","7","8","9","10","11","12"};
        JComboBox Monthpicker = new JComboBox(AllMonth);
        Monthpicker.setEnabled(false);

        String[] AllDay = {"Day","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        JComboBox Daypicker = new JComboBox(AllDay);
        Daypicker.setEnabled(false);

        String[] time = {"Time","9h00","9h30","10h00","10h30","11h00","11h30","12h00","14h30","15h00","15h30","16h00","16h30","17h30","00h00"};
        JComboBox Timepicker = new JComboBox(time);
        Timepicker.setEnabled(false);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPaneTableAppointments, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(lblNIC, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(InputCIN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(lblName))
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                                        .addComponent(Yearpicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                        .addComponent(Monthpicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                        .addComponent(Daypicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(Timepicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(214)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(btnadd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnSearch))
                                                .addGap(16)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(15)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNIC)
                                                        .addComponent(InputCIN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblName))
                                                .addGap(18)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(Yearpicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Monthpicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Daypicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Timepicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblDate)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(24)
                                                .addComponent(btnSearch)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(btnadd)))
                                .addGap(30)
                                .addComponent(scrollPaneTableAppointments, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(21, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);

       //boutton ajouter un rdv
            btnadd.addActionListener(e ->{
              /*  Patient p = new Patient("test","test","DD1111","Marrakech", LocalDate.of(2001,9,22),"0606060606");
                  Doctor d = new Doctor("elzrek","elzrek","DO1598","Marrakech", LocalDate.of(1988,7,2),"0608060708","");
                  Receptionist r = new Receptionist("sara","sara","RE1598","Marrakech", LocalDate.of(1997,10,10),"0608065088","",true);
                  entityManager.getTransaction().begin();
                    entityManager.persist(p);
            entityManager.persist(d);
            entityManager.persist(r);
            entityManager.getTransaction().commit();
            entityManager.clear();*/

                EntityManager entityManager = EntityManagerInstance.getNewInstance();

                // Object Doctor
                Query query_docteur = entityManager.createQuery("SELECT d from Doctor d where d.nic LIKE 'DO1598' ", Doctor.class);
                List<Doctor> doctors = query_docteur.getResultList();

                for (Doctor doc : doctors){
                    doctor = doc;
                }

                // Object Receptionist
                Query query_receptionist = entityManager.createQuery("SELECT r from Receptionist r where r.nic LIKE 'RE1598' ", Receptionist.class);
                List<Receptionist> receptionists = query_receptionist.getResultList();

                for (Receptionist rec : receptionists){
                    receptionist = rec;
                }

                // Object Patient
                Query query_patient = entityManager.createQuery("SELECT p from Patient p where p.nic LIKE '"+InputCIN.getText()+"'", Patient.class);
                List<Patient> patientQ = query_patient.getResultList();

                for (Patient pat : patientQ){
                   patient = pat;
                }

                // Object typeconsultation
                Query query_typeconsultation = entityManager.createQuery("SELECT t from TypeConsultation t", TypeConsultation.class);
                List<TypeConsultation> typeConsultations = query_typeconsultation.getResultList();

                for (TypeConsultation typ : typeConsultations){
                    typeConsultation = typ;
                }

                int inputYear = Integer.parseInt(YearSelected);
                int inputMonth = Integer.parseInt(MonthSelected);
                int inputDay = Integer.parseInt(DaySelected);
                String[] SplitTime = TimeSelected.split("h",0);
                int hour = Integer.parseInt(SplitTime[0]);
                int minute = Integer.parseInt(SplitTime[1]);

                try{
                    LocalDateTime DateConsultation = LocalDateTime.of(inputYear,inputMonth,inputDay,hour,minute);
                    LocalDateTime CurrentDate = LocalDateTime.now();

                    //l'ajout d'une consultation
                    if(DateConsultation.equals(CurrentDate) || DateConsultation.isBefore(CurrentDate)){ JOptionPane.showMessageDialog(null,"La Date est Invalid","ERROR",JOptionPane.ERROR_MESSAGE); return;}

                                Long CountDate = entityManager
                                        .createQuery("SELECT count(c) from Consultation c where c.dateTime =: dateC", Long.class)
                                        .setParameter("dateC",DateConsultation)
                                        .getSingleResult();

                                // condition pour la redondance de la date du consultation
                                if(CountDate == 1){
                                    JOptionPane.showMessageDialog(null,"this date is already reserved","ERROR",JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                    entityManager.getTransaction().begin();
                    entityManager.persist(new Consultation(DateConsultation,"testDescription",false,250.00,doctor,receptionist,patient,typeConsultation));
                    entityManager.getTransaction().commit();
                    entityManager.clear();


                for (Patient pat : patientQ){

                    fillTableConsultation(pat.getId());
                }
                JOptionPane.showMessageDialog(null,"well-established addition");

               /* InputYear.setText("");
                InputMonth.setText("");
                InputDay.setText("");*/
                Yearpicker.setSelectedIndex(0);
                Monthpicker.setSelectedIndex(0);
                Daypicker.setSelectedIndex(0);
                Timepicker.setSelectedIndex(0);

                }catch (Exception ee){JOptionPane.showMessageDialog(null,"The date is not valid","ERROR",JOptionPane.ERROR_MESSAGE);}

            });

        //action pour selection pour year
        Yearpicker.addActionListener(e -> {
            YearSelected= (String)Yearpicker.getSelectedItem();

        });

        //action pour selection pour month
        Monthpicker.addActionListener(e -> {
            MonthSelected = (String)Monthpicker.getSelectedItem();

        });

        //action pour selection pour day
        Daypicker.addActionListener(e -> {
            DaySelected = (String)Daypicker.getSelectedItem();

        });

        //action pour selection pour time
        Timepicker.addActionListener(e -> {
            TimeSelected = (String)Timepicker.getSelectedItem();

        });

        //btn de recherche
        btnSearch.addActionListener(e -> {
            EntityManager entityManager = EntityManagerInstance.getNewInstance();

            if(InputCIN.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Please fill in the NIC","Attention",JOptionPane.WARNING_MESSAGE);
                return;
            }else{
                Query query = entityManager.createQuery("SELECT p from Patient p where p.nic LIKE '"+InputCIN.getText()+"'", Patient.class);
                List<Patient> patient = query.getResultList();

                    Long CountPatient = entityManager
                            .createQuery("SELECT count(p) FROM Patient p where p.nic =:nic", Long.class)
                            .setParameter("nic", InputCIN.getText())
                            .getSingleResult();

                    if (CountPatient == 1) {
                        for (Patient patient1 : patient) {
                            lblName.setText(patient1.getFullName());
                            fillTableConsultation(patient1.getId());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "patient not found","ERROR",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
            }
            lblDate.setEnabled(true);
           // InputDate.setEnabled(true);

         /*   lblYear.setEnabled(true);
            InputYear.setEnabled(true);

            lblMonth.setEnabled(true);
            InputMonth.setEnabled(true);

            lblDay.setEnabled(true);
            InputDay.setEnabled(true);*/

            btnadd.setEnabled(true);
            Yearpicker.setEnabled(true);
            Monthpicker.setEnabled(true);
            Daypicker.setEnabled(true);
            Timepicker.setEnabled(true);

        });

        //vider la table
        InputCIN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<Consultation> c = new ArrayList<>();
                appointmentsTableModel.setConsultations(c);
                appointmentsTableModel.fireTableDataChanged();

                lblDate.setEnabled(false);
                btnadd.setEnabled(false);
                Yearpicker.setEnabled(false);
                Monthpicker.setEnabled(false);
                Daypicker.setEnabled(false);
                Timepicker.setEnabled(false);
                lblName.setText("FullName");
            }
        });

        //
        setVisible(true);
    }
    public void fillTableConsultation(Long id){
        EntityManager entityManager = EntityManagerInstance.getNewInstance();
        appointmentsTableModel.setConsultations(entityManager
                .createQuery("select c from Consultation c where c.patient.id =: id", Consultation.class)
                .setParameter("id",id)
                .getResultList()
        );
    entityManager.close();
    appointmentsTableModel.fireTableDataChanged();

    }


}
