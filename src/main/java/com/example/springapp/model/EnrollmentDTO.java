package com.example.springapp.model;

public class EnrollmentDTO {
    private int id;
    private int courseId;
    private String courseName;
    private String instructor;
    private float mark;

    public EnrollmentDTO(int id, int courseId, String courseName, String instructor, float mark) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
