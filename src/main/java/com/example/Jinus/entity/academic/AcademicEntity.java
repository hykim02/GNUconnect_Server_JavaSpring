package com.example.Jinus.entity.academic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "academic_calendar")
public class AcademicEntity {
    @Id
    @Column(name = "academic_id")
    private int id;

    @Column(name = "calendar_type")
    private int calendarType;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "content")
    private String content;
}
