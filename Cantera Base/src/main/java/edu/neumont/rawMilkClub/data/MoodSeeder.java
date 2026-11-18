package edu.neumont.rawMilkClub.data;

import edu.neumont.rawMilkClub.model.Mood;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Runs once, right after the app starts (CommandLineRunner).
 * An empty database gets the 4 starting moods; a database that has moods is left alone.
 */
@Component
public class MoodSeeder implements CommandLineRunner {

    private final MoodRepository moods;

    public MoodSeeder(MoodRepository moods) {
        this.moods = moods;
    }

    @Override
    public void run(String... args) {
        if (moods.count() == 0) {
            for (String name : List.of("Happy", "Calm", "Tired", "Stressed")) {
                moods.save(new Mood(name));
            }
        }
    }
}
