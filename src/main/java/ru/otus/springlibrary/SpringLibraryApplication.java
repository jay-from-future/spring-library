package ru.otus.springlibrary;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.springlibrary.dao.AuthorDaoImpl;
import ru.otus.springlibrary.domain.Author;

import java.sql.SQLException;

@SpringBootApplication
public class SpringLibraryApplication {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(SpringLibraryApplication.class, args);

        AuthorDaoImpl authorDao = context.getBean(AuthorDaoImpl.class);

        Author author = authorDao.insert(new Author("first", "last"));
        System.out.println(author);

        // jdbc:h2:mem:testdb
        Console.main(args);
    }

}
