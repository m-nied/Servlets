
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import org.jfree.data.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.plot.*;
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.xml.transform.TransformerConfigurationException;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

public class StudentRegistration extends javax.swing.JApplet {    
    private static final int MAX_RECORDS = 100;
    
    // Variables and Instances of Classes
    private StudentRecord currentStudent;       //store an instance of current Student which is a StudentRecord
    private StudentRecord studentArray[] = new StudentRecord[MAX_RECORDS];        //store all student records
    private int nextStudent = 0;         // location of next empty position in the array
    private int numStudents = 0;         // number of input student records
            
    private String xmlFirstName;      // temporary storage for first name from xml
    private String xmlLastName;       //temporary storage for last name from xml
    private String xmlDegreeStatus;   //temporary storage for degree status from xml
    private String xmlMajor;          // temporary storage for major from xml
    private Database myDatabase = new Database();       //instance of the database class
    private DefaultCategoryDataset studentData = new DefaultCategoryDataset();
    private DefaultPieDataset pieData = new DefaultPieDataset();
    DefaultListModel model = new DefaultListModel();

    public StudentRegistration() {
        this.displayList = new JList(model);
    }
    
    /**
     * Initializes the applet StudentRegistration
     */
    @Override
    public void init() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        /*
            Bar and Pie Chart initialization
            code accessible through the JFreeChart plugin
        */
        JFreeChart barChart = ChartFactory.createBarChart("Student's Degree Status","Status", "Students", studentData, PlotOrientation.VERTICAL, false,true, false);
        barChart.setBackgroundPaint(Color.white);
        barChart.getTitle().setPaint(Color.black); 
        CategoryPlot barPlot = barChart.getCategoryPlot(); 
        barPlot.setRangeGridlinePaint(Color.red); 
        ChartPanel barChartFrame = new ChartPanel(barChart);
        barChartFrame.setVisible(true);
        barChartPanel.setLayout(new java.awt.BorderLayout());
        barChartPanel.add(barChartFrame,BorderLayout.CENTER);
        
        JFreeChart pieChart = ChartFactory.createPieChart("Student Majors", pieData, true, true, false);
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setSectionPaint("IST", Color.green);
        piePlot.setSectionPaint("Business", Color.red);
        piePlot.setSectionPaint("Nursing", Color.blue);
        piePlot.setSectionPaint("HDFS", Color.ORANGE);
        piePlot.setSimpleLabels(true);
        ChartPanel pieChartFrame = new ChartPanel(pieChart);
        pieChartFrame.setVisible(true);
        pieChartPanel.setLayout(new java.awt.BorderLayout());
        pieChartPanel.add(pieChartFrame,BorderLayout.CENTER);
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        piePlot.setLabelGenerator(gen);
        setSize(1700,950);
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileNameTextArea = new javax.swing.JTextField();
        loadXMLButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        barChartPanel = new javax.swing.JPanel();
        saveToXMLButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        displayList = new javax.swing.JList();
        pieChartPanel = new javax.swing.JPanel();

        fileNameTextArea.setText("Enter Filename Here");
        fileNameTextArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNameTextAreaActionPerformed(evt);
            }
        });

        loadXMLButton.setText("Load XML");
        loadXMLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadXMLButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout barChartPanelLayout = new javax.swing.GroupLayout(barChartPanel);
        barChartPanel.setLayout(barChartPanelLayout);
        barChartPanelLayout.setHorizontalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        barChartPanelLayout.setVerticalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        saveToXMLButton.setText("Save to XML");
        saveToXMLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToXMLButtonActionPerformed(evt);
            }
        });

        displayList.setModel(model);
        displayList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(displayList);

        javax.swing.GroupLayout pieChartPanelLayout = new javax.swing.GroupLayout(pieChartPanel);
        pieChartPanel.setLayout(pieChartPanelLayout);
        pieChartPanelLayout.setHorizontalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );
        pieChartPanelLayout.setVerticalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pieChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addGap(18, 18, 18)
                        .addComponent(removeButton)
                        .addGap(18, 18, 18)
                        .addComponent(saveToXMLButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileNameTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(loadXMLButton)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileNameTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadXMLButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pieChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(barChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(removeButton)
                    .addComponent(saveToXMLButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fileNameTextAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNameTextAreaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_fileNameTextAreaActionPerformed

    private void loadXMLButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadXMLButtonActionPerformed
        // TODO add your handling code here:
//        get contents of xml file and load the array
        readFile(fileNameTextArea.getText());
        displayData();
        storeData();
        barChart();
        pieChart();
        
    }//GEN-LAST:event_loadXMLButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        // TODO add your handling code here:
        //get user input for student deletion
        int id= Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Enter the ID of the Student to be deleted", "Delete Student",JOptionPane.QUESTION_MESSAGE));
        String fName = JOptionPane.showInputDialog(rootPane, "Enter the First Name of the Student to be deleted", "Delete Student",JOptionPane.QUESTION_MESSAGE);
        String lName = JOptionPane.showInputDialog(rootPane, "Enter the Last Name of the Student to be deleted", "Delete Student",JOptionPane.QUESTION_MESSAGE);
        String status = JOptionPane.showInputDialog(rootPane, "Enter the Degree Status of the Student to be deleted", "Delete Student",JOptionPane.QUESTION_MESSAGE);
        String major = JOptionPane.showInputDialog(rootPane, "Enter the Major of the Student to be deleted", "Delete Student",JOptionPane.QUESTION_MESSAGE);
        myDatabase.deleteStudent(id,fName,lName,status,major);
        if(myDatabase.getDBEntry()==true){
            StudentRecord student = new StudentRecord(id, fName, lName, status, major);
            studentArray[id] = student;
            model.remove(id);
            JOptionPane.showMessageDialog(null, "Student deleted successfully", "Deleted Student", JOptionPane.INFORMATION_MESSAGE);
        }//end if
        else if(myDatabase.getDBEntry()==false){
            JOptionPane.showMessageDialog(null, "Student does not exist\n No record deleted.", "No Student", JOptionPane.INFORMATION_MESSAGE);
        }
        barChart();
        pieChart();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        //get user input for adding student
        String fName = JOptionPane.showInputDialog(rootPane, "Enter the First Name of the Student to be added", "Add Student",JOptionPane.QUESTION_MESSAGE);
        String lName = JOptionPane.showInputDialog(rootPane, "Enter the Last Name of the Student to be added", "Add Student",JOptionPane.QUESTION_MESSAGE);
        String status = JOptionPane.showInputDialog(rootPane, "Enter the Degree Status of the Student to be added", "Add Student",JOptionPane.QUESTION_MESSAGE);
        String major = JOptionPane.showInputDialog(rootPane, "Enter the Major of the Student to be added", "Add Student",JOptionPane.QUESTION_MESSAGE);
        myDatabase.addStudent(nextStudent,fName, lName, status, major);
         
        if(myDatabase.getDBEntry()==true){
            StudentRecord student = new StudentRecord(nextStudent, fName, lName, status, major);
            studentArray[nextStudent] = student;
            model.addElement(studentArray[nextStudent].toString()+"\n");
            System.out.println("StudentArray[nextStudent] = "+studentArray[nextStudent].toString());
            numStudents++;
            nextStudent++;
            JOptionPane.showMessageDialog(null, "Student added successfully", "Added Student", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(myDatabase.getDBEntry()==false){
            JOptionPane.showMessageDialog(null, "Student already exists\n No record added.", "Existing Student", JOptionPane.INFORMATION_MESSAGE);
        }
        barChart();
        pieChart();
    }//GEN-LAST:event_addButtonActionPerformed

    private void saveToXMLButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToXMLButtonActionPerformed
        // TODO add your handling code here:
        try {
            myDatabase.updateStudentXml();
        } catch (ClassNotFoundException | SQLException | IOException | TransformerException ex) {
            Logger.getLogger(StudentRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(rootPane, "File saved to 'Students.xml'", "Saved File", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_saveToXMLButtonActionPerformed

    private void displayListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayListMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_displayListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel barChartPanel;
    private javax.swing.JList displayList;
    private javax.swing.JTextField fileNameTextArea;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton loadXMLButton;
    private javax.swing.JPanel pieChartPanel;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton saveToXMLButton;
    // End of variables declaration//GEN-END:variables



 public void displayData ()
{   
        for (int i = 0; i<numStudents; i++)
        {
            model.addElement(studentArray[i].toString()+"\n");
        }
 }//end displayData
 
 public void queryData ()
    {
        studentArray =  myDatabase.getQueryData();
        //displayList.append("\nQuery Data \n");
        for (int i = 0; i<numStudents; i++)
        {
           model.addElement(studentArray[i].getFirstName()+" " + studentArray[i].getLastName()+"\n");
           System.out.println(studentArray[i]);
        }
 }//end displayData
 
  public void storeData ()
  {
      myDatabase.createTable();
      
      // store each Student Record in the table
      for (int i = 0; i<numStudents; i++)
       {
           myDatabase.storeRecord(
                   studentArray[i].getStudentID(), 
                   studentArray[i].getFirstName(),
                   studentArray[i].getLastName(), 
                   studentArray[i].getDegreeStatus(),
                   studentArray[i].getMajor());
      }
  }//end storeData
  
  
  public void barChart(){//set values for barChart
      myDatabase.getStatusCounts();
      studentData.setValue(myDatabase.getFTCount(), "Status", "Full-Time");
      studentData.setValue(myDatabase.getPTCount(), "Status", "Part-Time");
      studentData.setValue(myDatabase.getNDCount(), "Status", "Non-Degree");
      studentData.setValue(myDatabase.getProCount(), "Status", "Provisional");
//      System.out.println("IST count = "+myDatabase.getISTCount()+"\nBusiness Count = "+myDatabase.getBusinessCount()+"\nHDFS count = "+myDatabase.getHDFSCount()+"\nNursing count = "+myDatabase.getNursingCount());
  }
 
  public void pieChart(){//set values for pieChart
        myDatabase.getStudentCounts();
        pieData.setValue("IST", myDatabase.getISTCount());
        pieData.setValue("Business", myDatabase.getBusinessCount());
        pieData.setValue("Nursing", myDatabase.getNursingCount());
        pieData.setValue("HDFS", myDatabase.getHDFSCount());
  }
  
    //the method reads info from the input XML file, and then stores it in the studentArray[] 
    public void readFile(String filename){
        try
        {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            NodeList list = document.getElementsByTagName("Student");
           
            //This for loop gathers all the student attributes, puts them in a StudentRecord object
            //then stores that student in the StudentArray
            for(int i = 0; i < list.getLength(); i++)
            { 
                Element element = (Element)list.item(i);
                xmlFirstName = getFirstName(element);
                xmlLastName = getLastName(element);
                xmlDegreeStatus = getDegreeStatus(element);
                xmlMajor = getMajor(element);
                StudentRecord student = new StudentRecord(i, xmlFirstName, xmlLastName, xmlDegreeStatus, xmlMajor);
                
                // store student record in array
                studentArray[nextStudent] = student;
                System.out.println("StudentArray[nextStudent] = "+studentArray[nextStudent].toString());
                
                // increment number of student records and move to next position in studentArray
                numStudents++;
                nextStudent++;
                
            }//end for loop loading the studentArray[] with full student records
            
        }//end try block
        catch (ParserConfigurationException | SAXException | IOException parserException)
        {
        }//end catch block
       
    }//end readFile()

    //RETURNS THE FIRST NAME OF THE STUDENT
    public String getFirstName(Element parent){ 
        NodeList child = parent.getElementsByTagName("fName");
        Node childTextNode = child.item(0).getFirstChild();
        return childTextNode.getNodeValue();  
    }//end getFirstName
    
   //RETURNS THE LAST NAME OF THE STUDENT    
    public String getLastName(Element parent){ 
        NodeList child = parent.getElementsByTagName("lName");
        Node childTextNode = child.item(0).getFirstChild();
        return childTextNode.getNodeValue();  
    }//end getLastName
    
    //RETURNS THE DEGREE STATUS OF THE STUDENT    
    public String getDegreeStatus(Element parent){ 
        NodeList child = parent.getElementsByTagName("status");
        Node childTextNode = child.item(0).getFirstChild();
        return childTextNode.getNodeValue();  
    }//end getDegreeStatus
    
//RETURNS THE MAJOR OF THE STUDENT    
    public String getMajor(Element parent){ 
        NodeList child = parent.getElementsByTagName("major");
        Node childTextNode = child.item(0).getFirstChild();
        return childTextNode.getNodeValue();  
    }//end getFirstName
}//end applet

//public void actionPerformed (ActionEvent e)
//    {
//        //get contents of xml file and load the array
//        readFile(fileNameTextArea.getText());
//        
//         // display data in the text area
//        displayData();
//        
//        // store student data in the database
//        storeData();
//        
//        //display query data
//        //queryData();
//        
//    }//end actionPerformed
