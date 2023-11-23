package com.sqlandfriends;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DatabaseHandler{
    
    static String mysqlDriver = "com.mysql.cj.jdbc.Driver";

    static String url = "jdbc:mysql://localhost:3306/activity_proposal_system";
    Connection con;
    public DatabaseHandler(){
        try{
            Class.forName(mysqlDriver);
            
            String[] auth = App.getSettings();
            if(!auth[0].isBlank() || !auth[1].isBlank()){
                con = DriverManager.getConnection(url, auth[0], auth[1]);

            }else{
                con = DriverManager.getConnection(url);
            }
            
            System.out.println("Connected");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException{
        Connection con;
        String user, password;
        user = App.user;
        password = App.password;
        if(user.isBlank() || password.isBlank()){
            con = DriverManager.getConnection(url);
            
        }
        else{
            con = DriverManager.getConnection(url, user, password);
        }
        return con;
    }

    public static  User verifyLogin(User user1) throws SQLException{
        Connection con = getConnection();
        String sql = String.format("SELECT " + 
        "User.email_address AS emailAddress, " +
        "User.user_password AS password, " +
        "User.id AS id, " +
        "User.first_name AS firstName, " +
        "User.last_name AS lastName, " +
        "User.middle_name AS middleName, " +
        "User.mobile_number AS mobileNumber, " +
        "User.position AS position, " +
        "Course.department AS department, " +
        "Course.year_level AS yearLevel, " +
        "Course.program AS program " +  
        "FROM User JOIN Course ON User.id = Course.user_id " +
        "WHERE email_address='%s' AND user_password = SHA2('%s', 256);",user1.getEmailAddress(),  user1.getPassword());
        ResultSet resultSet;
        
        Statement statement = con.createStatement();
        resultSet = statement.executeQuery(sql);
        
        if(resultSet.next()){
            String emailAddress = resultSet.getString("emailAddress");
            String password = resultSet.getString("password");
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String middleName = resultSet.getString("middleName");
            String mobileNumber = resultSet.getString("mobileNumber");
            String position = resultSet.getString("position");
            String department = resultSet.getString("department");
            int yearLevel = resultSet.getInt("yearLevel");
            String program = resultSet.getString("program");

           
            User user = new User(firstName, middleName, lastName, emailAddress, mobileNumber, position, yearLevel);
            user.setProgram(program);
            user.setOrganization(department);
            user.setDepartment(department); 
            user.setId(id);
            con.close();
            return user;

        }
        else{
            con.close();
            return null;
        }
    }
    public static ArrayList<User> getAllUsers() throws SQLException{
        Connection con = getConnection();
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT " + 
        "User.email_address AS emailAddress, " +
        "User.user_password AS password, " +
        "User.id AS id, " +
        "User.first_name AS firstName, " +
        "User.last_name AS lastName, " +
        "User.middle_name AS middleName, " +
        "User.mobile_number AS mobileNumber, " +
        "User.position AS position, " +
        "Course.department AS department, " +
        "Course.year_level AS yearLevel, " +
        "Course.program AS program, " +  
        "Group_Classification.classification AS classification,"+
        "Group_Classification.organization_name as organization " +
        "FROM User JOIN Course ON User.id = Course.user_id JOIN Group_Classification ON User.id = Group_Classification.user_id ORDER BY User.id DESC;";
        
        ResultSet resultSet;
        Statement statement = con.createStatement();
        resultSet = statement.executeQuery(sql);    
        while(resultSet.next()){
            String emailAddress = resultSet.getString("emailAddress");
            String password = resultSet.getString("password");
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String middleName = resultSet.getString("middleName");
            String mobileNumber = resultSet.getString("mobileNumber");
            String position = resultSet.getString("position");
            String department = resultSet.getString("department");
            int yearLevel = resultSet.getInt("yearLevel");
            String program = resultSet.getString("program");
            String classification = resultSet.getString("classification");

           
            User user = new User(firstName, middleName, lastName, emailAddress, mobileNumber, position, yearLevel);
            user.setProgram(program);
            user.setOrganization(department);
            user.setDepartment(department); 
            user.setClassification(classification);
            user.setId(id);
            users.add(user);
        }
      

        con.close();
        return users;
    }

    public static ArrayList<Proposal> getProposals(String status) throws SQLException{
        Connection con = getConnection();
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();
        ResultSet rs;
    
        String sql = "SELECT DISTINCT " +
       "Activity_Proposal.id AS proposalId, " +
        "Activity_Proposal.title AS proposalTitle, " +
        "Activity_Proposal.user_id AS userId," +
        "Activity_Proposal.file_location AS proposalFileLocation, " +
        "Activity_Proposal.submission_date AS proposalSubmissionDate, " +

        "User.first_name AS userFirstName, " +
        "User.middle_name AS userMiddleName, " +
        "User.last_name AS userLastName, " +
        "Course.department AS department, " +
        "Group_Classification.organization_name AS userOrganizationName, " +

        "Proposal_Status.remarks AS remarks," +
        "Proposal_Status.current_status AS proposalCurrentStatus, " +
        "Proposal_Status.status_time AS proposalTime " +
        "FROM " +
        "Activity_Proposal " +
        "JOIN User ON Activity_Proposal.user_id = User.id " +
        "JOIN Proposal_Status ON Activity_Proposal.id = Proposal_Status.activity_proposal_id " +
        "JOIN Group_Classification ON User.id = Group_Classification.user_id "+
        "JOIN Course ON User.id = Course.user_id ";
       if(!status.isBlank()){
            String filter = String.format("WHERE Proposal_Status.current_status = '%s' ", status);
            sql += filter;
       }
       sql += "ORDER BY Proposal_Status.status_time DESC;";

        Statement statement = con.createStatement();
        rs = statement.executeQuery(sql);
       
        while (rs.next()) {
            // Retrieve values from each column in the current row
            int id = rs.getInt("proposalId");
            String title =  rs.getString("proposalTitle");
            String firstName = rs.getString("userFirstName");
            String middleName = rs.getString("userMiddleName");
            String lastName = rs.getString("userLastName");
            String organizationName = rs.getString("userOrganizationName");
            Date submissionDate = rs.getDate("proposalSubmissionDate");
            String department = rs.getString("department");
            String fileLocation = rs.getString("proposalFileLocation");
            String currentStatus = rs.getString("proposalCurrentStatus");
            String remarks = rs.getString("remarks");
            Time time = rs.getTime("proposalTime");
            String userName;
            if(middleName == null || middleName.equals("null"))
                userName = String.format("%s %s", firstName, lastName);
            else
                userName = String.format("%s %s %s", firstName,middleName, lastName);
            Proposal proposal = new Proposal(title, userName, organizationName, submissionDate, department, fileLocation, currentStatus);
            proposal.setId(id);
            proposal.setRemarks(remarks);
            proposal.setTime(time);
            proposals.add(proposal);
            
        }
        System.out.println(proposals.size());
        con.close();
        return proposals;
    }


    public static ArrayList<Proposal> getProposals(String status, User user) throws SQLException{
        Connection con = getConnection();
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();
        ResultSet rs;
    
        String sql = "SELECT DISTINCT " +
       "Activity_Proposal.id AS proposalId, " +
        "Activity_Proposal.title AS proposalTitle, " +
        "Activity_Proposal.user_id AS userId," +
        "Activity_Proposal.file_location AS proposalFileLocation, " +
        "Activity_Proposal.submission_date AS proposalSubmissionDate, " +

        "User.first_name AS userFirstName, " +
        "User.middle_name AS userMiddleName, " +
        "User.last_name AS userLastName, " +
        "User.id AS userID," +
        "Course.department AS department, " +
        "Group_Classification.organization_name AS userOrganizationName, " +

        "Proposal_Status.remarks AS remarks," +
        "Proposal_Status.current_status AS proposalCurrentStatus, " +
        "Proposal_Status.status_time AS proposalTime " +
        "FROM " +
        "Activity_Proposal " +
        "JOIN User ON Activity_Proposal.user_id = User.id " +
        "JOIN Proposal_Status ON Activity_Proposal.id = Proposal_Status.activity_proposal_id " +
        "JOIN Group_Classification ON User.id = Group_Classification.user_id "+
        "JOIN Course ON User.id = Course.user_id ";
       if(!status.isBlank()){
            String filter = String.format("WHERE Proposal_Status.current_status = '%s' AND User.id = %d ", status, user.getId());
            sql += filter;
       }
       sql += "ORDER BY Proposal_Status.status_time DESC;";

        Statement statement = con.createStatement();
        rs = statement.executeQuery(sql);
       
        while (rs.next()) {
            // Retrieve values from each column in the current row
            int id = rs.getInt("proposalId");
            String title =  rs.getString("proposalTitle");
            String firstName = rs.getString("userFirstName");
            String middleName = rs.getString("userMiddleName");
            String lastName = rs.getString("userLastName");
            String organizationName = rs.getString("userOrganizationName");
            Date submissionDate = rs.getDate("proposalSubmissionDate");
            String department = rs.getString("department");
            String fileLocation = rs.getString("proposalFileLocation");
            String currentStatus = rs.getString("proposalCurrentStatus");
            String remarks = rs.getString("remarks");
            Time time = rs.getTime("proposalTime");
            String userName;
            int userId = rs.getInt("userID");
            if(middleName == null || middleName.equals("null"))
                userName = String.format("%s %s", firstName, lastName);
            else
                userName = String.format("%s %s %s", firstName,middleName, lastName);
            Proposal proposal = new Proposal(title, userName, organizationName, submissionDate, department, fileLocation, currentStatus);
            proposal.setId(id);
            proposal.setRemarks(remarks);
            proposal.setTime(time);
            proposals.add(proposal);
            proposal.setUserId(userId);
            
        }
        System.out.println(proposals.size());
        con.close();
        return proposals;
    }
    public static void changeProposalStatus(Proposal proposal, String status) throws SQLException{
        Connection con = getConnection();
        String sql = String.format("UPDATE Proposal_Status "+
        "SET current_status = '%s', remarks='%s', status_time=NOW() WHERE activity_proposal_id = '%d';", status, proposal.getRemarks(), proposal.getId());

        Statement statement = con.createStatement();
        statement.execute(sql);
        System.out.println(sql);
        con.close();
    }


    // INSERTIONS
    public void addUser(User user) throws SQLException{
        String sql = "INSERT INTO User (first_name, middle_name, last_name, email_address, mobile_number, user_password, position) " + 
                    "VALUES ('%s','%s','%s','%s','%s',SHA2('%s', 256),'%s');"; 
        sql = String.format(sql, user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getEmailAddress(), user.getMobileNumber(), user.getPassword(), user.getPosition());
        Statement statement = con.createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.executeQuery("SELECT id FROM User WHERE id = LAST_INSERT_ID();");
        if(resultSet.next())
        user.setId(resultSet.getInt("id"));
        addCourse(user);
        addGroupClassification(user);
    }
    public boolean addCourse(User user) throws SQLException{
        String sql  = String.format("INSERT INTO Course (user_id, program, department, year_level) VALUES (%d, '%s', '%s', %d);", user.getId(), user.getProgram(), user.getDepartment(), user.getYearLevel());
        Statement statement = con.createStatement();
        return statement.execute(sql);
    }
    public boolean addGroupClassification(User user) throws SQLException{
        String sql = String.format("INSERT INTO Group_Classification (user_id, classification, organization_name) VALUES (%s, '%s', '%s');", user.getId(), user.getClassification(), user.getOrganization());
        Statement statement = con.createStatement();
        return statement.execute(sql);
    }
    public static void addProposal(Proposal proposal) throws SQLException{
        Connection con = getConnection();
        System.out.println(proposal.getFileLocation());
        String sql = String.format("INSERT INTO Activity_Proposal (user_id, title, file_location)" +
                    "VALUES (%d, '%s', '%s');",proposal.getUserId(), proposal.getTitle(), proposal.getFileLocation());
        Statement statement = con.createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.executeQuery("SELECT id FROM Activity_Proposal WHERE id = LAST_INSERT_ID();");
        if(resultSet.next())
            proposal.setId(resultSet.getInt("id"));
        addProposalStatus(proposal.getId());
        con.close();
    }
    public static void addProposalStatus(int id) throws SQLException{
        Connection con = getConnection();
        String sql  = String.format("INSERT INTO Proposal_Status (activity_proposal_id, remarks, current_status, status_time)" +
                        "VALUES (%d, '', '%s', NOW());", id, Proposal.PENDING);
        Statement statement = con.createStatement();
        statement.execute(sql);
        con.close();
    }


    public static void deleteUser(User user) throws SQLException{
        Connection con = getConnection();
        String sql = String.format("DELETE FROM USER WHERE id = '%d'", user.getId());
        Statement statement = con.createStatement();
        statement.execute(sql);
    }
}