package edu.neumont.csc280.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

/**
 * One diary entry, now also one ROW in the diary_entry table.
 *   @Entity       this class is a table (class DiaryEntry -> table diary_entry)
 *   @Id           this field is the primary key
 *   @GeneratedValue(IDENTITY)   the database picks the id (AUTO_INCREMENT)
 *   @Column       optional rules for a column; field createdAt -> column created_at
 * The JSON is exactly what it was in E3-1.
 */
@Entity
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 2000)
    private String text;

    private Integer moodId;   // still just a number; E3-5 turns it into a real link to a Mood

    /** JPA needs an empty constructor to build objects from rows. */
    public DiaryEntry() {
    }

    /** No id: the database assigns it when the entry is saved. */
    public DiaryEntry(LocalDateTime createdAt, String text, Integer moodId) {
        this.createdAt = createdAt;
        this.text = text;
        this.moodId = moodId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMoodId() {
        return moodId;
    }

    public void setMoodId(Integer moodId) {
        this.moodId = moodId;
    }
}
