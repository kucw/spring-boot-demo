package com.kucw.dao;

import com.kucw.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDao {

    private static final BeanPropertyRowMapper<Student> STUDENT_MAPPER = new BeanPropertyRowMapper<>(Student.class);

    private static final String SQL_INSERT = "INSERT INTO student(name, age) VALUES(:name, :age)";
    private static final String SQL_GET_BY_ID = "SELECT id, name, age FROM student WHERE id = :id";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int insert(Student student) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", student.getName());
        paramMap.put("age", student.getAge());

        // 插入後自動返回此record的id
        String[] keyColumnNames = new String[]{"id"};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL_INSERT, new MapSqlParameterSource(paramMap), keyHolder, keyColumnNames);
        return keyHolder.getKey().intValue();
    }

    public Student getById(Integer id) {
        List<Student> list = jdbcTemplate.query(SQL_GET_BY_ID, Collections.singletonMap("id", id), STUDENT_MAPPER);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
