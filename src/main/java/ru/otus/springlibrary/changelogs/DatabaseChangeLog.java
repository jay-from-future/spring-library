package ru.otus.springlibrary.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DBRef;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        Document review0 = new Document().append("_id", ObjectId.get()).append("review", "It is my favourite book!");
        Document review1 = new Document().append("_id", ObjectId.get()).append("review", "Parents need to know that The Green Mile is a 1999 movie based on a Stephen King novel in which a newly arrived death-row inmate has a miraculous gift. In the film's most graphic scene, a man is brutally killed by a botched electric chair execution -- the man screams in excruciating pain as his skin visibly and audibly sizzles; comment is later made of how the smell of the execution will linger in the prison for a long time. A large man is shown sitting in a field with two dead little girls in both arms. Use of the \"N\" word, another racial slur, and the obsolete \"colored\" designation. Frequent profanity, including variations on \"f--k.\" In a tense standoff with a prisoner, one of the guards urinates himself. Themes of racism, criminal justice, capital punishment, miracles, and faith even in the direst environments, and the treatment of the elderly in contemporary society are conveyed throughout this movie, and should provoke discussion and debate between parents and mature teens.");
        Document review2 = new Document().append("_id", ObjectId.get()).append("review", "The movie focuses on the rape and murder of two little girls though it is only ever mentioned maybe 3 times in the 3 hour movie. The criminal who committed the crime leers at them in a flashback sequence at the end of the movie. There are numerous sexually graphic moments of dialogue including threats of same-sex rape and very crude references to sex acts. There is a naked male backside shown for a few seconds in a nonsexual situation. There is presence of a pornographic comic strip, the inside is shown depicting graphic sex for a second or two. There also appeared to be a porn mag, but I hardly caught it. I was screening the movie with intent to allow my 13 y/o watch it, but I have decided he needs to wait until 16.");
        Document review3 = new Document().append("_id", ObjectId.get()).append("review", "I just watched this film last night, and I was absolutely memorized. It is no doubt, my favourite film. It has got and extravagent storyline, brilliant acting and the script is awesome. Although, I don't think it is suitable for kids, unless they are very, very mature. There is one scene in which a certain character is executed, but a wet sponge was not placed on the victim's head, and so, he literally frazzled to death. There is just screaming for that whole 2 mins. Very graphic, I think that if you were to let your child watch this, definitely leave out that scene. Some other violence, including a man being shot several times, bloody marks on his chest, and there is also an upsetting scene in which the main character ( well, one of them), is executed. There are some sexual references, and a sex scene, although it is heard, for about 10 seconds, and not seen. Some swear words, f*** about 7 times, the word n*gg*r is used against John Coffey, mostly spoken by 'Wild Bill'. There are some references to a man raping and killing two little girls, but the actual rape is not seen. Viewers, even adults, will probably cry at this film, as it is extraordinary at really making you feel a part of it. I recommend for about teens, maybe younger if your child is really, really mature. Thanks for reading. :)");

        Document book = new Document()
                .append("_id", ObjectId.get())
                .append("title", "The Green Mile")
                .append("authors", Collections.singletonList(new DBRef("author", authorId)))
                .append("genres", Collections.singletonList(new DBRef("genre", genreId)))
                .append("reviews", List.of(review0, review1, review2, review3));

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

        Document review0 = new Document().append("_id", ObjectId.get()).append("review", "Most Java books on my shelf are great as reference resources. But, they're not the kind of books you would really want to sit down with and read from cover-to-cover. This book, \"SCJP Sun Certified Programmer for Java 6 Exam\" nicely fits in both categories, serving both as an excellent reference and as a book that I found enjoyable to read cover-to-cover. It definitely fills in all those potholes of understanding - things that you either knew you didn't fully understand or those topics where you simply didn't know what you didn't know. I find that I am also coming away with a greater appreciation for the genius of the Java language, as well as the general principles of OO.\n" +
                "I must also give the publisher, McGraw-Hill, kudos for caring enough to make the Kindle version, especially the code snippets, readable. McGraw-Hill was smart enough to publish the code snippets as text that can be sized, rather than as the microscopic images that are found in the technical books I've purchased from APress.");
        Document review1 = new Document().append("_id", ObjectId.get()).append("review", "I studied this book for the Sun (now Oracle) Certified Java Programmer SE 6 exam (Exam 310-065). I thought I knew how to program in Java before but I found that I learned quite a lot from the book. It goes through the subjects covered on the exam and explains them and gives sample code.\n" +
                "There were a few times when I thought it could have been better organized, but overall the organization was OK. The main drawback I found was that there seemed to be a few details it left out. There were a number of times as I was doing the practice questions that I came upon some rule that had not been covered in the text. I even rescheduled my test because I was missing so many of the questions. That is the main reason why I'm giving it four stars and not five. In their defense, it is difficult to write a text that exhaustively covers everything in a language as large as Java. I did pass the test, but I'm just wondering if I could have passed the test earlier and with less time spent studying had they explained a few things a little better.\n" +
                "Despite a few small flaws, I ended up passing the test with an 85%, 27% more than the required 58%. The only training I did was studying this book, doing the practice questions, writing lots of sample programs, and occasionally looking at the online API. Oh, and I did consult another book about threads but I ended up not needing to because the questions on threads on the exam were not very hard. So this book did it's job. The questions on the test were easier than the book's practice questions.\n" +
                "I would recommend that someone wanting to pass the exam get this book, read it, take notes, and do lots of little programs as the authors recommend in the forward. Start by copying the sample code in the book, then tinker with it to see what happens when you change this thing or that thing. Then write a few of your own programs, changing various parts to see how it changes the output. And then take all the sample questions at the end of the book, and then do the sample quizzes and tests on the CD. Start the quizzes several weeks before the test, not a week before like I did, so you can have plenty of time to go back and study the areas where you are weak. When you miss a question, find out why you missed it, and go back later after you have forgotten the specific answers and try it again. And don't stress too much because like I said I found the actual test to be easier than their practice questions. If you combine careful study of this book with writing code and doing the quizzes, you will almost certainly pass the test.");
        Document review2 = new Document().append("_id", ObjectId.get()).append("review", "As someone who considers themselves a pretty experienced Java developer, I have two comments on this book:\n" +
                "1. This WILL NOT teach you Java and should not be your introduction to Java. This does not teach you how to program.\n" +
                "2. This WILL prepare you for the test. I don't think I would have passed the test if not reading this book prior to taking the exam. They know the types of questions that will be asked and prepare you for those types of questions.\n" +
                "I definitely recommend this to someone who wants to pass the Oracle Certified Professional Programmer exam.\n" +
                "If you are wondering, I received an 80% on the exam. Not an amazing score, but definitely a solid passing mark. I read through the book once and after each chapter, did the chapter quiz. I didn't do any of the exams on the CD or any form of final study.");

        Document book = new Document()
                .append("_id", ObjectId.get())
                .append("title", "SCJP Sun Certified Programmer for Java 6 Exam 310-065")
                .append("authors", Collections.singletonList(new DBRef("author", authorId)))
                .append("genres", Collections.singletonList(new DBRef("genre", genreId)))
                .append("reviews", List.of(review0, review1, review2));

        db.getCollection("book").insertOne(book);
    }

    @ChangeSet(order = "004", id = "importMoreAuthorsAndGenres", author = "Grigorii Liullin")
    public void importMoreAuthorsAndGenres(MongoDatabase db) {

        String[] genres = new String[]{
                "Action and Adventure",
                "Anthology",
                "Classic",
                "Comic and Graphic Novel",
                "Crime and Detective",
                "Drama",
                "Fable",
                "Fairy Tale",
                "Fan-Fiction",
                "Fantasy",
                "Historical Fiction",
                "Horror",
                "Humor",
                "Legend",
                "Magical Realism",
                "Mystery",
                "Mythology",
                "Realistic Fiction",
                "Romance",
                "Satire",
                "Science Fiction (Sci-Fi)",
                "Short Story",
                "Suspense/Thriller",
                "Biography/Autobiography",
                "Essay",
                "Memoir",
                "Narrative Nonfiction",
                "Periodicals",
                "Reference Books",
                "Self-help Book",
                "Speech",
                "Textbook",
                "Poetry"
        };

        List<Document> genreDocuments = Arrays.stream(genres)
                .map(g -> new Document()
                        .append("_id", ObjectId.get())
                        .append("genre", g))
                .collect(Collectors.toList());

        db.getCollection("genre").insertMany(genreDocuments);

        String[] authors = new String[]{
                "Mitsuru Adachi",
                "Jirō Akagawa",
                "Horatio Alger",
                "Gosho Aoyama",
                "Hirohiko Araki",
                "Jeffrey Archer",
                "David Baldacci",
                "Enid Blyton",
                "Norman Bridwell",
                "Dan Brown",
                "Edgar Burroughs",
                "Erskine Caldwell",
                "Lewis Carroll",
                "Barbara Cartland",
                "Agatha Christie",
                "Tom Clancy",
                "Paulo Coelho",
                "Jackie Collins",
                "Robin Cook",
                "Catherine Cookson",
                "Patricia Cornwell",
                "John Creasey",
                "Michael Crichton",
                "Clive Cussler",
                "Roald Dahl",
                "Janet Dailey",
                "Frédéric Dard",
                "EL James",
                "Ian Fleming",
                "Ken Follett",
                "Anne Golon",
                "René Goscinny",
                "Zane Grey",
                "John Grisham",
                "Arthur Hailey",
                "Roger Hargreaves",
                "Hermann Hesse",
                "Eleanor Hibbert",
                "Evan Hunter",
                "Jin Yong",
                "Penny Jordan",
                "Judith Krantz",
                "Masashi Kishimoto",
                "Dean Koontz",
                "Louis L'Amour",
                "Astrid Lindgren",
                "Robert Ludlum",
                "Alistair MacLean",
                "Debbie Macomber",
                "Ann M. Martin",
                "Karl May",
                "Stephenie Meyer",
                "Seiichi Morimura",
                "Andrew Neiderman",
                "Nicholas Sparks",
                "Kyotaro Nishimura",
                "Eiichiro Oda",
                "Gilbert Patten",
                "James Patterson",
                "Beatrix Potter",
                "Alexander Pushkin",
                "Anne Rice",
                "Harold Robbins",
                "Nora Roberts",
                "Denise Robins",
                "Joanne  Rowling",
                "Richard Scarry",
                "William Shakespeare",
                "Sidney Sheldon",
                "Ryōtarō Shiba",
                "Georges Simenon",
                "Wilbur Smith",
                "Mickey Spillane",
                "Danielle Steel",
                "Rex Stout",
                "Rumiko Takahashi",
                "Corín Tellado",
                "Osamu Tezuka",
                "Tite Kubo",
                "Leo Tolstoy",
                "Akira Toriyama",
                "Yasuo Uchida",
                "Gérard Villiers",
                "Edgar Wallace",
                "Irving Wallace",
                "Cao Xueqin",
                "Eiji Yoshikawa"
        };

        List<Document> authorDocuments = Arrays.stream(authors)
                .map(a -> {
                    String[] nameParts = a.split(" ");
                    if (nameParts.length < 2) {
                        throw new IllegalArgumentException("Dataset is corrupted!");
                    }
                    String firstName = nameParts[0];
                    String lastName = nameParts[1];
                    return new Document()
                            .append("_id", ObjectId.get())
                            .append("first_name", firstName)
                            .append("last_name", lastName);
                })
                .collect(Collectors.toList());

        db.getCollection("author").insertMany(authorDocuments);

        List<Bson> pipeline = Collections.singletonList(new BasicDBObject("$project",
                new BasicDBObject("full_name", new BasicDBObject("$concat", Arrays.asList("$first_name", " ", "$last_name")))));
        db.createView("authorWithFullNamesView", "author", pipeline);
    }
}
