package student;

import java.util.Locale;
import java.util.Objects;

public class Student {
    private String studentName;
    private int studentId;
    private String course;
    private String branch;

    public Student(int studentId, String studentName, String course, String branch) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course = course;
        this.branch = branch;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId && Objects.equals(studentName, student.studentName) && Objects.equals(course, student.course) && Objects.equals(branch, student.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, studentId, course, branch);
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "%-30d | %-30s | %-30s | %-30s",
                "Student Id: " + this.getStudentId(),
                "Student Name" + this.getStudentName(),
                "Visited: " + this.getCourse(),
                "Kilometers Required: " + this.getBranch() + " kms"
        );
    }
}
