package com.naz_desu.sumato.api.student.ai_chat.dto;

public enum ChatRole {
    USER("user"),
    ASSISTANT("assistant");

    private final String role;

    ChatRole(String role) {
        this.role = role;
    }

    public static ChatRole from(String role) {
        return ChatRole.valueOf(role.toUpperCase());
    }

    public String getRole() {
        return role;
    }
}
