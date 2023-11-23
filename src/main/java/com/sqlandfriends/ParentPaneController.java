package com.sqlandfriends;

import java.io.IOException;
import java.util.ArrayList;

import animatefx.animation.SlideInRight;
import animatefx.animation.Tada;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ParentPaneController {
    public HomeScreenController homeScreenController;
    protected AnchorPane myPane;
    protected String fxmlDetailPath;
    protected AnchorPane title;
    protected VBox cardHolder;
    public User user;
    

    public ParentPaneController(){

    }
    public void setAll(VBox v, AnchorPane mp, AnchorPane title){
       
        cardHolder = v;
        myPane = mp;
        this.title = title;
    }

    public void setUser(User user){
        this.user = user;
    }
    public void setHomeScreenController(HomeScreenController homeScreenController){
        this.homeScreenController = homeScreenController;
    }
    public void setCardHolder(VBox cardHolder){
        this.cardHolder = cardHolder;
    }
    public void setMyPane(AnchorPane myPane){
        this.myPane = myPane;
    }
    public void setTitle(AnchorPane title){
        this.title = title;
    }
    public AnchorPane getMyPane(){
        return myPane;
    }
    public HomeScreenController getHomeScreenController(){
        return homeScreenController;
    }
    public void toggleDetailPane(Proposal proposal) throws IOException{

        FXMLLoader loader = new FXMLLoader(HomeScreenController.class.getResource(fxmlDetailPath));
        Parent root = loader.load();
       
        Parent snippet = loader.getRoot();
        AnchorPane pane = (AnchorPane)snippet.lookup("#reviewPane");
        double width = myPane.getWidth();
		double height = myPane.getHeight();
        pane.setPrefSize(width, height);
        BorderPane mainPanel = (BorderPane)myPane.getParent();
        mainPanel.setCenter(pane);
        SlideInRight animator = new SlideInRight(pane);
        animator.play();

        ReviewPaneController controller = loader.getController();
        controller.setProposal(proposal);
        controller.setParentPaneController(this);
        controller.setUser(user);

    }
    public void toggleTitle(){
        Tada animator = new Tada(title);
        animator.play();
    }
    public void testCard(){
        for (int i = 0; i < 10; i++){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/proposalCard.fxml"));
            try{
                AnchorPane root = (AnchorPane)loader.load();
                CardController controller = loader.getController();
                controller.setPaneController(this);
                cardHolder.getChildren().add(root);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void addProposalCards(String proposalType){
        try{
            ArrayList<Proposal> proposals;
            
            if(!user.getPosition().equalsIgnoreCase("director")){
                proposals = DatabaseHandler.getProposals(proposalType,user);
            }else{
                proposals = DatabaseHandler.getProposals(proposalType);
            }
            for(Proposal proposal: proposals){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/proposalCard.fxml"));
                
                AnchorPane root = (AnchorPane)loader.load();
                CardController controller = loader.getController();
                controller.setPaneController(this);
                controller.setLabel(proposal);
                cardHolder.getChildren().add(root);
            
                
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
}
