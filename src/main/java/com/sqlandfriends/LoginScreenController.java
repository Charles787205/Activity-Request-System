package com.sqlandfriends;

import java.io.IOException;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import animatefx.animation.*;
import javafx.scene.layout.BorderPane;

public class LoginScreenController {
	
	private BorderPane root;
	private Scene scene;
	private Stage stage;
	@FXML
	TextField emailAddressField;
	@FXML
	PasswordField passwordField;
	@FXML
	private void initialize(){
		
	}
	
	public void login(ActionEvent event) throws IOException {
		System.out.println("Hello");
		FXMLLoader loader;
		
		
		DatabaseHandler database = new DatabaseHandler();
		User user = new User(emailAddressField.getText(), passwordField.getText());
		try{
			System.out.print(emailAddressField.getText() + " " + passwordField.getText());
			User verifiedUser = database.verifyLogin(user);
			if(verifiedUser != null){

				if(!verifiedUser.getPosition().equals("Director")){
					loader = new FXMLLoader(LoginScreenController.class.getResource("fxml/UserHomeScreen.fxml"));
				}else{
					loader = new FXMLLoader(LoginScreenController.class.getResource("fxml/HomeScreen.fxml"));
				}
				root = (BorderPane)loader.load();
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				stage.getScene().setRoot(root);
				stage.setMinWidth(root.getPrefWidth());
				stage.setMinHeight(root.getPrefHeight());
				stage.setResizable(true);
				stage.centerOnScreen();
				stage.show();
				HomeScreenController hsc = loader.getController();
				hsc.setUser(verifiedUser);
				FadeIn animator = new FadeIn(root);
				animator.play();
				
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Wrong username/password");
				alert.show();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
	
}
