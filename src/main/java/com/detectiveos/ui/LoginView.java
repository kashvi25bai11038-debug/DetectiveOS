package com.detectiveos.ui;

import com.detectiveos.exception.AuthenticationException;
import com.detectiveos.model.User;
import com.detectiveos.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {
    private final Stage stage;
    private final StackPane root = new StackPane();
    private final AuthService authService = new AuthService();

    public LoginView(Stage stage) {
        this.stage = stage;
        root.getStyleClass().add("login-root");
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        VBox card = new VBox(16);
        card.setMaxWidth(470); card.setPadding(new Insets(42)); card.getStyleClass().add("login-card");
        Label eyebrow = new Label("CASE MANAGEMENT • EVIDENCE • REASONING"); eyebrow.getStyleClass().add("eyebrow");
        Label title = new Label("DETECTIVE OS"); title.getStyleClass().add("hero-title");
        Label subtitle = new Label("Interactive Crime Investigation & Evidence Analysis"); subtitle.getStyleClass().add("hero-subtitle");
        Separator separator = new Separator();
        TextField user = new TextField("detective"); user.setPromptText("Username"); user.setMaxWidth(Double.MAX_VALUE);
        PasswordField pass = new PasswordField(); pass.setText("detective123"); pass.setPromptText("Password"); pass.setMaxWidth(Double.MAX_VALUE);
        Button login = new Button("ENTER INVESTIGATION  →"); login.setMaxWidth(Double.MAX_VALUE); login.setDefaultButton(true); login.getStyleClass().add("primary-button");
        Label hint = new Label("Demo credentials are prefilled for the assessment build."); hint.getStyleClass().add("muted");
        Label error = new Label(); error.getStyleClass().add("error"); error.setWrapText(true);

        login.setOnAction(e -> {
            try { User u = authService.authenticate(user.getText(), pass.getText()); openDashboard(u); }
            catch (AuthenticationException ex) { error.setText(ex.getMessage()); }
            catch (RuntimeException ex) { error.setText("Database connection failed. Check SETUP.md and MySQL credentials."); }
        });
        pass.setOnAction(login.getOnAction());
        card.getChildren().addAll(eyebrow,title,subtitle,separator,user,pass,login,hint,error);
        root.getChildren().add(card);
    }

    private void openDashboard(User user) {
        DashboardView dashboard = new DashboardView(stage,user);
        stage.setScene(new Scene(dashboard.root(), 1360, 860));
    }
    public Parent root(){return root;}
}
