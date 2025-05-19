package model;

import com.badlogic.gdx.graphics.Texture;

public class Account {
    private static record QuestionAnswerPair(String question, String answer){};
    private String username;
    private String password;
    private Texture avatar;
    private QuestionAnswerPair securityQuestion;
    private int runes;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
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
}
