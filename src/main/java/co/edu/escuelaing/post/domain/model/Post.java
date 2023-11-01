package co.edu.escuelaing.post.domain.model;

import java.sql.Timestamp;

public class Post {
    private long id;
    private long userId;
    private String message;
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
