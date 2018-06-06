package ua.nure.tur.db.dao.mysql;

import ua.nure.tur.db.dao.ReviewDAO;
import ua.nure.tur.entities.Review;
import ua.nure.tur.exceptions.DataAccessException;
import ua.nure.tur.utils.ClosingUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ua.nure.tur.utils.ClosingUtils.close;

public class MysqlReviewDAO implements ReviewDAO {

    private static final String GET_BY_PERIODICAL_ID = "select * from reviews where periodical_id=?";

    private static final String INSERT_REVIEW = "insert into reviews (score, message, creation_date, user_id, periodical_id)" +
            " values (?, ?, ?, ?, ?)";

    private static final String COUNT_FOR_PERIODICAL = "select count(*) from reviews where periodical_id=?";

    private static final String GET_BY_ID = "select * from reviews where id=?";

    private static final String DELETE = "delete from reviews where id=?";

    private Connection connection;

    public MysqlReviewDAO(Connection connection) {
        this.connection = connection;
    }

    private Review extractReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("id"));
        review.setScore(resultSet.getInt("score"));
        review.setMessage(resultSet.getString("message"));
        review.setCreationDate(resultSet.getDate("creation_date"));
        review.setUserId(resultSet.getLong("user_id"));
        review.setPeriodicalId(resultSet.getLong("periodical_id"));
        return review;
    }

    @Override
    public List<Review> findForPeriodical(Long id) throws DataAccessException {
        List<Review> reviews = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(GET_BY_PERIODICAL_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reviews.add(extractReview(resultSet));
            }
        } catch (SQLException e) {
            //TODO log
            System.out.println(e.getMessage());
            throw new DataAccessException("Failed getting reviews for periodical", e);
        }
        return reviews;

    }

    @Override
    public void create(Review review) throws DataAccessException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(INSERT_REVIEW);
            prepareInsert(statement, review);
            statement.executeUpdate();
        } catch (SQLException e) {
            //TODO log
            System.out.println(e.getMessage());
            throw new DataAccessException("Cannot add review", e);
        } finally {
            close(statement);
        }
    }

    @Override
    public int getAmountForPeriodical(long periodicalId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(COUNT_FOR_PERIODICAL);
            statement.setLong(1, periodicalId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(statement);
        }
        return 0;
    }

    @Override
    public Review findById(long reviewId) throws DataAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, reviewId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractReview(resultSet);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot get review", e);
        } finally {
            close(resultSet);
            close(statement);
        }
        return null;
    }

    @Override
    public void remove(long reviewId) throws DataAccessException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(DELETE);
            statement.setLong(1, reviewId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Cannot delete review", e);
        } finally {
            close(statement);
        }
    }

    private void prepareInsert(PreparedStatement statement, Review review) throws SQLException {
        int i = 1;
        statement.setInt(i++, review.getScore());
        statement.setString(i++, review.getMessage());
        statement.setDate(i++, review.getCreationDate());
        statement.setLong(i++, review.getUserId());
        statement.setLong(i, review.getPeriodicalId());
    }
}
