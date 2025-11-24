package srms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent; // <--- FIX 1: Add this import

public class MainController {

    @FXML
    private StackPane contentPane;

    private void loadPage(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/" + fxml));
            Node page = loader.load();
            contentPane.getChildren().setAll(page);
        } catch (Exception e) {
            System.out.println("❌ Failed to load: " + fxml);
            e.printStackTrace();
        }
    }

    // FIX 2: All methods must now accept ActionEvent
    @FXML
    public void showStudents(ActionEvent event) { 
        loadPage("students.fxml");
    }

    @FXML
    public void showCourses(ActionEvent event) {
        loadPage("courses.fxml");
    }

    @FXML
    public void showEnrollments(ActionEvent event) {
        loadPage("enrollments.fxml");
    }

    @FXML
    public void showReports(ActionEvent event) {
        loadPage("reports.fxml");
    }
}