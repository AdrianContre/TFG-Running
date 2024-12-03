package com.example.API_Running.dtos;

import com.example.API_Running.models.Comment;

import java.time.LocalDateTime;

public class CommentDTO {
    private UserDTO author;
    private String content;
    private LocalDateTime date;

    public CommentDTO(Comment comment) {
        this.author = new UserDTO(comment.getSender().getId(), comment.getSender().getName(), comment.getSender().getSurname(), comment.getSender().getUsername());
        this.content = comment.getContent();
        this.date = comment.getSendDate();
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
