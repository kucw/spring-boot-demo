package com.kucw.dao;

import com.kucw.model.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDao {
    private static final BeanPropertyRowMapper<User> USER_MAPPER = new BeanPropertyRowMapper<>(User.class);

    private static final String SQL_INSERT = "INSERT INTO user (name, update_time) VALUES (:name, :updateTime)";
    private static final String SQL_USER_BY_ID = "SELECT id, name, update_time FROM user WHERE id = :id";

    @Autowired
    @Qualifier("demoJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Integer insertUser(User user) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getName());
        param.put("updateTime", user.getUpdateTime());
        return namedParameterJdbcTemplate.update(SQL_INSERT, param);
    }

    public User getUserById(Integer id) {
        List<User> userList = namedParameterJdbcTemplate.query(SQL_USER_BY_ID, Collections.singletonMap("id", id), USER_MAPPER);
        if (CollectionUtils.isNotEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

    public void print() {
        System.out.println("This is user dao");
    }
}
