package edu.wfit.liveteaching.controller;

import edu.wfit.liveteaching.model.Major;
import edu.wfit.liveteaching.model.Student;
import edu.wfit.liveteaching.model.Teacher;
import edu.wfit.liveteaching.service.MajorService;
import edu.wfit.liveteaching.service.StudentService;
import edu.wfit.liveteaching.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 注册接口
 */
@Controller
public class RegisterController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MajorService majorService;

    /**
     * 判断是哪一种注册，根据kind值调用
     * @param kind
     * @param model
     * @return
     */
    @GetMapping("/register/{kind}")
    public String toRegisterPage(@PathVariable String kind, Model model) {
        switch (kind) {
            case "teacher":
                return "register-teacher";
            case "student":
                List<Major> majorList = majorService.findAll();
                model.addAttribute("majorList", majorList);
                return "register-student";
            default:
                return "redirect:/";
        }
    }

    @PostMapping("/register/student")
    public String registerStudent(Student student, Model model) {
        Student studentFromDB = studentService.findById(student.getId());
        if (studentFromDB != null) {
            model.addAttribute("message", "该学号已经被注册");
            return "/register-student";
        }
        studentService.save(student);
        return "redirect:/login";
    }

    @PostMapping("/register/teacher")
    public String registerTeacher(Teacher teacher, Model model) {
        Teacher teacherFromDB = teacherService.findById(teacher.getId());
        if (teacherFromDB != null) {
            model.addAttribute("message", "该工号已经被注册");
            return "/register-teacher";
        }
        teacherService.save(teacher);
        return "redirect:/login";
    }
}
