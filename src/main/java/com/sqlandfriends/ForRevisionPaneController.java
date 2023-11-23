package com.sqlandfriends;
import java.io.IOException;
import java.util.ArrayList;

import animatefx.animation.Tada;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ForRevisionPaneController extends ParentPaneController{

    @FXML
    AnchorPane title;
    @FXML
    VBox cardHolder;
    @FXML
    AnchorPane forRevisionPane;
    @FXML
    private void initialize(){
        fxmlDetailPath = "fxml/ReviewPane.fxml";
        setAll(cardHolder, forRevisionPane, title);
        
    }

    private void getProposals(){
        addProposalCards(Proposal.FOR_REVISION);
    }
    public void setUser(User user){
        super.setUser(user);
        getProposals();
    }

    
}