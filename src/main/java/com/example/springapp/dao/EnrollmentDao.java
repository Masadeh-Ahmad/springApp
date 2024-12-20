package com.example.springapp.dao;

import com.example.springapp.model.Course;
import com.example.springapp.model.Enrollment;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("database")
public class EnrollmentDao {
    private final JdbcTemplate jdbcTemplate;

    public EnrollmentDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Enrollment> getForUser(int id) {
        final String sql = "SELECT * FROM enrollment WHERE user_id=?;";
        return jdbcTemplate.query(sql, new EnrollmentRowMapper(),id);
    }
    public List<Enrollment> getForCourse(int id) {
        final String sql = "SELECT * FROM enrollment WHERE course_id=?;";
        return jdbcTemplate.query(sql, new EnrollmentRowMapper(),id);
    }
    public Enrollment getById(int id) {
        final String sql = "SELECT * FROM enrollment WHERE id=?;";
        return jdbcTemplate.queryForObject(sql, new EnrollmentRowMapper(),id);
    }
    public float getAvg(int id){
        final String sql = "SELECT AVG(mark) FROM enrollment WHERE course_id=?;";
        return jdbcTemplate.queryForObject(sql, Float.class, id);
    }
    public float getMax(int id){
        final String sql = "SELECT MAX(mark) FROM enrollment WHERE course_id=?;";
        return jdbcTemplate.queryForObject(sql, Float.class, id);
    }
    public float getMin(int id){
        final String sql = "SELECT MIN(mark) FROM enrollment WHERE course_id=?;";
        return jdbcTemplate.queryForObject(sql, Float.class, id);
    }

    public float getMedian(int id){
        String sql = "SELECT COUNT(*) FROM enrollment WHERE course_id = ?;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        if (count % 2 == 0) {
            sql = "SELECT AVG(mark) FROM (SELECT mark FROM enrollment WHERE course_id = ? ORDER BY mark LIMIT " + (count / 2 - 1) + ", 2) AS tmp;";
            return jdbcTemplate.queryForObject(sql, Float.class, id);
        } else {
            sql = "SELECT mark FROM enrollment WHERE course_id = ? ORDER BY mark LIMIT " + (count / 2) + ", 1;";
            return jdbcTemplate.queryForObject(sql, Float.class, id);
        }
    }

    private static final class EnrollmentRowMapper implements RowMapper<Enrollment> {
        @Override
        public Enrollment mapRow(ResultSet rs, int index) throws SQLException {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(rs.getInt("id"));
            enrollment.setUserId(rs.getInt("user_id"));
            enrollment.setCourseId(rs.getInt("course_id"));
            enrollment.setMark(rs.getFloat("mark"));
            return enrollment;
        }
    }
}
