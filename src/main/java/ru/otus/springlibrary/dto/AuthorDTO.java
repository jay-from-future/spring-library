package ru.otus.springlibrary.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthorDTO {

    @NotEmpty(message = "{author.first_name.not_empty}")
    private String firstName;

    @NotEmpty(message = "{author.last_name.not_empty}")
    private String lastName;
}
