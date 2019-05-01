package ru.otus.springlibrary.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.DBRef;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collections;

@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "clean", author = "Grigorii Liullin", runAlways = true)
    public void clean(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "importSomeInitialData", author = "Grigorii Liullin")
    public void importSomeInitialData(MongoDatabase db) {
        ObjectId genreId = ObjectId.get();
        Document genre = new Document()
                .append("_id", genreId)
                .append("genre", "Dark Fantasy");

        db.getCollection("genre").insertOne(genre);

        ObjectId authorId = ObjectId.get();
        Document author = new Document()
                .append("_id", authorId)
                .append("first_name", "Stephen")
                .append("last_name", "King");

        db.getCollection("author").insertOne(author);

        Document review = new Document().append("_id", ObjectId.get()).append("review", "It's my favorite book!");

        Document book = new Document()
                .append("_id", ObjectId.get())
                .append("title", "The Green Mile")
                .append("authors", Collections.singletonList(new DBRef("author", authorId)))
                .append("genres", Collections.singletonList(new DBRef("genre", genreId)))
                .append("reviews", Collections.singletonList(review));

        db.getCollection("book").insertOne(book);
    }

    @ChangeSet(order = "003", id = "importSCJPBook", author = "Grigorii Liullin")
    public void importSCJPBook(MongoDatabase db) {
        ObjectId genreId = ObjectId.get();
        Document genre = new Document()
                .append("_id", genreId)
                .append("genre", "Programming");

        db.getCollection("genre").insertOne(genre);

        ObjectId authorId = ObjectId.get();
        Document author = new Document()
                .append("_id", authorId)
                .append("first_name", "Kathy")
                .append("last_name", "Sierra");

        db.getCollection("author").insertOne(author);

        Document review = new Document().append("_id", ObjectId.get()).append("review", "Really helpful for exam preparation.");

        Document book = new Document()
                .append("_id", ObjectId.get())
                .append("title", "SCJP Sun Certified Programmer for Java 6 Exam 310-065")
                .append("authors", Collections.singletonList(new DBRef("author", authorId)))
                .append("genres", Collections.singletonList(new DBRef("genre", genreId)))
                .append("reviews", Collections.singletonList(review));

        db.getCollection("book").insertOne(book);
    }
}
