package com.example.springapp.dao;

import com.example.springapp.model.Course;
import com.example.springapp.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("database")
public class CourseDao {
    private final JdbcTemplate jdbcTemplate;

    public CourseDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public Course getCourse(int id) {
        final String sql = "SELECT * FROM courses where id =?;";
        return jdbcTemplate.queryForObject(sql, new CourseRowMapper(),id);
    }


    private static final class CourseRowMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet rs, int index) throws SQLException {
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setCourseName(rs.getString("courseName"));
            course.setInstructor(rs.getString("instructor"));
            course.setDescription(rs.getString("description"));
            return course;
        }
    }
}
