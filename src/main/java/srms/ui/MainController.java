package srms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

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

    @FXML
    public void showStudents() {
        loadPage("students.fxml");
    }

    @FXML
    public void showCourses() {
        loadPage("courses.fxml");
    }

    @FXML
    public void showEnrollments() {
        loadPage("enrollments.fxml");
    }

    @FXML
    public void showReports() {
        loadPage("reports.fxml");
    }
}
