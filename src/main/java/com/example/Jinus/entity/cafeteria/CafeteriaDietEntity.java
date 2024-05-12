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
@Table(name = "cafeteria-diet")
public class CafeteriaDietEntity {
    @Id
    @Column(name = "date")
    private String date;

    @Column(name = "day")
    private String day;

    @Column(name = "time")
    private String time;

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "cafeteria_id")
    private String cafeteriaId;

}
