package edu.neumont.rawmilkclub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.net.URI;
import java.util.*;

/** A mood, now a row in the mood table, e.g. {"id":1,"name":"Happy"}. */
@Entity
public class Card {

    //core card fields THAT CAN'T BE NULL
    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String layout;
    @Column(nullable = false, unique = true)
    private URI scryfall_uri;

    //gameplay fields THAT CAN'T BE NULL
    @Column(nullable = false, unique = true)
    private String name;

    //print fields THAT CAN'T BE NULL
    @Column(nullable = false, unique = true)
    private String image_status;
    @Column(nullable = false, unique = true)
    private String set_name;

    //card face objects THAT CANT BE NULL
    @Column(nullable = false, unique = true)
    private String face_mana_cost_one;
    @Column(nullable = false, unique = true)
    private String face_name_one;
    @Column(nullable = true, unique = true)
    private String face_mana_cost_two;
    @Column(nullable = true, unique = true)
    private String face_name_two;
    @Column(nullable = true, unique = true)
    private String type_line_one;
    @Column(nullable = true, unique = true)
    private String type_line_two;
    @Column(nullable = true, unique = true)
    private String oracle_text_one;
    @Column(nullable = true, unique = true)
    private String oracle_text_two;

    /** JPA needs an empty constructor to build objects from rows. */
    public Card() {
    }

    //TODO: get these constructors working :J
    public Card(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
