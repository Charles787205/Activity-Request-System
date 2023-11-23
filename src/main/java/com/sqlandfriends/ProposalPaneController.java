package com.sqlandfriends;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ProposalPaneController extends ParentPaneController{
    
    UserHomeScreenController homeScreenController;
    @FXML
    VBox cardHolder;
    @FXML
    AnchorPane title, proposalPane;
    
    
    @FXML
    private void initialize(){
        fxmlDetailPath = "fxml/ReviewPane.fxml";
        setAll(cardHolder, proposalPane, title);
    }
    public void setHomeScreenController(HomeScreenController homeScreenController) {
        this.homeScreenController = (UserHomeScreenController)homeScreenController;
    }
    
    public void toggleAddProposalPane(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddProposalPane.fxml"));
        AnchorPane pane = (AnchorPane)loader.load();
        AddProposalController controller = loader.getController();
        controller.setUser(user);
        controller.setProposalPaneController(this);
        homeScreenController.mainPanel.setCenter(pane);
    }
    private void getProposals(){
        addProposalCards(Proposal.FOR_REVISION);
        addProposalCards(Proposal.PENDING);
    }
    public void setUser(User user){
        super.setUser(user);
        getProposals();
    }
}
