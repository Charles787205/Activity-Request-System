package com.sqlandfriends;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class UsersCardController {
    @FXML
    Label userName, emailAddress, department,yearLevel;
    ParentPaneController paneController;
    User user;

    @FXML 
    public void initialize(){

    }
    
    public void setParentPaneController(ParentPaneController paneController){
        this.paneController = paneController; 
    }
    public void setUser(User user){
        this.user = user;
        userName.setText(user.getFullName());
        emailAddress.setText(user.getEmailAddress());
        department.setText(user.getDepartment());
        yearLevel.setText(Integer.toString(user.getYearLevel()));
    }
    public void clicked(MouseEvent event) throws IOException{
        paneController.getHomeScreenController().toggleAddUserPane(user);
    }
}
