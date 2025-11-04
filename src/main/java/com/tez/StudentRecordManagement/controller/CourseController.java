package com.tez.studentrecordmanagement.controller;

import com.tez.studentrecordmanagement.entity.Course;
import com.tez.studentrecordmanagement.entity.Teacher;
import com.tez.studentrecordmanagement.repository.CourseRepository;
import com.tez.studentrecordmanagement.repository.TeacherRepository; // Öğretmenleri çekmek için eklendi
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CourseController {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository; // İkinci repository eklendi

    @Autowired
    public CourseController(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    // --- DERS LİSTELEME SAYFASI ---
    // Adres: /courses
    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courseList = courseRepository.findAll();
        model.addAttribute("dersler", courseList);
        return "courses"; // courses.html'i gösterecek
    }

    // --- DERS EKLEME FORMUNU GÖSTERME ---
    // Adres: /courses/new
    @GetMapping("/courses/new")
    public String showAddCourseForm(Model model) {
        // 1. Form için boş bir Course nesnesi oluştur
        Course course = new Course();
        // 2. Dropdown için TÜM öğretmenlerin listesini veritabanından çek
        List<Teacher> teacherList = teacherRepository.findAll();

        // 3. Hem boş 'course' nesnesini hem de 'teacherList'i modele ekle
        model.addAttribute("course", course);
        model.addAttribute("allTeachers", teacherList); // Dropdown bu listeyi kullanacak

        return "add-course"; // add-course.html dosyasını gösterecek
    }

    // --- DOLDURULAN FORMU KAYDETME (HEM YENİ HEM GÜNCELLEME) ---
    // Adres: /courses/save
    @PostMapping("/courses/save")
    public String saveCourse(@ModelAttribute("course") Course course) {
        courseRepository.save(course);
        return "redirect:/courses"; // Ders listesine yönlendir
    }

    // --- DERS GÜNCELLEME FORMUNU GÖSTERME ---
    // Adres: /courses/update/{id}
    @GetMapping("/courses/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long courseId, Model model) {
        // 1. Güncellenecek DERS'i ID ile bul
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz Ders ID: " + courseId));

        // 2. Dropdown için TÜM öğretmenlerin listesini çek
        List<Teacher> teacherList = teacherRepository.findAll();

        // 3. Hem dolu 'course' nesnesini hem de 'teacherList'i modele ekle
        model.addAttribute("course", course);
        model.addAttribute("allTeachers", teacherList);

        return "add-course"; // Güncelleme için de aynı formu (add-course.html) kullanıyoruz
    }

    // --- DERS SİLME ---
    // Adres: /courses/delete/{id}
    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long courseId) {
        courseRepository.deleteById(courseId);
        return "redirect:/courses";
    }
}