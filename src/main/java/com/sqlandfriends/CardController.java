package com.sqlandfriends;
import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class CardController {
    
    @FXML
    Label cardTitle;
    @FXML
    Label userName;
    @FXML
    Label organization;
    @FXML
    Label time;
    ParentPaneController paneController;

    Proposal proposal;
    @FXML
    private void initialize(){

    }
    public void setPaneController(ParentPaneController controller){
        paneController = controller;
    }
    public void setLabel(Proposal proposal){
        this.proposal = proposal;
        cardTitle.setText(proposal.getTitle());
        userName.setText(proposal.getUserName());
        organization.setText(proposal.getOrganization());
        time.setText(proposal.getDate().toString());
    }
    public void clicked(MouseEvent event) throws IOException{
        if(paneController != null)
        paneController.toggleDetailPane(proposal);
    }
    
}
