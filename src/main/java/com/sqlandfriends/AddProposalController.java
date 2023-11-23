package com.sqlandfriends;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.dansoftware.pdfdisplayer.PDFDisplayer;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;


public class AddProposalController {
    
    @FXML
    TextField filePathField, titleField;
    @FXML
    StackPane pdfHolder;
    PDFDisplayer displayer;
    TextField[] textFields;
    User user;
    File file;
    ProposalPaneController proposalPaneController;
    @FXML
    private void initialize(){
        displayer = new PDFDisplayer();
        pdfHolder.getChildren().add(displayer.toNode());
       TextField[] tf = {filePathField, titleField};
       textFields = tf;
        
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setProposalPaneController(ProposalPaneController controller){
        proposalPaneController = controller;
    }
    
    public void openFileChooser(Event event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            String extension = getFileExtension(file);
            if (extension.equalsIgnoreCase("pdf")) {
                displayer.loadPDF(file);
                pdfHolder.setVisible(true);
                filePathField.setText(file.getPath());
                this.file = file;
            } else {
                pdfHolder.setVisible(false);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("File is not PDF");
                alert.setTitle("File Error");
                alert.show();
                
                
            }
        }
    }
    public  void submit(Event event) throws Exception{
        if(AddUserController.checkTextField(textFields)){
            String directory = String.format("data" + File.separator + "%s", user.getOrganization());
            Path source = Paths.get(file.getPath());
            Path targetDir = Paths.get(directory);
            Path target = targetDir.resolve(titleField.getText() +".pdf");

            File dir = new File(directory);
            
            if(!dir.exists()){
                dir.mkdirs();
            }
            Files.copy(source, target);
            Proposal proposal = new Proposal(user.getId(), titleField.getText(), target.toString().replaceAll("\\\\", "/"));
            DatabaseHandler.addProposal(proposal);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("File Uploaded"); 
            alert.setHeaderText("File Successfully Uploaded to Database");
            alert.showAndWait();
            proposalPaneController.homeScreenController.toggleProposalPane(event);
        }
    }
    public static String getFileExtension(File file) {
        String extension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
    }
}
