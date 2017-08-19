package com.boomerang.careers.data;

import com.boomerang.careers.binding.AuthRequest;
import com.boomerang.careers.binding.AuthResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao {
  private static final Logger LOG = Logger.getLogger(UserDao.class);

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Value("${careers.sql.auth}")
  String sqlAuth;

  public UserDao() {
    LOG.info("userDao instantiated");
  }

  public boolean authenticate(final AuthRequest authRequest) {
    String xpass = jdbcTemplate.query(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement pstmnt = connection.prepareStatement(sqlAuth);
        pstmnt.setString(1, authRequest.getUsername());
        return pstmnt;
      }
    }, new ResultSetExtractor<String>() {
      @Override
      public String extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.next()) {
          return resultSet.getString(2);
        }
        return null;
      }
    });
    if (xpass != null) {
      String pass = Integer.toHexString(authRequest.getPassword().hashCode());
      return xpass.equals(pass);
    }
    return false;
  }
}
