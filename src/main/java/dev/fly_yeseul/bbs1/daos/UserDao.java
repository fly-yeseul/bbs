package dev.fly_yeseul.bbs1.daos;

import dev.fly_yeseul.bbs1.entities.UserEntity;
import dev.fly_yeseul.bbs1.vos.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository(value = "dev.fly_yeseul.bbs1.daos.UserDao")

public class UserDao {// 다오에서는 웬만하면 sql 언어 써라
    private static final int DEFAULT_LEVEL = 9;

    private final DataSource dataSource;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insert(UserRegisterVo userRegisterVo) throws SQLException {
        int record; // 전역변수는 디폴트 0, 지역변수는 디폴트없으면 접근 못한다.
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO `spring2`.`users` (email, password, nickname, contact, `level`)" +
                    "VALUES (?, ?, ?, ?, ?);")) {
                preparedStatement.setString(1, userRegisterVo.getEmail());
                preparedStatement.setString(2, userRegisterVo.getPassword());
                preparedStatement.setString(3, userRegisterVo.getNickname());
                preparedStatement.setString(4, userRegisterVo.getContact());
                preparedStatement.setInt(5, UserDao.DEFAULT_LEVEL);
                record = preparedStatement.executeUpdate();
            }
        }       // 위에서 오류 터지면 그대로 throws 되기 때문에 괜찮다.// while이 돌게 되면 달라진다.
        return record == 1;
    }

    // TODO
    public void select(UserEntity userEntity) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT `index`          AS `index`,\n" +
                    "       `email`          AS `email`,\n" +
                    "       `password`       AS `password`,\n" +
                    "       `nickname`       AS `nickname`,\n" +
                    "       `contact`        AS `contact`,\n" +
                    "       `level`          AS `level`,\n" +
                    "       `registered_at`  AS `registeredAt`,\n" +
                    "       `modified_at`    AS `modifiedAt`,\n" +
                    "       `deleted_flag`   AS `isDeleted`,\n" +
                    "       `suspended_flag` AS `isSuspended`\n" +
                    "FROM `spring2`.`users`\n" +
                    "WHERE `email` = ? AND `password` = ?" +
                    "LIMIT 1;")) {
                preparedStatement.setString(1, userEntity.getEmail());
                preparedStatement.setString(2, userEntity.getPassword());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {      // 게시글/댓글처럼 몇개가 나올지 모르는 상황에서는 while을 쓴다.
                        userEntity.setIndex(resultSet.getInt("index"));
                        userEntity.setEmail(resultSet.getString("email"));
                        userEntity.setPassword(resultSet.getString("password"));
                        userEntity.setNickname(resultSet.getString("nickname"));
                        userEntity.setContact(resultSet.getString("contact"));
                        userEntity.setLevel(resultSet.getInt("level"));
                        userEntity.setRegisteredAt(resultSet.getDate("registeredAt"));
                        userEntity.setModifiedAt(resultSet.getDate("modifiedAt"));
                        userEntity.setIsdeleted(resultSet.getBoolean("isDeleted"));
                        userEntity.setIssuspended(resultSet.getBoolean("isSuspended"));
                    }
                }
            }
        }
        // 열 전부 셀렉트해서 userentity로 돌려주면 된다.
        // email/pw 만 있음.
    }

    public int selectEmailCount(UserEntity userEntity) throws SQLException {
        // UserRegisterVO : 회원가입시에만 쓸 수 있음
        int count;
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT COUNT(1) AS `count` FROM `spring2`.`users` WHERE `email`=? ")) {
                preparedStatement.setString(1, userEntity.getEmail());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int selectNicknameCount(UserEntity userEntity) throws SQLException {
        int count;
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT COUNT(1) AS `count` FROM `spring2`.`users` WHERE `nickname`=? ")) {
                preparedStatement.setString(1, userEntity.getNickname());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int selectContactCount(UserEntity userEntity) throws SQLException {
        int count;
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT COUNT(1) AS `count` FROM `spring2`.`users` WHERE `contact`=? ")) {
                preparedStatement.setString(1, userEntity.getContact());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }
}
