package com.detectiveos;

import com.detectiveos.config.DatabaseInitializer;
import com.detectiveos.config.JPAUtil;
import com.detectiveos.ui.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {
    @Override public void start(Stage stage) {
        try {
            DatabaseInitializer.seed();
            LoginView login = new LoginView(stage);
            stage.setTitle("Detective OS • Investigation Console");
            stage.setMinWidth(1180); stage.setMinHeight(760);
            stage.setScene(new Scene(login.root(), 1360, 860));
            stage.show();
        } catch (RuntimeException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Detective OS — Startup Error");
            alert.setHeaderText("The application could not connect to the database.");
            alert.setContentText("Start MySQL and verify src/main/resources/application.properties.\n\nDetails: " + ex.getMessage());
            alert.showAndWait();
            stop();
        }
    }
    @Override public void stop(){JPAUtil.close();}
    public static void main(String[] args){launch(args);}
}
