package srms.repo;

import srms.db.DB;
import srms.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {

    public Student save(Student s) throws SQLException {
        if (s.getId() == null) {
            // insert
            String sql = "INSERT INTO students (registration_number, first_name, last_name, email, date_of_birth, enrollment_date, department) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection c = DB.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, s.getRegistrationNumber());
                ps.setString(2, s.getFirstName());
                ps.setString(3, s.getLastName());
                ps.setString(4, s.getEmail());
                if (s.getDateOfBirth() != null) ps.setDate(5, Date.valueOf(s.getDateOfBirth())); else ps.setNull(5, Types.DATE);
                if (s.getEnrollmentDate() != null) ps.setDate(6, Date.valueOf(s.getEnrollmentDate())); else ps.setNull(6, Types.DATE);
                ps.setString(7, s.getDepartment());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) s.setId(rs.getInt(1));
                }
                return s;
            }
        } else {
            // update
            String sql = "UPDATE students SET registration_number=?, first_name=?, last_name=?, email=?, date_of_birth=?, enrollment_date=?, department=? WHERE id=?";
            try (Connection c = DB.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setString(1, s.getRegistrationNumber());
                ps.setString(2, s.getFirstName());
                ps.setString(3, s.getLastName());
                ps.setString(4, s.getEmail());
                if (s.getDateOfBirth() != null) ps.setDate(5, Date.valueOf(s.getDateOfBirth())); else ps.setNull(5, Types.DATE);
                if (s.getEnrollmentDate() != null) ps.setDate(6, Date.valueOf(s.getEnrollmentDate())); else ps.setNull(6, Types.DATE);
                ps.setString(7, s.getDepartment());
                ps.setInt(8, s.getId());
                ps.executeUpdate();
                return s;
            }
        }
    }

    public Optional<Student> findById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY registration_number";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setRegistrationNumber(rs.getString("registration_number"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setEmail(rs.getString("email"));
        Date dob = rs.getDate("date_of_birth");
        if (dob != null) s.setDateOfBirth(dob.toLocalDate());
        Date enr = rs.getDate("enrollment_date");
        if (enr != null) s.setEnrollmentDate(enr.toLocalDate());
        s.setDepartment(rs.getString("department"));
        return s;
    }
}
