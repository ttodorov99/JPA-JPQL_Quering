package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.AgeRestriction;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<String> getAllBookTitles(AgeRestriction ageRestriction);

    List<String> getAllBookTitlesWithLessThanCopiesAndGoldenEdition();

    List<String> priceBook();

    List<String> getBookTitleByAuthorLastName(String pattern);

    List<String> authorCopies();

    List<String> findAllBooksByTitle(String title);

    void updateBookCopies(int copies, LocalDate date);
}
