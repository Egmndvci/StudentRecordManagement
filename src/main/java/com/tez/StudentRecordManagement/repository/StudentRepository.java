package com.tez.studentrecordmanagement.repository;

import com.tez.studentrecordmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // JpaRepository<Hangi Sınıf, ID'nin Tipi>
    // Buraya şimdilik ekstra bir şey yazmamıza gerek yok.
    // findById, findAll, save, delete gibi metotlar otomatik gelecek.
}