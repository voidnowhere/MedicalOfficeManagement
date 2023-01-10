package org.example.GUIs;

import jakarta.persistence.EntityManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.example.Entities.Document;
import org.example.Entities.Patient;
import org.example.Entities.Prescription;
import org.example.Models.EntityManagerInstance;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class UpdateDocumentGUI extends JDialog {
    private PatientRecordGUI patientRecordGUI;
    private Document document;
    private Patient patient;
    private JPanel contentPane;
    private JTextArea textAreaDocumentDescription;
    private JButton btnUpdateDocument, btnSaveDocument;

    public UpdateDocumentGUI(PatientRecordGUI patientRecordGUI, Document document, Patient patient) {
        super(patientRecordGUI, "Update Document", true);
        this.patientRecordGUI = patientRecordGUI;
        this.document = document;
        this.patient = patient;
        //
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Description");

        textAreaDocumentDescription = new JTextArea(document.getDescription());
        JScrollPane scrollPaneTextAreaDocumentDescription = new JScrollPane(textAreaDocumentDescription);

        btnUpdateDocument = new JButton("Update");

        btnSaveDocument = new JButton("Save");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPaneTextAreaDocumentDescription, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblNewLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                                                .addComponent(btnUpdateDocument)
                                                .addGap(18)
                                                .addComponent(btnSaveDocument)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(btnSaveDocument)
                                        .addComponent(btnUpdateDocument))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(scrollPaneTextAreaDocumentDescription, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        //
        initActionsListener();
        //
        setLocationRelativeTo(this.patientRecordGUI);
        setVisible(true);
    }

    private void initActionsListener() {
        btnUpdateDocument.addActionListener(e -> {
            if (document != null) {
                if (textAreaDocumentDescription.getText().length() > 1000) {
                    JOptionPane.showMessageDialog(this, "Description should not exceed 1000 character!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EntityManager entityManager = EntityManagerInstance.getNewInstance();
                entityManager.getTransaction().begin();
                document.setDescription(textAreaDocumentDescription.getText());
                entityManager.merge(document);
                entityManager.getTransaction().commit();
                entityManager.close();
                JOptionPane.showMessageDialog(this, "Document updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                patientRecordGUI.fillTableDocuments();
            }
        });
        btnSaveDocument.addActionListener(e -> {
            String documentType = (document instanceof Prescription) ? "Prescription" : "Medical Certificate";
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Document");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF File", "pdf"));
            fileChooser.setSelectedFile(new File(documentType + " " + patient.getFullName() + " " + document.getDateTime().toLocalDate().toString()));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    PDDocument pdfDocument = new PDDocument();
                    PDPage pdfPage = new PDPage();
                    pdfDocument.addPage(pdfPage);
                    // Get logo
                    PDImageXObject image = PDImageXObject.createFromFile(Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo.png")).getPath(), pdfDocument);
                    PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);
                    // Draw logo
                    contentStream.drawImage(
                            image,
                            pdfPage.getMediaBox().getWidth() - 150,
                            pdfPage.getMediaBox().getHeight() - 150,
                            125,
                            125
                    );
                    // Title
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(
                            (pdfPage.getMediaBox().getWidth() - (PDType1Font.HELVETICA_BOLD.getStringWidth(documentType) / 1000 * 20)) / 2,
                            pdfPage.getMediaBox().getHeight() - 200
                    );
                    contentStream.showText(documentType);
                    contentStream.endText();
                    // Document description
                    contentStream.setFont(PDType1Font.HELVETICA, 14);
                    String[] lines = document.getDescription().split("\n");
                    float writeY = pdfPage.getMediaBox().getHeight() - 250;
                    for (String line : lines) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(80, writeY);
                        writeY -= 20;
                        contentStream.showText(line);
                        contentStream.endText();
                    }
                    /*
                    float writeY = pdfPage.getMediaBox().getHeight() - 250;
                    float lineMaxWidth = 450;
                    float currentLineWidth = 0;
                    int lineWordsCount = 0;
                    int wordsCount = 0;
                    StringBuilder line = new StringBuilder();
                    StringBuilder backupWords = new StringBuilder();
                    String[] words = document.getDescription().replace("\n", "").split(" ");
                    for (String word : words) {
                        float wordWidth = PDType1Font.HELVETICA.getStringWidth(backupWords + " " + word + " ") / 1000 * 14;
                        boolean written = false;
                        lineWordsCount++;
                        wordsCount++;
                        if (wordWidth + currentLineWidth < lineMaxWidth) {
                            line.append(backupWords).append(" ").append(word).append(" ");
                            backupWords = new StringBuilder();
                            currentLineWidth += wordWidth;
                        } else {
                            backupWords.append(word).append(" ");
                            written = true;
                            contentStream.beginText();
                            contentStream.newLineAtOffset(80, writeY);
                            writeY -= 20;
                            contentStream.setWordSpacing((lineMaxWidth - currentLineWidth) / lineWordsCount);
                            contentStream.showText(line.toString());
                            contentStream.endText();
                            lineWordsCount = 0;
                            currentLineWidth = 0;
                            line = new StringBuilder();
                        }
                        if (wordsCount == words.length && !written) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(80, writeY);
                            writeY -= 20;
                            contentStream.showText(line.toString());
                            contentStream.endText();
                        }
                    }
                     */
                    // Patient name
                    contentStream.setFont(PDType1Font.HELVETICA, 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(pdfPage.getMediaBox().getWidth() - 200, 125);
                    contentStream.showText(patient.getFullName());
                    contentStream.endText();
                    // Document date
                    contentStream.setFont(PDType1Font.HELVETICA, 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(pdfPage.getMediaBox().getWidth() - 200, 100);
                    contentStream.showText(document.getDateTime().toLocalDate().toString());
                    contentStream.endText();

                    // Saving pdf file
                    contentStream.close();
                    pdfDocument.save(fileChooser.getSelectedFile().getAbsolutePath() + ".pdf");
                    pdfDocument.close();

                    JOptionPane.showMessageDialog(this, "Document is saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (FileNotFoundException fileNotFoundException) {
                    JOptionPane.showMessageDialog(this, "A PDF document with same name is in use close it!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        });
    }
}
