package com.example.Jinus.entity.cafeteria;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "campus")
@NoArgsConstructor
public class CampusEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "campus_name_ko")
    private String campusNameKo;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
}
