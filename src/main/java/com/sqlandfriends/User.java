package com.sqlandfriends;
import javafx.beans.property.*;

public class User {
    private String emailAddress, password, firstName, lastName,middleName, mobileNumber, position, department, organization, program, classification;
    
    private int id, yearLevel;
    public String getProgram() {
        return this.program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
    public String getClassification() {
        return this.classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
    
    public static String[] POSITIONS = {"Director", "Adviser", "President", "Vice-President", "Secretary"};
    public static String[] DEPARTMENT = {"CCE","CSHE","CAFAE", "CASE", "CTE", "CAE", "CBAE", "CCJE", "CEE", "CHE" };


    public int getYearLevel() {
        return this.yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }
    

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public User(String firstName, String middleName, String lastName, String emailAddress, String mobileNumber,String position, int yearLevel){
        this.firstName =  firstName;
        this.middleName =  middleName;
        this.lastName =  lastName;
        this.mobileNumber =  mobileNumber;
        this.emailAddress =  emailAddress;
        this.position =  position;
        this.yearLevel = yearLevel;

    }
    public User(String emailAddress, String password){
        this.emailAddress =  emailAddress;
        this.password =  password;
    }


    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getFullName(){
        String fullName;
        if(middleName == null || middleName.equals("null"))
            fullName = String.format("%s %s", firstName, lastName);
        else
            fullName = String.format("%s %s %s", firstName, middleName, lastName);
        return fullName;
    }
    
}
