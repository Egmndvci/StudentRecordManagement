package com.tez.studentrecordmanagement.controller;

import com.tez.studentrecordmanagement.entity.Student;
import com.tez.studentrecordmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // Bu sınıfın bir web kontrolcüsü olduğunu belirtir
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired // Spring'in bu repository'yi otomatik olarak bağlamasını sağlar
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // --- ÖĞRENCİ LİSTELEME SAYFASI ---
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> studentList = studentRepository.findAll();
        model.addAttribute("ogrenciler", studentList);
        return "students";
    }

    // --- ÖĞRENCİ EKLEME FORMUNU GÖSTERME ---
    @GetMapping("/students/new")
    public String showAddStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "add-student";
    }

    // --- DOLDURULAN FORMU KAYDETME (HEM YENİ HEM GÜNCELLEME İÇİN ÇALIŞIR) ---
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        // Formdan gelen 'student' nesnesinin 'id' alanı doluysa JPA bunu UPDATE,
        // 'id' alanı boş (null) ise INSERT olarak anlar.
        studentRepository.save(student);
        return "redirect:/students";
    }

    // --- ÖĞRENCİ SİLME ---
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long studentId) {
        studentRepository.deleteById(studentId);
        return "redirect:/students";
    }

    // --- YENİ EKLENDİ ---
    // --- ÖĞRENCİ GÜNCELLEME FORMUNU GÖSTERME ---
    // Kullanıcı ".../students/update/1" gibi bir adrese tıkladığında bu metot çalışacak
    @GetMapping("/students/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long studentId, Model model) {

        // 1. Veritabanından o ID'ye sahip öğrenciyi bul
        //    .findById() metodu bir 'Optional' döner, .get() ile içindeki 'Student' nesnesini alırız.
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz Öğrenci ID: " + studentId));

        // 2. Bulunan 'student' nesnesini modele ekle
        model.addAttribute("student", student);

        // 3. 'add-student.html' formunu bu dolu nesneyle göster
        return "add-student";
    }
}