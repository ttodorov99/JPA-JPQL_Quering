package bookshopsystemapp.controller;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Controller
public class BookshopController implements CommandLineRunner {

    private final Scanner INPUT = new Scanner(System.in);
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... strings) throws Exception {

        while (true) {

            String option = INPUT.nextLine();

            if(option.toLowerCase().equals("exit")){
                break;
            }

            switch (option.toLowerCase()) {

                case "seed":
                    seed();
                    break;
                case "find by age restriction":
                    findByAgeRestriction();
                    break;

                case "find by copies and edition type":

                    printList(this.bookService.getAllBookTitlesWithLessThanCopiesAndGoldenEdition());
                    break;
                case "price":
                    printList(this.bookService.priceBook());
                    break;
                case "search author":
                    searchAuthors();
                    break;
                case "search book by author":
                    getBookTitleByAuthorLastName();
                    break;
                case "count":
                    printList(this.bookService.authorCopies());
                    break;
                case "title":
                    findByTitle();
                    break;
                case "update":
                   update();
                    break;
            }
        }


    }

    private void update() {
        int copies = INPUT.nextInt();

        String date = INPUT.next();

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        this.bookService.updateBookCopies(copies, localDate);
    }

    private void findByTitle() {
        String title = INPUT.nextLine();
        printList(this.bookService.findAllBooksByTitle(title));
    }

    private void getBookTitleByAuthorLastName() {
        String pattern = INPUT.nextLine().toLowerCase();
        printList(this.bookService.getBookTitleByAuthorLastName(pattern));
    }

    private void searchAuthors() {
        String pattern = INPUT.nextLine();
        printList(this.authorService.searchForAuthors(pattern));
    }

    private void printList(List<String> allBookTitlesWithLessThanCopiesAndGoldenEdition) {
        allBookTitlesWithLessThanCopiesAndGoldenEdition
                .forEach(System.out::println);
    }

    private void findByAgeRestriction() {
        String ageRestriction = INPUT.nextLine().toUpperCase();

        AgeRestriction ageRestriction1 = AgeRestriction.valueOf(ageRestriction);

        List<String> bookTitles = this.bookService.getAllBookTitles(ageRestriction1);

        printList(bookTitles);
    }

    private void seed() throws IOException {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
    }
}
