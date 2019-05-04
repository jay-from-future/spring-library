package ru.otus.springlibrary.dto;

import lombok.Data;
import ru.otus.springlibrary.validation.ValidObjectId;

import javax.validation.constraints.NotBlank;

@Data
public class BookDTO {

    private String id;

    @NotBlank(message = "{book.title.not_empty}")
    private String title;

    @ValidObjectId(message = "{book.author.not_empty}")
    private String authorId;

    @ValidObjectId(message = "{book.genre.not_empty}")
    private String genreId;

}
