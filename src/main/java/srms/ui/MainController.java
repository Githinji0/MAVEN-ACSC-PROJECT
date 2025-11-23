package srms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane contentPane;

    // Load FXML pages into the content pane
    private void loadPage(String fxml) {
        try {
            Node page = FXMLLoader.load(getClass().getResource("/ui/" + fxml));
            contentPane.getChildren().setAll(page);
        } catch (Exception e) {
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
