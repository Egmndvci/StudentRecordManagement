package com.tez.studentrecordmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String courseCode;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    // ----- İLİŞKİ -----
    // Birçok ders, tek bir öğretmene aittir (@ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY) // Veritabanından verimlilik için 'lazy'
    @JoinColumn(name = "teacher_id", nullable = false) // 'courses' tablosundaki 'teacher_id' sütunu
    private Teacher teacher; // Bu dersin 'Teacher' nesnesi
}