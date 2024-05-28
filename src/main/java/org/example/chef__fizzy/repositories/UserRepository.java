package org.example.chef__fizzy.repositories;

import org.example.chef__fizzy.exception.UserUpdateFailedException;
import org.example.chef__fizzy.models.User;

import java.sql.*;

public class UserRepository {


    public static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/chef__fizzy";
        String user = "postgres";
        String password = "08027146369Aos@@@";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User saveUser(User user) {
        String getIdSqlStatement = "select  count(*) from users";
        String sql = "insert into users (id,wallet_id)values (?, ?)";
        try(Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(getIdSqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long currentId = resultSet.getLong(1);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,currentId+1);
            preparedStatement.setObject(2, user.getWalletId());
            preparedStatement.execute();
            return getUserBy(currentId+1);
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to connect to database");
        }
    }


    private User getUserBy(Long id){
        String sql = "select * from users where id=?";
        try(Connection conn = connect()){
            var preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long userId = resultSet.getLong(1);
            long walletId = resultSet.getLong(2);
            User user = new User();
            user.setId(userId);
            user.setWalletId(walletId);
            return user;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed To Connect to database");
        }

    }

    public User updateUser(Long userId, Long walletId){
        try(Connection connection = connect()){
            String sql = "UPDATE users SET wallet_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, walletId);
            statement.setLong(2, userId);
            statement.executeUpdate();
            return getUserBy(userId);
        } catch (SQLException e){
            throw new UserUpdateFailedException(":(");
        }
    }
}
