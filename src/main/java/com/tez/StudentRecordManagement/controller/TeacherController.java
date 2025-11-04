package com.tez.studentrecordmanagement.controller;

import com.tez.studentrecordmanagement.entity.Teacher;
import com.tez.studentrecordmanagement.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // Bu bir Controller sınıfıdır
public class TeacherController {

    // Artık StudentRepository değil, TeacherRepository kullanıyoruz
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // --- ÖĞRETMEN LİSTELEME SAYFASI ---
    // Adres: /teachers
    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        List<Teacher> teacherList = teacherRepository.findAll();
        model.addAttribute("ogretmenler", teacherList); // Modelin adı "ogretmenler" oldu
        return "teachers"; // teachers.html dosyasını gösterecek
    }

    // --- ÖĞRETMEN EKLEME FORMUNU GÖSTERME ---
    // Adres: /teachers/new
    @GetMapping("/teachers/new")
    public String showAddTeacherForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "add-teacher"; // add-teacher.html dosyasını gösterecek
    }

    // --- DOLDURULAN FORMU KAYDETME (HEM YENİ HEM GÜNCELLEME) ---
    // Adres: /teachers/save
    @PostMapping("/teachers/save")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
        teacherRepository.save(teacher);
        return "redirect:/teachers"; // Öğretmen listesine yönlendir
    }

    // --- ÖĞRETMEN GÜNCELLEME FORMUNU GÖSTERME ---
    // Adres: /teachers/update/{id}
    @GetMapping("/teachers/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long teacherId, Model model) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz Öğretmen ID: " + teacherId));
        model.addAttribute("teacher", teacher);
        return "add-teacher"; // Güncelleme için de aynı formu (add-teacher.html) kullanıyoruz
    }

    // --- ÖĞRETMEN SİLME ---
    // Adres: /teachers/delete/{id}
    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") Long teacherId) {
        teacherRepository.deleteById(teacherId);
        return "redirect:/teachers";
    }
}