package ru.otus.springlibrary.dto;

import lombok.Data;
import ru.otus.springlibrary.validation.ValidObjectId;

import javax.validation.constraints.NotEmpty;

@Data
public class BookDTO {

    private String id;

    @NotEmpty(message = "{book.title.not_empty}")
    private String title;

    @ValidObjectId(message = "{book.author.not_empty}")
    private String authorId;

    @ValidObjectId(message = "{book.genre.not_empty}")
    private String genreId;

}
