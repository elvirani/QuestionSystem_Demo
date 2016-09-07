package pojo;

import java.io.Serializable;

/**
 * Created by yanni on 2016/9/4.
 */
public class User implements Serializable{
    String username;
    String password;
    String nickname;

    public User() {
    }

    public User(String username, String password,String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
