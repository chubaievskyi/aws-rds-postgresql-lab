package com.chubaievskyi;

import com.chubaievskyi.validation.UserValidation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@UserValidation
public class User {

    private String name;
    private String eddr;
    private int count;
    private LocalDate createdAt;

    public User() {
    }

    public User(String name, String eddr, int count, LocalDate createdAt) {
        this.name = name;
        this.eddr = eddr;
        this.count = count;
        this.createdAt = createdAt;
    }
}