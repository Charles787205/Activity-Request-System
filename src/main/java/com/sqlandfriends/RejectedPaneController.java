package com.sqlandfriends;
import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class RejectedPaneController extends ParentPaneController{
    @FXML
    AnchorPane title;
    @FXML
    VBox cardHolder; 
    @FXML
    AnchorPane rejectedPane;
    User user;
    @FXML
    private void initialize(){
        
        fxmlDetailPath = "fxml/ReviewPane.fxml";
        setAll(cardHolder, rejectedPane, title);
        
    }
    private void getProposals(){
        addProposalCards(Proposal.REJECTED);
    }
    public void setUser(User user){
        super.setUser(user);
        getProposals();
    }
    

    
}
