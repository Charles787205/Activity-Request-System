package com.sqlandfriends;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.dansoftware.pdfdisplayer.*;

import javafx.fxml.FXML;



public class ReviewPaneController {
    @FXML
    StackPane pdfHolderPane;
    @FXML
    Label proposalTitleLabel, forRevisionLabel;
    @FXML
    TextArea remarksField;
    @FXML
    AnchorPane approvedButton, rejectButton, reviseButton;
    
    Proposal proposal;
    ParentPaneController parentPaneController;
    PDFDisplayer displayer;
    User user;
    File file;
    @FXML
    private void initialize(){
        
        displayer = new PDFDisplayer();
        
        pdfHolderPane.getChildren().add(displayer.toNode());
        try{
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void setUser(User user){
        this.user = user;
        System.out.println(String.format("User: %d Proposal: %d", user.getId(), proposal.getUserId()));
        if(!user.getPosition().equalsIgnoreCase("director")){
            remarksField.setEditable(false);
            approvedButton.setVisible(false);
            reviseButton.setVisible(false);
            rejectButton.setVisible(false);
        }
        if(user.getId() == proposal.getUserId() && proposal.getStatus().equalsIgnoreCase(Proposal.FOR_REVISION)){
            reviseButton.setVisible(true);
            approvedButton.setVisible(true);
            ((Label)approvedButton.getChildren().get(1)).setText("Submit");
            approvedButton.setOnMouseClicked(e -> {
                try{
                    Path newFile = file.toPath();
                    Path oldFile = Paths.get(proposal.getFileLocation());
                    Files.copy(newFile, oldFile, StandardCopyOption.REPLACE_EXISTING);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Proposal Revision Submited");
                    alert.show();
                    DatabaseHandler.changeProposalStatus(proposal, Proposal.PENDING);
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }); 
            reviseButton.setOnMouseClicked(e-> {
                Stage stage = (Stage)(((Node) e.getSource()).getScene().getWindow());
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);
                
                if(file != null){
                    String extension = AddProposalController.getFileExtension(file);
                    if(extension.equalsIgnoreCase("pdf")){
                        try{
                            displayer.loadPDF(file);
                            this.file = file;
                            

                        }catch(Exception exception){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setHeaderText("File is not PDF");
                            alert.setTitle("File Error");
                        }
                        
                    }
                }
            });
        }
    }
    
    public void setProposal(Proposal proposal){
        this.proposal = proposal;
        proposalTitleLabel.setText(proposal.getTitle());
        setRemarks();
        File file = new File(proposal.getFileLocation());
        if(file.exists()){
            try{
                displayer.loadPDF(file);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("helloooooo" + file.getPath());
        }
        
    }
    public void setRemarks(){
        remarksField.setText(proposal.getRemarks());
       
        if(!proposal.getStatus().equals(Proposal.PENDING)){
            remarksField.setEditable(false);
            approvedButton.setVisible(false);
            reviseButton.setVisible(false);
            rejectButton.setVisible(false);
        }
    }
    public void setParentPaneController(ParentPaneController controller){
        parentPaneController = controller;
    }
    public void approveProposal(MouseEvent event) throws Exception{
        changeUpdateProposal(Proposal.APPROVED, "Proposal Approved");
        parentPaneController.homeScreenController.toggleApprovedPane(event);
    }
    public void rejectProposal(MouseEvent event) throws Exception{
        changeUpdateProposal(Proposal.REJECTED, "Proposal Rejected");
        parentPaneController.homeScreenController.toggleRejectedPane(event);
    }
    public void reviseProposal(MouseEvent event) throws Exception{
        changeUpdateProposal(Proposal.FOR_REVISION, "Proposal is now for revision");
        parentPaneController.homeScreenController.toggleForRevisionPane(event);

    }
    public void  changeUpdateProposal(String status, String popUpText) throws Exception{
        
        proposal.setRemarks(remarksField.getText());
        DatabaseHandler.changeProposalStatus(proposal, status);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(popUpText);
        alert.showAndWait();
    }
    
    

}

