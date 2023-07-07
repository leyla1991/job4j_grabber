package ru.job4j.gc.leak;

import java.util.List;
import java.util.Objects;

public class Post {

    private int id;

    private String text;

    private List<Comment> commentList;

    public Post(int id, String text, List<Comment> commentList) {
        this.id = id;
        this.text = text;
        this.commentList = commentList;
    }

    public Post(String text, List<Comment> commentList) {
        this.text = text;
        this.commentList = commentList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(text, post.text) && Objects.equals(commentList, post.commentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, commentList);
    }
}
