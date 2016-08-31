package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kartikey on 27/06/16.
 */
public class UserDao {
    public List<User> getAllUsers(){
        List<User> userList = null;
        try {
            File file = new File("Users.dat");
            if (!file.exists()) {
                User user = new User(1, "Mahesh", "Teacher");
                userList = new ArrayList<User>();
                userList.add(user);
                saveUserList(userList);
            }
            else{
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList = (List<User>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private void saveUserList(List<User> userList){
        try {
            File file = new File("Users.dat");
            FileOutputStream fos;

            fos = new FileOutputStream(file);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        UserDao userDao = new UserDao();
        List<User> users =  userDao.getAllUsers();
        for (User user: users)
            System.out.println(user.getId() + " " +user.getName() + " " + user.getProfession() );
    }
}
