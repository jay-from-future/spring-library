package ru.otus.springlibrary.dto;

import lombok.Data;
import ru.otus.springlibrary.validation.ValidObjectId;

import javax.validation.constraints.NotEmpty;

@Data
public class ReviewDTO {

    @ValidObjectId
    private String bookId;

    @NotEmpty(message = "{review.not_empty}")
    private String review;
}
