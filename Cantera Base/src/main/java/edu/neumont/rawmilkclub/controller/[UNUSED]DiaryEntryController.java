//package edu.neumont.rawmilkclub.controller;
//
//import edu.neumont.rawmilkclub.data.DiaryEntryRepository;
//import edu.neumont.rawmilkclub.data.MoodRepository;
//import edu.neumont.rawmilkclub.model.DiaryEntry;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.URI;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Map;
//
///**
// * Full CRUD for diary entries.
// *
// * GET    /api/entries               200  every entry
// * GET    /api/entries?search=spring 200  entries whose text contains "spring" (any case)
// * GET    /api/entries/{id}          200  one entry, or 404
// * POST   /api/entries               201  create  (400 if the text is blank or the mood doesn't exist)
// * PUT    /api/entries/{id}          200  update  (404 / 400)
// * DELETE /api/entries/{id}          204  deleted, or 404
// */
//@RestController
//@RequestMapping("/api/entries")
//public class DiaryEntryController {
//
//    private final DiaryEntryRepository entries;
//    private final MoodRepository moods;
//
//    public DiaryEntryController(DiaryEntryRepository entries, MoodRepository moods) {
//        this.entries = entries;
//        this.moods = moods;
//    }
//
//    /**
//     * What a client may send to create or update an entry. The annotations are the rules;
//     * @Valid below makes Spring check them and answer 400 when one fails (see ApiErrors).
//     */
//    public record EntryRequest(
//            @NotBlank(message = "text is required")
//            @Size(max = 2000, message = "text can be at most 2000 characters")
//            String text,
//            Integer moodId) {   // optional: null means "no mood"
//    }
//
//    @GetMapping
//    public List<DiaryEntry> getAll(@RequestParam(required = false) String search) {
//        // @RequestParam(required = false): search is null when the URL has no ?search=
//        if (search == null || search.isBlank()) {
//            return entries.findAll();
//        }
//        return entries.findByTextContainingIgnoreCase(search.trim());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<DiaryEntry> getOne(@PathVariable int id) {
//        // @PathVariable: the {id} part of the URL, e.g. 3 in /api/entries/3
//        return ResponseEntity.of(entries.findById(id));   // 200 with the entry, or 404 if empty
//    }
//
//    @PostMapping
//    public ResponseEntity<?> create(@Valid @RequestBody EntryRequest request) {
//        if (moodMissing(request.moodId())) {
//            return ResponseEntity.badRequest().body(Map.of("error", "There is no mood with id " + request.moodId()));
//        }
//        DiaryEntry entry = new DiaryEntry(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
//                request.text().trim(), request.moodId());
//        DiaryEntry created = entries.save(entry);   // INSERT; the saved entry has its new id
//        // 201 Created, with a Location header pointing at the new entry
//        return ResponseEntity.created(URI.create("/api/entries/" + created.getId())).body(created);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody EntryRequest request) {
//        if (moodMissing(request.moodId())) {
//            return ResponseEntity.badRequest().body(Map.of("error", "There is no mood with id " + request.moodId()));
//        }
//        return entries.findById(id)
//                .map(entry -> {
//                    entry.setText(request.text().trim());
//                    entry.setMoodId(request.moodId());
//                    return entries.save(entry);          // UPDATE: this id already exists
//                })
//                .<ResponseEntity<?>>map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable int id) {
//        // 204 No Content: it worked, and there's nothing to send back
//        if (!entries.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        entries.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    /** A mood id was given, but no mood has it. */
//    private boolean moodMissing(Integer moodId) {
//        return moodId != null && !moods.existsById(moodId);
//    }
//}
