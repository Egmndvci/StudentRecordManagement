package com.tez.studentrecordmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    // ----- İLİŞKİ 1 -----
    // Birçok kayıt, tek bir öğrenciye aittir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // ----- İLİŞKİ 2 -----
    // Birçok kayıt, tek bir derse aittir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(length = 5)
    private String grade; // Not (örn: "AA", "FF")
}