package dev.fly_yeseul.bbs1.daos;

import dev.fly_yeseul.bbs1.entities.BbsArticleEntity;
import dev.fly_yeseul.bbs1.entities.BbsBoardEntity;
import dev.fly_yeseul.bbs1.entities.BbsCommentEntity;
import dev.fly_yeseul.bbs1.vos.BbsArticleReadVo;
import dev.fly_yeseul.bbs1.vos.BbsBoardListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository(value = "dev.fly_yeseul.bbs1.daos.BbsDao")
public class BbsDao {

    private final DataSource dataSource;

    @Autowired
    public BbsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void selectArticle(BbsArticleReadVo bbsArticleReadVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(("" +
                    "SELECT `article`.`index`      AS `index`,\n" +
                    "       `article`.`board_id`   AS `boardId`,\n" +
                    "       `article`.`user_email` AS `userEmail`,\n" +
                    "       `article`.`title`      AS `title`,\n" +
                    "       `article`.`content`    AS `content`,\n" +
                    "       `article`.`written_at` AS `writtenAt`,\n" +
                    "       `article`.`view`       AS `view`,\n" +
                    "       `user`.`nickname`      AS `userNickname`\n" +
                    "\n" +
                    "FROM `spring2`.`articles` AS `article`\n" +
                    "         LEFT JOIN `spring2`.`users` AS `user` ON `article`.`user_email` = `user`.`email`\n" +
                    "WHERE `article`.`index` = ? \n" +
                    "LIMIT 1"))) {
                preparedStatement.setInt(1, bbsArticleReadVo.getIndex());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        bbsArticleReadVo.setIndex(resultSet.getInt("index"));
                        bbsArticleReadVo.setBoardId(resultSet.getString("boardId"));
                        bbsArticleReadVo.setUserEmail(resultSet.getString("userEmail"));
                        bbsArticleReadVo.setTitle(resultSet.getString("title"));
                        bbsArticleReadVo.setContent(resultSet.getString("content"));
                        bbsArticleReadVo.setWrittenAt(resultSet.getTimestamp("writtenAt"));
                        bbsArticleReadVo.setView(resultSet.getInt("view"));
                        bbsArticleReadVo.setUserNickname(resultSet.getString("userNickname"));
                    }
                }
            }

            // boardID 가 null 이 아니면 댓글도 불러와야한다.
            if (bbsArticleReadVo.getBoardId() != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "SELECT `comment`.`index`         AS `index`," +
                        "       `comment`.`article_index` AS `articleIndex`," +
                        "       `comment`.`user_email`    AS `userEmail`," +
                        "       `comment`.`content`       AS `content`," +
                        "       `comment`.`written_at`    AS `writtenAt`," +
                        "       `user`.`nickname`         AS `userNickname`" +
                        "FROM `spring2`.`comments` AS `comment`" +
                        "         LEFT JOIN `spring2`.`users` AS `user` ON `comment`.`user_email` = `user`.`email`" +
                        "WHERE `comment`.`article_index` = ? " +
                        "ORDER BY `comment`.`written_at` ASC")) {
                    preparedStatement.setInt(1, bbsArticleReadVo.getIndex());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            BbsCommentEntity commentEntity = new BbsCommentEntity(
                                    resultSet.getInt("index"),
                                    resultSet.getInt("articleIndex"),
                                    resultSet.getString("userEmail"),
                                    resultSet.getString("content"),
                                    resultSet.getTimestamp("writtenAt")
                            );
                            String userNickname = resultSet.getString("userNickname");
                            bbsArticleReadVo.getComments().add(commentEntity);
                            bbsArticleReadVo.getCommentUserNicknames().put(commentEntity.getIndex(), userNickname);
                        }
                    }

                    // 1. 위에서 선택한 게시글이 가지고 있는 댓글 전체 SELECT 하기(유저 닉네임도 같이)
                    // 2. while 반복문이 돌면 위 bbsArticleReadVo 객체가 가지고 있는 getComment() 랑 getCommentUserNickname() 메서드 호출해서 반환되는 ArrayList 및 HashMap 에 알맞은 값 추가하기.
                }
            }
        }
    }

    // 게시글 데이터 접근
    public void selectArticle(BbsBoardListVo bbsBoardListVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT `articles`.`index`      AS `index`," +
                    "       `articles`.`board_id`   AS `boardId`," +
                    "       `articles`.`user_email` AS `userEmail`," +
                    "       `articles`.`title`      AS `title`," +
//                    "       `articles`.`content`    AS `content`," +
                    // 메모리 낭비라서 지움
                    "       `articles`.`written_at` AS `writtenAt`," +
                    "       `articles`.`view`       AS `view`," +
                    "       `boards`.`name`         AS `boardName`," +
                    "       `user`.`nickname`       AS `userNickname`," +
                    "       COUNT(`comments`.`index`) AS `commentCount`" +
                    "FROM `spring2`.`articles` AS `articles`" +
                    "LEFT JOIN `spring2`.`boards` AS `boards` ON `articles`.`board_id` = `boards`.`id`" +
                    "LEFT JOIN `spring2`.`users` AS `user` ON `articles`.`user_email` = `user`.`email`" +
                    "LEFT JOIN `spring2`.`comments` AS `comments` ON `articles`.`index` = `comments`.`article_Index`" +
                    "WHERE `articles`.`board_id` = ?" +
                    "GROUP BY `articles`.`index`" +
                    "ORDER BY `articles`.`index` DESC " +
                    "LIMIT ? OFFSET ?")) {
                preparedStatement.setString(1, bbsBoardListVo.getId());
                preparedStatement.setInt(2, bbsBoardListVo.getArticleCountPerPage());
                preparedStatement.setInt(3, (bbsBoardListVo.getRequestPage() - 1) * bbsBoardListVo.getArticleCountPerPage());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        BbsArticleEntity article = new BbsArticleEntity();
                        article.setIndex(resultSet.getInt("index"));
                        article.setBoardId(resultSet.getString("boardId"));
                        article.setUserEmail(resultSet.getString("userEmail"));
                        article.setTitle(resultSet.getString("title"));
                        article.setWrittenAt(resultSet.getTimestamp("writtenAt"));
                        article.setView(resultSet.getInt("view"));
                        bbsBoardListVo.getArticles().add(article);
                        bbsBoardListVo.getArticleCommentCounts().put(
                                article.getIndex(),
                                resultSet.getInt("commentCount")
                        );
                        bbsBoardListVo.getArticleNicknames().put(
                                article.getIndex(),
                                resultSet.getString("userNickname")
                        );
                    }
                }

            }
        }
    }

    // 게시글 갯수 데이터 접근
    public int selectArticleCount(BbsBoardEntity bbsBoardEntity) throws SQLException {
        int count;
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT COUNT(1) AS `count`" +
                    "FROM `spring2`.`articles`" +
                    "WHERE `board_id` = ?")) {
                preparedStatement.setString(1, bbsBoardEntity.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }


    // 게시판 데이터 접근
    public void selectBoard(BbsBoardEntity boardEntity) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT `index`         AS `index`," +
                    "       `id`            AS `id`," +
                    "       `name`          AS `name`," +
                    "       `list_level`    AS `listLevel`," +
                    "       `write_level`   AS `writeLevel`," +
                    "       `read_level`    AS `readLevel`," +
                    "       `comment_level` AS `commentLevel`" +
                    "FROM `spring2`.`boards`" +
                    "WHERE `id` = ?" +
                    "LIMIT 1")) {
                preparedStatement.setString(1, boardEntity.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        boardEntity.setIndex(resultSet.getInt("index"));
                        boardEntity.setId(resultSet.getString("id"));
                        boardEntity.setName(resultSet.getString("name"));
                        boardEntity.setListLevel(resultSet.getInt("listLevel"));
                        boardEntity.setWriteLevel((resultSet.getInt("writeLevel")));
                        boardEntity.setReadLevel(resultSet.getInt("readLevel"));
                        boardEntity.setCommentLevel(resultSet.getInt("commentLevel"));
                    }
                }
            }
        }
    }



    public boolean writeComment(BbsCommentEntity bbsCommentEntity) throws SQLException {
        int record;
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO `spring2`.`comments`(`article_index`, `user_email`, `content`, `written_at`)\n" +
                    "VALUES (?, ?, ?, ?);")) {
                preparedStatement.setInt(1, bbsCommentEntity.getArticleIndex());
                preparedStatement.setString(2, bbsCommentEntity.getUserEmail());
                preparedStatement.setString(3, bbsCommentEntity.getContent());
                preparedStatement.setDate(4, (Date) bbsCommentEntity.getWrittenAt());
                record = preparedStatement.executeUpdate();
            }
            return record == 1;
        }
    }

}

