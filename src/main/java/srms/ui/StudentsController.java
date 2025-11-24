package srms.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import srms.model.Student;
import srms.service.StudentService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StudentsController {

    @FXML
    private TextField regNoField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private DatePicker enrollPicker;
    @FXML
    private TextField departmentField;

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> colId;
    @FXML
    private TableColumn<Student, String> colRegNo;
    @FXML
    private TableColumn<Student, String> colFirstName;
    @FXML
    private TableColumn<Student, String> colLastName;
    @FXML
    private TableColumn<Student, String> colEmail;
    @FXML
    private TableColumn<Student, String> colDept;
    @FXML
    private TableColumn<Student, String> colEnrollDate;

    private final StudentService studentService = new StudentService();
    private final ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup column value factories
        colId.setCellValueFactory(cd -> new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getId()));
        colRegNo.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getRegistrationNumber()));
        colFirstName.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getFirstName()));
        colLastName.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getLastName()));
        colEmail.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getEmail()));
        colDept.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getDepartment()));
        colEnrollDate.setCellValueFactory(cd -> {
            LocalDate d = cd.getValue().getEnrollmentDate();
            return new javafx.beans.property.SimpleStringProperty(d == null ? "" : d.toString());
        });

        studentTable.setItems(students);

        // row selection -> populate fields
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFields(newSel);
            }
        });

        loadStudents();
    }

    private void populateFields(Student s) {
        regNoField.setText(s.getRegistrationNumber());
        firstNameField.setText(s.getFirstName());
        lastNameField.setText(s.getLastName());
        emailField.setText(s.getEmail());
        dobPicker.setValue(s.getDateOfBirth());
        enrollPicker.setValue(s.getEnrollmentDate());
        departmentField.setText(s.getDepartment());
    }

    @FXML
    private void handleNew() {
        studentTable.getSelectionModel().clearSelection();
        regNoField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        dobPicker.setValue(null);
        enrollPicker.setValue(null);
        departmentField.clear();
    }

    @FXML
    private void handleSave() {
        try {
            Student s = studentTable.getSelectionModel().getSelectedItem();
            boolean isNew = (s == null);
            if (isNew) {
                s = new Student();
            }

            s.setRegistrationNumber(regNoField.getText().trim());
            s.setFirstName(firstNameField.getText().trim());
            s.setLastName(lastNameField.getText().trim());
            s.setEmail(emailField.getText().trim());
            s.setDateOfBirth(dobPicker.getValue());
            s.setEnrollmentDate(enrollPicker.getValue() == null ? LocalDate.now() : enrollPicker.getValue());
            s.setDepartment(departmentField.getText().trim());

            Student saved = studentService.saveStudent(s);
            if (isNew) {
                students.add(saved); 
            }else {
                loadStudents();
            }

            showInfo("Saved", "Student saved successfully.");
            handleNew(); // clear form

        } catch (IllegalArgumentException ia) {
            showError("Validation", ia.getMessage());
        } catch (SQLException se) {
            showError("Database Error", se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            showError("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Student s = studentTable.getSelectionModel().getSelectedItem();
        if (s == null) {
            showInfo("Select", "Select a student to delete.");
            return;
        }
        boolean confirmed = confirm("Delete", "Delete student " + s.getRegistrationNumber() + "?");
        if (!confirmed) {
            return;
        }

        try {
            studentService.deleteById(s.getId());
            students.remove(s);
            handleNew();
            showInfo("Deleted", "Student deleted.");
        } catch (SQLException e) {
            showError("Database Error", e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try {
            List<Student> all = studentService.findAll();
            students.setAll(all);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database", "Failed to load students: " + e.getMessage());
        }
    }

    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setTitle(title);
        a.showAndWait();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setTitle(title);
        a.showAndWait();
    }

    private boolean confirm(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        a.setTitle(title);
        return a.showAndWait().filter(bt -> bt == ButtonType.YES).isPresent();
    }
}
