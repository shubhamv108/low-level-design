package code.shubham.tinyurl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortURL, Integer> {

    @Query(nativeQuery = true, value = "SELECT url FROM short_urls WHERE short_url = ? AND (user_id IS NULL OR user_id = ?)")
    String findURL(String shortUrl, Integer userId);
}
