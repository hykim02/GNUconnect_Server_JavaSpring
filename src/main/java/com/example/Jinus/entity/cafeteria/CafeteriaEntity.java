package com.example.Jinus.entity.cafeteria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cafeteria")
public class CafeteriaEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "cafeteria_name_ko")
    private String cafeteriaNameKo;

    @Column(name = "campus_id")
    private int campusId;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
}
