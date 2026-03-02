package bookshopsystemapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AuthorService {

    void seedAuthors() throws IOException;

    List<String> searchForAuthors(String pattern);

}
