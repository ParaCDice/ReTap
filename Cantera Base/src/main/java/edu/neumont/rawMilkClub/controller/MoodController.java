package edu.neumont.rawMilkClub.controller;

import edu.neumont.rawMilkClub.data.DiaryEntryRepository;
import edu.neumont.rawMilkClub.data.MoodRepository;
import edu.neumont.rawMilkClub.model.Mood;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * CRUD for moods: the second resource, with its own controller (Lab 2 asks for one per entity).
 *
 * GET    /api/moods         200  every mood
 * GET    /api/moods/{id}    200 / 404
 * POST   /api/moods         201 / 400
 * PUT    /api/moods/{id}    200 / 404 / 400
 * DELETE /api/moods/{id}    204 / 404, or 409 if entries still use it
 */
@RestController
@RequestMapping("/api/moods")
public class MoodController {

    private final MoodRepository moods;
    private final DiaryEntryRepository entries;

    public MoodController(MoodRepository moods, DiaryEntryRepository entries) {
        this.moods = moods;
        this.entries = entries;
    }

    public record MoodRequest(
            @NotBlank(message = "name is required")
            @Size(max = 40, message = "name can be at most 40 characters")
            String name) {
    }

    @GetMapping
    public List<Mood> getAll() {
        return moods.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mood> getOne(@PathVariable int id) {
        return ResponseEntity.of(moods.findById(id));
    }

    @PostMapping
    public ResponseEntity<Mood> create(@Valid @RequestBody MoodRequest request) {
        Mood created = moods.save(new Mood(request.name().trim()));
        return ResponseEntity.created(URI.create("/api/moods/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mood> update(@PathVariable int id, @Valid @RequestBody MoodRequest request) {
        return ResponseEntity.of(moods.findById(id).map(mood -> {
            mood.setName(request.name().trim());
            return moods.save(mood);
        }));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        // Don't leave entries pointing at a mood that's gone: 409 Conflict instead
        long inUse = entries.countByMoodId(id);
        if (inUse > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", inUse == 1 ? "1 entry still uses this mood"
                                                     : inUse + " entries still use this mood"));
        }
        if (!moods.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        moods.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
