package edu.neumont.rawmilkclub.data;

import edu.neumont.rawmilkclub.model.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The whole "store", as an interface. Spring Data writes the class for you when the app starts.
 * From JpaRepository<DiaryEntry, Integer> (entity type, id type) you get, for free:
 *   findAll(), findById(id), save(entry), deleteById(id), existsById(id), count() ...
 * The two methods below are "derived queries": Spring reads the method NAME and writes the SQL.
 */
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Integer> {

    /** ... WHERE UPPER(text) LIKE UPPER('%searchText%') */
    List<DiaryEntry> findByTextContainingIgnoreCase(String searchText);

    /** SELECT COUNT(*) ... WHERE mood_id = ? */
    long countByMoodId(Integer moodId);
}
