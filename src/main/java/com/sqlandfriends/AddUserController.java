package com.sqlandfriends;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.text.DefaultStyledDocument.ElementSpec;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class AddUserController extends ParentPaneController {
    @FXML
    TextField firstNameField, middleNameField, lastNameField, emailField, mobileNumberField, programField, organizationField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button registerButton, deleteButton;
    @FXML
    ComboBox<String> positionBox, departmentBox, yearLevelBox;

    @FXML
    RadioButton csgButton, csoButton;

    ParentPaneController parentPaneController;

    TextField textFields[];
    ComboBox<String> comboBoxes[];
    RadioButton radioButton[];
    User user;
    @FXML
    private void initialize(){
        TextField tfs[] = {firstNameField, middleNameField, lastNameField, emailField, mobileNumberField, programField, organizationField, passwordField};
        ComboBox cbx[] = {positionBox, departmentBox, yearLevelBox};
        RadioButton rbs[] = {csgButton, csoButton};
        textFields = tfs;
        radioButton = rbs;
        comboBoxes = cbx;


        positionBox.getItems().addAll(User.POSITIONS);
        departmentBox.getItems().addAll(User.DEPARTMENT);
        yearLevelBox.getItems().addAll("1", "2", "3", "4", "5");
        organizationField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) {
                organizationField.setText(oldValue);
                organizationField.setStyle("-fx-border-color: green;");
            }
        });
    }

    public void viewUser(User user){
        deleteButton.setVisible(true);
        this.user = user;
        firstNameField.setText(user.getFirstName());
        

        middleNameField.setText(user.getMiddleName());
       

        lastNameField.setText(user.getLastName());
          

        mobileNumberField.setText(user.getMobileNumber());
        
        emailField.setText(user.getEmailAddress());
        programField.setText(user.getProgram());
        organizationField.setText(user.getOrganization());
        
        
        if(user.getClassification().equalsIgnoreCase("csg")){
            csgButton.setSelected(true);
        }else{
            csoButton.setSelected(true);
        }

        for(TextField textField: textFields){
            textField.setEditable(false);
        }
        for(ComboBox box: comboBoxes){
            box.setEditable(false);
            box.getItems().clear();
        }
        for(RadioButton button: radioButton){
            button.setDisable(true);
        }
        departmentBox.setValue(user.getDepartment());
        positionBox.setValue(user.getPosition());
        yearLevelBox.setValue(Integer.toString(user.getYearLevel()));
        registerButton.setVisible(false);
        
    }
   
    public void register(ActionEvent event) throws IOException{
       
        boolean isRadioButtonChecked = checkRadioButton(radioButton);
       boolean areBoxesFilled = checkComboBox(comboBoxes);
       boolean areTextFieldsFilled = checkTextField(textFields);
       System.out.println(isRadioButtonChecked);
       if(areBoxesFilled && areTextFieldsFilled){

           String firstName = firstNameField.getText();
           String middleName = middleNameField.getText();
           String lastName = lastNameField.getText();
           String email = emailField.getText();
           String mobileNumber = mobileNumberField.getText();
           String program = programField.getText();
           String organization = organizationField.getText();
           String position = positionBox.getValue();
           String department = departmentBox.getValue();
           String password = passwordField.getText();
           int yearLevel = Integer.parseInt(yearLevelBox.getValue());
           User user = new User(firstName, middleName, lastName, email, mobileNumber, position, yearLevel);


           user.setProgram(program);
           user.setOrganization(organization);
           user.setDepartment(department);
           user.setPassword(password);
           user.setClassification(csoButton.isSelected() ? "CSO" : "CSG");
           DatabaseHandler database = new DatabaseHandler();
           try{
               database.addUser(user);
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Success");
               alert.setHeaderText("New user added to the database");
               alert.showAndWait();
           }catch(Exception e){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
                
           }
           homeScreenController.toggleUsersPane(event);

       }

       

    }
    public void deleteUser(Event event) throws Exception{
        System.out.println(user.getId());
        DatabaseHandler.deleteUser(user);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("User deleted"); 
        alert.showAndWait();
        homeScreenController.toggleUsersPane(event);
    }

    
    public void checkOrganizationField(KeyEvent event) throws IOException{
        if(organizationField.getText().length() >= 10){
            organizationField.setText(organizationField.getText().substring(0,10));
            
        }
    }

    public static boolean checkTextField(TextField[] textFields){
        boolean isThereBlank = true;
        for(TextField textField : textFields){
            if(textField.getText().isBlank() && !textField.getId().equals("middleNameField")){
                
                textField.setStyle("-fx-border-color: red; -fx-border-radius: 2px;");
                isThereBlank = false;
            }else{
                textField.setStyle("-fx-border-color: none; -fx-border-radius: 2px;");    
            }
        }
        return isThereBlank;
    }
    public boolean checkRadioButton(RadioButton[] buttons){ // check if one of the buttons is selected
        boolean isSomethingSelected = false;
        for (RadioButton button: buttons){
            if(button.isSelected()){
                isSomethingSelected = true;
            }
        }
        
        for(RadioButton button: buttons){
            if(isSomethingSelected){
                button.getStyleClass().remove("radioButton");
                
            }else{
                button.getStyleClass().add("radioButton");
            }
        }
       
        return isSomethingSelected;
    }
    public boolean checkComboBox(ComboBox[] boxes){
        boolean isThereBlank = true;
        for(ComboBox box: boxes){
            if(box.getValue() == null ){
                box.setStyle("-fx-border-color: red; -fx-border-radius: 2px;");
                isThereBlank = false;
            }else{
                box.setStyle("-fx-border-color: none; -fx-border-radius: 2px;");
            }
        }
        return isThereBlank;
    }


    public void checkedRadioButton(ActionEvent event) throws IOException{
        RadioButton radioButton = (RadioButton)event.getSource();
        if(radioButton.getId().equals("csoButton")){
            csgButton.setSelected(false);
        }else{
            csoButton.setSelected(false);
        }
    }
}
