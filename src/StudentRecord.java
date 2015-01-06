/*
 * StudentRecord.java
 *
 */

//The StudentRecord class contains information about each student. Instances of this class are created in the Applet. 
public class StudentRecord {

    private String firstName;       // first name of Student
    private String lastName;        // last name of Student
    private String degreeStatus;    // degree status of student
    private String major;           // student major
    private int sID;                 // StudentID, (contains position of student in the Array within the Applet)
    
    /** Creates a new instance of StudentRecord */
    public StudentRecord(int id ,String fName, String lName, String degStat, String maj) {
        sID = id;
        firstName = fName;
        lastName = lName;
        degreeStatus = degStat;
        major = maj;
    }
    
    
//ACCESSORS
    //retrieves first name
    public String getFirstName(){
        return firstName;
    }//end getFirstName()
    
    //retrieves last name
    public String getLastName(){
        return lastName;
    }//end getLastName()
    
    //retrieves degree status
    public String getDegreeStatus(){
        return degreeStatus;
    }//end getDegreeStatus()
    
    //retrieves major
    public String getMajor(){
        return major;
    }//end getMajor()
    
    public int getStudentID(){
        return sID;
    }//end getStudentID() 
    
//MUTATORS 
    //sets first name
    public void setFirstName(String fName){
        firstName = fName;
    }//end setFirstName()
    
    //sets last name
    public void setLastName(String lName){
        lastName = lName;
    }//end setLastName()
    
    //sets degree status
    public void setDegreeStatus(String degStat){
        degreeStatus = degStat;
    }//end setDegreeStatus()
    
    //sets major
    public void setMajor(String maj){
        major = maj;
    }//end setMajor()
    
    //sets Student ID, but it's really this students spot in the array
    public void setID(int id){
        sID = id;
    }//end setID()
   
    
    public String toString(){
        return sID + "    " + firstName + " " + lastName + "       " + degreeStatus + "         " + major;
    }
  
   
}//end StudentRecord Class
