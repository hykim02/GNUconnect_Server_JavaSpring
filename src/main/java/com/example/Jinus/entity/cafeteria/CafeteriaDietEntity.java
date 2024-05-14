package com.example.Jinus.entity.cafeteria;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "cafeteria_diet")
public class CafeteriaDietEntity {
    @Id
    @Column(name = "diet_id")
    private int dietId;

    @Column(name = "date")
    private LocalTime date;

    @Column(name = "day")
    private String day;

    @Column(name = "time")
    private String time;

    @Column(name = "dish_category")
    private String dishCategory;

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "cafeteria_id")
    private int cafeteriaId;
}
