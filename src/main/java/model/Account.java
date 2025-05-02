package model;

import com.badlogic.gdx.graphics.Texture;

public class Account {
    private static record QuestionAnswerPair(String question, String answer){};
    private String username;
    private String password;
    private Texture avatar;
    private QuestionAnswerPair securityQuestion;
    private int runes;
}
