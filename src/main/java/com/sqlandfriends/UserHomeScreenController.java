package com.sqlandfriends;

import java.io.IOException;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class UserHomeScreenController extends HomeScreenController {
    
    @FXML
    AnchorPane proposalButton, rejectedButton, approvedButton;

    FXMLLoader proposalPaneLoader;
    AnchorPane proposalPane;
    @FXML
    private void initialize(){
        proposalPaneLoader = new FXMLLoader(getClass().getResource("fxml/ProposalPane.fxml"));
        try {
            proposalPane = (AnchorPane)proposalPaneLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void toggleProposalPane(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ProposalPane.fxml"));
        AnchorPane proposalPane = (AnchorPane)loader.load();
        togglePane(proposalPane, loader, proposalButton);
    }
    public void setButtonsToNormal(){
        approvedButton.setStyle("-fx-backgroud-color:maroon;");
        rejectedButton.setStyle("-fx-backgroud-color:maroon;");
        proposalButton.setStyle("-fx-backgroud-color:maroon;");
    }
    @Override
    public void setUser(User user){
        this.user = user;
        nameLabel.setText(user.getLastName());
		departmentLabel.setText(user.getDepartment());
		positionOrganizationLabel.setText(user.getPosition() + "|" + user.getOrganization());
        Event event = new Event(EventType.ROOT);
        togglePane(proposalPane, proposalPaneLoader, proposalButton);
    }
    public void toggleAddProposalPane(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddProposalPane.fxml"));
        AnchorPane pane = (AnchorPane)loader.load();
        togglePane(pane, loader, null);
    }

}   

