package com.sqlandfriends;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;




/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static String user, password;
    
            
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("LoginScreen"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Activity Proposal Management System");
        
        Image image = new Image(getClass().getResource("img/icon.png").toURI().toString());
        stage.getIcons().add(image);
        stage.show();
        String[] auth = App.getSettings();
        user = auth[0];
        password = auth[1];
        
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" +fxml + ".fxml"));
        
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String[] getSettings() throws Exception{

        File file = new File("settings.txt");
        String user = "";
        String password = "";
        if(file.createNewFile()){
            System.out.println("sdf");
            FileWriter writer = new FileWriter("settings.txt");
            user = JOptionPane.showInputDialog(null, "Enter user");
            password = JOptionPane.showInputDialog(null, "Enter Password");
            writer.write(user+"\n");
            writer.write(password);
            writer.close();
        }else{
            
            Scanner sc = new Scanner(file);
            if (sc.hasNext())
                user = sc.nextLine();
            if (sc.hasNextLine())
                password = sc.nextLine();
        }
        String authentication[] = {user, password};
        
        return authentication;
    }

}