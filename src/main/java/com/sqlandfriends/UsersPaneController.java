package com.sqlandfriends;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class UsersPaneController extends ParentPaneController {

    @FXML
    AnchorPane users;
    @FXML
    AnchorPane title;
    @FXML
    VBox cardHolder;
    
    
    @FXML
    private void initialize(){
        fxmlDetailPath = "fxml/proposalCard.fxml";
        setAll(cardHolder, users, title);
        setUserCards();
    }

    public void setUserCards(){
        try{
            
            ArrayList<User> users = DatabaseHandler.getAllUsers();
            
            for(User user: users){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/userCard.fxml"));
                AnchorPane root = (AnchorPane)loader.load();
                UsersCardController controller = loader.getController();
                controller.setUser(user);
                controller.setParentPaneController(this);
                cardHolder.getChildren().add(root);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void setAddUserPane(ActionEvent event) throws IOException{

        homeScreenController.toggleAddUserPane();
        
    }
    

    
}
