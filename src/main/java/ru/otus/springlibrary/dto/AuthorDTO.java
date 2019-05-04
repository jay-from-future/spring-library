package ru.otus.springlibrary.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthorDTO {

    @NotBlank(message = "{author.first_name.not_empty}")
    private String firstName;

    @NotBlank(message = "{author.last_name.not_empty}")
    private String lastName;
}
