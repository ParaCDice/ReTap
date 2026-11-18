package edu.neumont.rawMilkClub.data;

import edu.neumont.rawMilkClub.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

/** Same idea for moods. Nothing extra needed yet. */
public interface MoodRepository extends JpaRepository<Mood, Integer> {
}
