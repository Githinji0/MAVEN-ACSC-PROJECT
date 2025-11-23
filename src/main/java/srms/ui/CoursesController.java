package srms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CoursesController {

    @FXML private TableView<?> tableCourses;
    @FXML private TableColumn<?, ?> colCode;
    @FXML private TableColumn<?, ?> colTitle;
    @FXML private TableColumn<?, ?> colCredits;

    @FXML
    public void initialize() {}

    @FXML
    private void addCourse() {
        System.out.println("Add Course clicked");
    }

    @FXML
    private void editCourse() {
        System.out.println("Edit Course clicked");
    }

    @FXML
    private void deleteCourse() {
        System.out.println("Delete Course clicked");
    }
}
