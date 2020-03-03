package dao;
import java.sql.ResultSet;

import com.mysql.cj.jdbc.CallableStatement;

import DB.MyConnection;
import domain.User;

public class UserDao {

    public int selectByEmail(String email) {
        String sql="call alpha.selectUserByEmail('"+email+"');";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                return 1;
            }
            return 0;
        }catch (Exception e) {
            System.out.println("Exception in DaoUser selectByEmail");
            System.out.println(e.getMessage());
            return -1;
        }
    }
    
    public User selectByEmailPass(String email, String password) {
        String sql="call alpha.selectUserByEmailAndPass('"+ email+"', '"+password+"');";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setIdUser(resultSet.getInt("id_user"));
                user.setEmailUser(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setIdUser(resultSet.getInt("id_user"));
                return user;
            }
            user.setIdUser(-1);
            return user;
        }catch (Exception e) {
            System.out.println("Exception in DaoUser selectByEmailPass");
            System.out.println(e.getMessage());
            return new User();
        }
    }
    
    public void insertNewUser(String email, String password) {
        String sql="call alpha.insertNewUser('"+email+"', '"+password+"');";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            callableStatement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Exception in DaoUser insertNewUser");
            System.out.println(e.getMessage());
        }
    }
}
