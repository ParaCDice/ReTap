package edu.neumont.rawmilkclub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.net.URI;
import java.util.UUID;

@Entity
public class CardImages {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String thumb_face_one;
    @Column(nullable = false, unique = true)
    private String grid_face_one;

    @Column(nullable = false, unique = true)
    private String thumb_face_two;
    @Column(nullable = false, unique = true)
    private String grid_face_two;

}
