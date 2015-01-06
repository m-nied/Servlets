import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;


//Database Class
public class Database {
    
//Declaration of variables    
private PreparedStatement sqlFind;
private int count=0;
private int istCount=0, hdfsCount=0, nursingCount=0, busiCount=0;
private int ftCount=0, ptCount=0, ndCount=0, proCount=0;
private boolean entry=false;
//createTable() drops the current table and creates a new one
    public void createTable() {
//        setDropCount(count);
       try
        {
             // load database driver class
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           
         // connect to database
         Connection con = DriverManager.getConnection("jdbc:odbc:StudentRegistration");
         Statement stmt = con.createStatement();
         
         //this code may need to be commented out because an exception will be thrown
         //if this table doesn't exist in the database
         
            stmt.execute("DROP TABLE Students");
         
         System.out.println("Dropped Students table");
         stmt.execute("CREATE TABLE Students" + 
                         "(ID number, FirstName varchar(255)," +
                         " LastName varchar(255), " + 
                         "DegreeStatus varchar(255), Major varchar(255))");
        
         System.out.println("Created Students table");
         
         stmt.close();
         con.close();
        }
       // detect problems interacting with the database
      catch ( SQLException sqlException ) {
         JOptionPane.showMessageDialog( null, 
            sqlException.getMessage(), "Database Error",
            JOptionPane.ERROR_MESSAGE );
         
         System.exit( 1 );
      }//end catch block
      
      // detect problems loading database driver
      catch ( ClassNotFoundException classNotFound ) {
         JOptionPane.showMessageDialog( null, 
            classNotFound.getMessage(), "Driver Not Found",
            JOptionPane.ERROR_MESSAGE );

         System.exit( 1 );
      }//end catch block
        
   }//end createTable()

    
//this method accepts the student data as input and stores it to the database 
    public void storeRecord(int id, String fName, String lName, String degStat, String maj){
       
        try {
         
         // load database driver class
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           
         // connect to database
         Connection con =
                    DriverManager.getConnection("jdbc:odbc:StudentRegistration");
         
         Statement stmt = con.createStatement();
         //this Insert statement puts student info in the database
         stmt.executeUpdate("INSERT INTO Students VALUES ("+id+",'"+fName+"','" +lName+"','" +degStat+"','"+maj+"')");
         //newSt = id;
         
         stmt.close();
         con.close();
        }//end try
        catch(Exception e) 
        {
                e.printStackTrace();
        }//end catch

    }//end storeRecord()
      
    public StudentRecord[] getQueryData ()
    {
        StudentRecord studentArray[] = new StudentRecord[20];
        int numStudents = 0;
        
        try {
        // load database driver class
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           
         // connect to database
         Connection con =
                    DriverManager.getConnection("jdbc:odbc:StudentRegistration");
         
         Statement stmt = con.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT * from Students");

          while (rs.next())
          {
              String rsFName = rs.getString("FirstName");
              String rsLName = rs.getString("LastName");
              String rsDegreeStat = rs.getString("DegreeStatus");
              String rsMajor = rs.getString("Major");

              studentArray[numStudents] = new StudentRecord(numStudents, rsFName, rsLName, rsDegreeStat, rsMajor);
              numStudents++;
              System.out.println(rsFName + " " + rsLName);
          }
          rs.close();
          stmt.close();

          con.close();

       }
       // detect problems interacting with the database
      catch ( SQLException sqlException ) {
         JOptionPane.showMessageDialog( null, 
            sqlException.getMessage(), "Database Error",
            JOptionPane.ERROR_MESSAGE );
         
         System.exit( 1 );
      }
      
      // detect problems loading database driver
      catch ( ClassNotFoundException classNotFound ) {
         JOptionPane.showMessageDialog( null, 
            classNotFound.getMessage(), "Driver Not Found",
            JOptionPane.ERROR_MESSAGE );

         System.exit( 1 );
      }      
       finally{
           return studentArray;
       }
        

   }
    
    public void deleteStudent(int id, String fName, String lName, String status, String major){
        entry=false;
        String str = "DELETE from Students WHERE ID = ?";
        try {
         // load database driver class
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           
         // connect to database
         Connection con =
                    DriverManager.getConnection("jdbc:odbc:StudentRegistration");
         
         //check passed in values against db values for a match
         Statement stmt = con.createStatement();
         System.out.println("Deleting student");
         String sID = Integer.toString(id);
         String first=fName;
         String last=lName;
         String stat=status;
         String maj=major;
         String rsCheck="";
         String check = first+" "+last+" "+stat+" "+maj;
         sqlFind = con.prepareStatement("SELECT * from Students WHERE FirstName = ?");
         sqlFind.setString(1, fName);
          ResultSet rs = sqlFind.executeQuery();
          while (rs.next()){
              String rsID = rs.getString("ID");
              String rsFName = rs.getString("FirstName");
              String rsLName = rs.getString("LastName");
              String rsDegreeStat = rs.getString("DegreeStatus");
              String rsMajor = rs.getString("Major");
              rsCheck = rsFName+" "+rsLName+" "+rsDegreeStat+" "+rsMajor;
              System.out.println(rsFName + " " + rsLName);
          }
          if(check.equalsIgnoreCase(rsCheck)){
              sqlFind = con.prepareStatement(str);
              sqlFind.setInt(1, id);
              System.out.println("Student deleted");
              sqlFind.executeUpdate();
              entry=true;
              setDBEntry(entry);
          }//end if
          else{
              entry=false;
              setDBEntry(entry);
          }//end else
        rs.close();
         stmt.close();
         con.close();
        }//end try
        catch(Exception e) 
        {
                e.printStackTrace();
        }//end catch
    }
    
    public void addStudent(int id, String fName, String lName, String status, String major){
        entry=false;
        try {
        // load database driver class
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        // connect to database
        Connection con = DriverManager.getConnection("jdbc:odbc:StudentRegistration");
        //check passed in values against db values for a match
        Statement stmt = con.createStatement();
        String sID = Integer.toString(id);
        String first=fName;
        String last=lName;
        String stat=status;
        String maj=major;
        String check = first+" "+last+" "+stat+" "+maj;
        String rsCheck="";
        sqlFind = con.prepareStatement("SELECT * from Students WHERE FirstName = ?");
        sqlFind.setString(1, fName);
        
        ResultSet rs = sqlFind.executeQuery();
        while (rs.next()){
          String rsID = rs.getString("ID");
          String rsFName = rs.getString("FirstName");
          String rsLName = rs.getString("LastName");
          String rsDegreeStat = rs.getString("DegreeStatus");
          String rsMajor = rs.getString("Major");
          rsCheck = rsFName+" "+rsLName+" "+rsDegreeStat+" "+rsMajor;
         
        }//end while   
          if(check.equalsIgnoreCase(rsCheck)){
              entry=false;
              setDBEntry(entry);
          }//end if
          else{
              String str="INSERT INTO Students VALUES (" + id + ",'" + fName +"','" + lName + "','" + status + "','"+major +"')";
             stmt.execute(str);
              entry=true;
              setDBEntry(entry);
          }//end else
        rs.close();
        stmt.close();
        con.close();
        System.out.println("Student added");
       }catch(ClassNotFoundException | SQLException e){}
    }
    
     //update XML file with updated databse entries
     public void updateStudentXml() throws ClassNotFoundException, SQLException, IOException, TransformerConfigurationException, TransformerException{
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection con = DriverManager.getConnection("jdbc:odbc:StudentRegistration");
        Statement stmt = con.createStatement();
        String fName ="" , lName ="", status="" , major ="";
        File file = new File("StudentRegistration.xml");
        file.delete();
        file = new File("StudentRegistration.xml");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        fw.write("");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("");
         ResultSet rs = stmt.executeQuery("SELECT * from Students");
            StringBuilder sqlXml = new StringBuilder();
            while (rs.next())
            {
              fName = rs.getString("FirstName");
              lName = rs.getString("LastName");
              status = rs.getString("DegreeStatus");
              major = rs.getString("Major");
            sqlXml.append ("\t<Student>");
            sqlXml.append("\n\t\t<fName>").append(fName).append ("</fName>\t");
            sqlXml.append("\n\t\t<lName>").append(lName).append ("</lName>\t");
            sqlXml.append("\n\t\t<status>").append(status).append ("</status>\t");
            sqlXml.append("\n\t\t<major>").append(major).append ("</major>\t");
            sqlXml.append ("\n\t</Student>\n");
            
            }//end while
        
        sqlXml.insert(0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sqlXml.insert(39,"<!DOCTYPE studentrecords SYSTEM \"Students.dtd\">\n");
        sqlXml.insert(88,"<studentrecords>\n");
        sqlXml.insert(sqlXml.length(),"</studentrecords>");
        bw.write(sqlXml.toString());
        
        //System.out.println( sqlXml );
        rs.close();
        bw.close();
        stmt.close();
        con.close();
    } 
     
  public void getStudentCounts(){//get major counts for pieChart 
     istCount=0;
     hdfsCount=0;
     nursingCount=0;
     busiCount=0; 
     String maj="";
      try {
         // load database driver class
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           
         // connect to database
         Connection con =
                    DriverManager.getConnection("jdbc:odbc:StudentRegistration");
         
         Statement stmt = con.createStatement();
         String str = ("SELECT Major FROM Students");
         
         ResultSet rs = stmt.executeQuery(str);
         while(rs.next()){
             //maj=rs.toString();
             maj=rs.getString("Major");
             System.out.println("Major = "+maj);
             if(maj.equalsIgnoreCase("IST")){
                 istCount++; 
                 setISTCount(istCount);
             }//end if ist
             else if(maj.equalsIgnoreCase("Nursing")){
                 nursingCount++;
                setNursingCount(nursingCount);}//end else nursing
             else if(maj.equalsIgnoreCase("HDFS")){
                 hdfsCount++;
                 setHDFSCount(hdfsCount); }//end else hdfs
             else if(maj.equalsIgnoreCase("Business")){
                 busiCount++;
                 setBusinessCount(busiCount);
             }//end else business
         }//end while
         rs.close();
         stmt.close();
         con.close();
        }//end try
        catch(Exception e) 
        {
                e.printStackTrace();
        }//end catch
  }//counts
  public void getStatusCounts(){//get degree status counts for bar chart
     ftCount=0;
     ptCount=0;
     ndCount=0;
     proCount=0; 
     String stat="";
      try {
         // load database driver class
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           
         // connect to database
         Connection con =
                    DriverManager.getConnection("jdbc:odbc:StudentRegistration");
         
         Statement stmt = con.createStatement();
         String str = ("SELECT DegreeStatus FROM Students");
         
         ResultSet rs = stmt.executeQuery(str);
         while(rs.next()){
             //maj=rs.toString();
             stat=rs.getString("DegreeStatus");
//             System.out.println("Major = "+maj);
             if(stat.equalsIgnoreCase("Full-Time")){
                 ftCount++; 
                 setFTCount(ftCount);
             }//end if full time
             else if(stat.equalsIgnoreCase("Part-Time")){
                 ptCount++;
                setPTCount(ptCount);}//end else part time
             else if(stat.equalsIgnoreCase("Non-Degree")){
                 ndCount++;
                 setNDCount(ndCount); }//end else non degree
             else if(stat.equalsIgnoreCase("Provisional")){
                 proCount++;
                 setProCount(proCount);
             }//end else provisional
         }//end while
         rs.close();
         stmt.close();
         con.close();
        }//end try
        catch(Exception e) 
        {
                e.printStackTrace();
        }//end catch
  }//counts
  
  public void setISTCount(int i){
      istCount=i;
  }
  public int getISTCount(){
      return istCount;
  }
  public void setNursingCount(int n){
      nursingCount=n;
  }
  public int getNursingCount(){
      return nursingCount;
  }
  public void setBusinessCount(int b){
      busiCount=b;
  }
  public int getBusinessCount(){
      return busiCount;
  }
  public void setHDFSCount(int h){
      hdfsCount=h;
  }
  public int getHDFSCount(){
      return hdfsCount;
  }

  
  public void setFTCount(int i){
      ftCount=i;
  }
  public int getFTCount(){
      return ftCount;
  }
  public void setPTCount(int n){
      ptCount=n;
  }
  public int getPTCount(){
      return ptCount;
  }
  public void setNDCount(int b){
      ndCount=b;
  }
  public int getNDCount(){
      return ndCount;
  }
  public void setProCount(int h){
      proCount=h;
  }
  public int getProCount(){
      return proCount;
  }
  
  public void setDBEntry(boolean db){
      entry=db;
  }
  public boolean getDBEntry(){
      return entry;
  }
}// end Database class
    

