package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    @Query("select b.title, b.price from books as b where b.price not between :price and :price1")
    List<String> priceFind(BigDecimal price, BigDecimal price1);

    List<Book> findAllByCopiesIsLessThanAndEditionType(Integer copies, EditionType editionType);

    @Query("select b from books as b inner join b.author where lower(b.author.lastName) like :pattern%")
    List<Book> findAllBooksByAuthor(@Param(value = "pattern") String pattern);

    @Query("SELECT ba.firstName, ba.lastName, sum(b.copies) from books as b" +
            " inner join b.author as ba group by ba.firstName order by ba.firstName desc ")
    List<String> authorCopies();

    List<Book> findAllByTitle(String title);

    @Modifying
    @Transactional
    @Query("update books as b set b.copies = b.copies + :numberOfCopies where b.releaseDate > :date")
    void updateCopies(@Param(value = "numberOfCopies") int numberOfCopies,
                      @Param(value = "date") LocalDate date);
}
