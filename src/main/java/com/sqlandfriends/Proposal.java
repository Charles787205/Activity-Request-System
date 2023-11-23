package com.sqlandfriends;
import java.sql.Time;
import java.util.Date;


public class Proposal {
    
    private int  id, userId;
    private String title, userName, organization, status,fileLocation, department, remarks;
    private Time time;
    public static String PENDING = "pending";
    public static String APPROVED = "approved";
    public static String FOR_REVISION = "for-revision";
    public static String REJECTED  = "rejected";

    private Date date;

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Proposal(int userId, String title, String fileLocation){
        this.userId = userId;
        this.title = title;
        this.fileLocation = fileLocation;
    }
    

    public Proposal(int id, int userId, String title, String fileLocation){
        
        this.userId = userId;
     
        this.fileLocation = fileLocation;
    }
    public Proposal(int id, String title, String userName, String organization, String status){
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.organization = organization;
        this.status = status;

    }
    public Proposal(String title, String userName, String organizationName, Date submissionDate, String department, String fileLocation, String status){
        this.title = title;
        this.userName = userName;
        this.organization = organizationName;
        this.date = submissionDate;
        this.department = department;
        this.fileLocation = fileLocation;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Time getTime() {
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
   
    public String getFileLocation() {
        return this.fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
