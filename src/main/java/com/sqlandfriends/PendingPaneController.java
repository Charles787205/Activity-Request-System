package com.sqlandfriends;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

import animatefx.animation.*;;
public class PendingPaneController extends ParentPaneController{
    
    @FXML
    AnchorPane title;
    @FXML
    AnchorPane pendingPane;
    @FXML
    VBox cardHolder;
    Proposal selectedProposal = null; 
    
    @FXML
    private void initialize(){
        fxmlDetailPath = "fxml/ReviewPane.fxml";
        setAll(cardHolder, pendingPane, title);
        
    }

    private void getProposals(){
        addProposalCards(Proposal.PENDING);
    }
    public void setUser(User user){
        super.setUser(user);
        getProposals();
    }

    

    

    
    
    
}
