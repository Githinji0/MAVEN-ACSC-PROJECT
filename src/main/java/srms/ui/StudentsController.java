package srms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentsController {

    @FXML private TableView<?> tableStudents;
    @FXML private TableColumn<?, ?> colRegNo;
    @FXML private TableColumn<?, ?> colFirstName;
    @FXML private TableColumn<?, ?> colLastName;
    @FXML private TableColumn<?, ?> colEmail;

    @FXML
    public void initialize() {
        // TODO: Load student data later when DB is ready
    }

    @FXML
    private void addStudent() {
        System.out.println("Add Student clicked");
    }

    @FXML
    private void editStudent() {
        System.out.println("Edit Student clicked");
    }

    @FXML
    private void deleteStudent() {
        System.out.println("Delete Student clicked");
    }
}
