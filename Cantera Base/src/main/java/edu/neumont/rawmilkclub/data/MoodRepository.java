package edu.neumont.rawmilkclub.data;

import edu.neumont.rawmilkclub.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

/** Same idea for moods. Nothing extra needed yet. */
public interface MoodRepository extends JpaRepository<Mood, Integer> {
}
