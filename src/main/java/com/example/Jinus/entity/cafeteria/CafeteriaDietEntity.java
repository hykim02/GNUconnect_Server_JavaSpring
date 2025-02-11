package com.example.Jinus.entity.cafeteria;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cafeteria_diet")
public class CafeteriaDietEntity {
    @Id
    @Column(name = "diet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dietId;

    @Column(name = "date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime dateTime;  // 날짜 + 시간 + 시간대

//    @Column(name = "date")
//    private LocalTime date;

    @Column(name = "day")
    private String day_of_week;

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
