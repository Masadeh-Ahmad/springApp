package com.example.springapp.controller;

import com.example.springapp.dao.CourseDao;
import com.example.springapp.dao.EnrollmentDao;
import com.example.springapp.dao.UserDao;
import com.example.springapp.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserDao userDao;
    private final CourseDao courseDao;
    private final EnrollmentDao enrollmentDao;
    private User user;

    public ApiController(UserDao userDao, CourseDao courseDao, EnrollmentDao enrollmentDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
        this.enrollmentDao = enrollmentDao;
        user = null;
    }
    @PostMapping("/auth")
    public ResponseEntity<String> auth (@RequestBody Credentials credentials) {
       user = userDao.auth(credentials);

       if(user != null)
           return new ResponseEntity<>("ACCEPTED",HttpStatus.ACCEPTED);
       return new ResponseEntity<>("FORBIDDEN",HttpStatus.FORBIDDEN);
    }
    @GetMapping("/enrollment")
    public ResponseEntity<List<EnrollmentDTO>> enrollment(){
        if(user == null)
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        List<EnrollmentDTO>  enrollmentDTOS = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentDao.getForUser(user.getId());
        for (Enrollment enrollment : enrollments){
            Course course = courseDao.getCourse(enrollment.getCourseId());
            enrollmentDTOS.add(new EnrollmentDTO(enrollment.getId(),course.getId(),course.getCourseName(),course.getInstructor(),enrollment.getMark()));
        }
        return new ResponseEntity<>(enrollmentDTOS,HttpStatus.OK);
    }
    @GetMapping("/data")
    public ResponseEntity<Statistics> data(int id){
        Enrollment enrollment = enrollmentDao.getById(id);
        if(user == null || user.getId() != enrollment.getUserId())
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        float avg = enrollmentDao.getAvg(enrollment.getCourseId());
        float max = enrollmentDao.getMax(enrollment.getCourseId());
        float min = enrollmentDao.getMin(enrollment.getCourseId());
        float median = enrollmentDao.getMedian(enrollment.getCourseId());
        return new ResponseEntity<>( new Statistics(avg,max,min,median),HttpStatus.OK);
    }

}
