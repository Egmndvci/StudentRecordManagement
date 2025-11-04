package com.tez.studentrecordmanagement.entity;

import jakarta.persistence.*; // jakarta.persistence.* olduğundan emin ol
import lombok.Data;

@Data // Lombok: Otomatik olarak getter, setter, toString vs. oluşturur
@Entity // Bu sınıfın bir veritabanı tablosu olduğunu belirtir
@Table(name = "students") // Tablo adını 'students' olarak belirler
public class Student {

    @Id // Bu alanın Primary Key (Birincil Anahtar) olduğunu belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'nin otomatik artan (auto-increment) olmasını sağlar
    @Column(name = "student_id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "student_number", nullable = false, unique = true, length = 20)
    private String studentNumber; // unique = true: Bu alana aynı veri ikinci kez girilemez

    @Column(nullable = false, unique = true, length = 100)
    private String email;
}