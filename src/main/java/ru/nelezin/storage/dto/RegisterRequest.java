package ru.nelezin.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterRequest {

    private String login;

    private String password;

    private String repeatPassword;
}
