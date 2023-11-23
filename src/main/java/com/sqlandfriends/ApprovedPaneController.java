package com.sqlandfriends;

import java.util.ArrayList;

import animatefx.animation.Wobble;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
public class ApprovedPaneController extends ParentPaneController{
    @FXML
    AnchorPane title;
    @FXML
    VBox cardHolder;
    @FXML
    AnchorPane approvedPane;
    @FXML
    private void initialize(){
        fxmlDetailPath = "fxml/ReviewPane.fxml";
        setAll(cardHolder, approvedPane, title);
        
    }

    private void getProposals(){
        addProposalCards(Proposal.APPROVED);
    }

    public void setUser(User user){
        super.setUser(user);
        getProposals();
    }

}