package service;

import dao.UserDao;
import domain.User;

public class UserService {
    public static User newUser(String email,String password) {
        UserDao userDao =  new UserDao();
        if (userDao.selectByEmail(email)==0) {
            userDao.insertNewUser(email, password);
            User user =new User();
            user=userDao.selectByEmailPass(email, password);
            return user;
        }
        User user = new User();
        user.setIdUser(-1);
        return user;
    }
    
    public static int selectUserByLoginAndPass(String login, String pass) {
        UserDao userDao = new UserDao();
        return userDao.selectByEmailPass(login, pass).getIdUser();
    }
}
