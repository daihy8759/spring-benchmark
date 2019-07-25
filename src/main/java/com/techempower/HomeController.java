package com.techempower;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 */
@RequestMapping
@RestController
public class HomeController {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String SELECT_WORLD = "SELECT id from world where id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("")
    public String index() throws JsonProcessingException {
        ObjectNode objectNode = jdbcTemplate.queryForObject(SELECT_WORLD, new Object[] {1},
                new RowMapper<ObjectNode>() {

                    @Override
                    public ObjectNode mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("id", rs.getInt(1));
                        return objectNode;
                    }

                });
        return mapper.writeValueAsString(objectNode);
    }

}
