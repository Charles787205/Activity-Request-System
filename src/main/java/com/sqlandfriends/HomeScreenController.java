package com.sqlandfriends;
import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import animatefx.animation.SlideInRight;
import javafx.scene.Node;

public class HomeScreenController {
	
	@FXML
	BorderPane mainPanel;
	@FXML
	ImageView sidePanelMenuButton;
	@FXML
	VBox sidePanel;
	@FXML
	TilePane tilePane;
	
	@FXML
	TableView<Proposal> tableProposal;
	@FXML
	Button reviewButton;
	@FXML
	AnchorPane title;
	@FXML
	AnchorPane pendingButton, approvedButton, forRevisionButton, rejectedButton, usersButton;
	@FXML
	Label pendingNotif, forRevisionNotif, nameLabel, departmentLabel, positionOrganizationLabel;
	AnchorPane approvedPane, forRevisionPane, pendingPane,rejectedPane, usersPane;
	
	Image closeImage;
	Image menuImage;
	Proposal selectedProposal = null;
	FXMLLoader approvedPaneLoader , forRevisionPaneLoader, pendingPaneLoader, rejectedPaneLoader, usersPaneLoader;
	User user;
	private int noOfPendings, noOfForRevisions;


	@FXML
	private void initialize() {
		//mainPanel.setLeft(null);
		try {

			pendingPaneLoader = new FXMLLoader(getClass().getResource("fxml/PendingPane.fxml"));
			pendingPane = (AnchorPane)pendingPaneLoader.load();
			System.out.println("sdfa");

			setNotifications();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUser(User user){
		this.user = user;
		nameLabel.setText(user.getLastName());
		departmentLabel.setText(user.getDepartment());
		positionOrganizationLabel.setText(user.getPosition() + "|" + user.getOrganization());
		togglePane(pendingPane, pendingPaneLoader, pendingButton);
	}

	
	

	

	public void togglePendingPane(MouseEvent event) throws IOException{
		pendingPaneLoader = new FXMLLoader(getClass().getResource("fxml/PendingPane.fxml"));
		pendingPane = (AnchorPane)pendingPaneLoader.load();
		togglePane(pendingPane, pendingPaneLoader, pendingButton);
	}

	public void toggleApprovedPane(MouseEvent event) throws IOException{
		approvedPaneLoader = new FXMLLoader(getClass().getResource("fxml/ApprovedPane.fxml"));
		approvedPane = (AnchorPane)approvedPaneLoader.load();
		togglePane(approvedPane, approvedPaneLoader, approvedButton);
	}
	public void toggleForRevisionPane(MouseEvent event) throws Exception{
		forRevisionPaneLoader = new FXMLLoader(getClass().getResource("fxml/ForRevisionPane.fxml"));
		forRevisionPane = (AnchorPane)forRevisionPaneLoader.load();
		togglePane(forRevisionPane, forRevisionPaneLoader, forRevisionButton);	
		setNotifications();
		
	}
	public void toggleRejectedPane(MouseEvent event) throws IOException{
		rejectedPaneLoader = new FXMLLoader(getClass().getResource("fxml/RejectedPane.fxml"));
		rejectedPane = (AnchorPane)rejectedPaneLoader.load();
		togglePane(rejectedPane, rejectedPaneLoader, rejectedButton);
	}

	public void toggleUsersPane(Event event) throws IOException{
		usersPaneLoader = new FXMLLoader(getClass().getResource("fxml/UsersPane.fxml"));
		usersPane = (AnchorPane)usersPaneLoader.load();
		togglePane(usersPane, usersPaneLoader, usersButton);
		
	}



	public void toggleAddUserPane() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddUserPane.fxml"));
        AnchorPane userPane = (AnchorPane)loader.load();
		AddUserController controller = (AddUserController)loader.getController();
		controller.setHomeScreenController(this);
		controller.setUser(user);
		togglePane(userPane, loader, null);
	}
	public void toggleAddUserPane(User viewedUser) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddUserPane.fxml"));
        AnchorPane userPane = (AnchorPane)loader.load();
		AddUserController controller = (AddUserController)loader.getController();
		controller.setHomeScreenController(this);
		controller.setUser(user);
		controller.viewUser(viewedUser);
		togglePane(userPane, loader, null);
	}




	public void togglePane(AnchorPane pane, FXMLLoader loader, AnchorPane button){
		setButtonsToNormal();
		if (button != null)
			button.setStyle("-fx-background-color:rgb(168, 4, 4);");
		ParentPaneController controller = loader.getController();
		controller.setHomeScreenController(this);
		controller.setUser(user);
		if(mainPanel.getCenter() != pane){
			SlideInRight animator = new SlideInRight(pane);
			mainPanel.setCenter(pane);
			animator.play();
		}else{
			
			controller.toggleTitle();
		}
	}
	public void setButtonsToNormal(){
		AnchorPane[] buttons = {pendingButton, approvedButton, forRevisionButton, rejectedButton,usersButton};
		for(AnchorPane button: buttons){
			button.setStyle("-fx-backgroud-color:maroon;");
		}
		
	}
	public void logOut(Event event) throws IOException{
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/LoginScreen.fxml"));
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setResizable(false);
		
		BorderPane pane = (BorderPane)parent;
		stage.setMinWidth(pane.getPrefWidth());
		stage.setMinHeight(pane.getPrefHeight());
		stage.setWidth(pane.getPrefWidth());
		stage.setHeight(pane.getPrefHeight());
		stage.setResizable(false);
		stage.centerOnScreen();
	}
	public void setNotifications() throws Exception{
		pendingNotif.setText(Integer.toString(noOfPendings));
		noOfPendings = DatabaseHandler.getProposals(Proposal.PENDING).size();
		noOfForRevisions = DatabaseHandler.getProposals(Proposal.FOR_REVISION).size();
		if(noOfForRevisions > 0){
			forRevisionNotif.setText(Integer.toString(noOfForRevisions)); 
			forRevisionNotif.setVisible(true);
		}
		else{
			forRevisionNotif.setVisible(false);
		}
		if(noOfPendings > 0){
			pendingNotif.setText(Integer.toString(noOfPendings)); 
			pendingNotif.setVisible(true);
		}
		else{
			pendingNotif.setVisible(false);
		}
	}
	
}
