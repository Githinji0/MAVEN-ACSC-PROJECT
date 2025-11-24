package srms.service;

import srms.model.Student;
import srms.repo.StudentRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentService {

    private final StudentRepository repo = new StudentRepository();

    public Student saveStudent(Student s) throws SQLException {
        // basic validation
        if (s.getRegistrationNumber() == null || s.getRegistrationNumber().isBlank())
            throw new IllegalArgumentException("Registration number required");
        if (s.getFirstName() == null || s.getFirstName().isBlank())
            throw new IllegalArgumentException("First name required");
        return repo.save(s);
    }

    public List<Student> findAll() throws SQLException {
        return repo.findAll();
    }

    public Optional<Student> findById(int id) throws SQLException {
        return repo.findById(id);
    }

    public void deleteById(int id) throws SQLException {
        repo.deleteById(id);
    }
}
