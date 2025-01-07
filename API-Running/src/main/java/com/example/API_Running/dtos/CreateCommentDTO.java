package com.example.API_Running.dtos;

public class CreateCommentDTO {
    private Long userId;
    private String content;

    public CreateCommentDTO(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
