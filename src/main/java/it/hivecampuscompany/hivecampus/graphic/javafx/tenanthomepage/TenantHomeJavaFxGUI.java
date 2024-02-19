package it.hivecampuscompany.hivecampus.graphic.javafx.tenanthomepage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TenantHomeJavaFxGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/tenantHome-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 900,600);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }
}

