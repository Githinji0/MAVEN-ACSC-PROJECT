package srms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EnrollmentsController {

    @FXML private TableView<?> tableEnrollments;
    @FXML private TableColumn<?, ?> colStudent;
    @FXML private TableColumn<?, ?> colCourse;
    @FXML private TableColumn<?, ?> colGrade;
    @FXML private TableColumn<?, ?> colDate;

    @FXML
    private void addEnrollment() {
        System.out.println("Add Enrollment clicked");
    }

    @FXML
    private void deleteEnrollment() {
        System.out.println("Delete Enrollment clicked");
    }
}
