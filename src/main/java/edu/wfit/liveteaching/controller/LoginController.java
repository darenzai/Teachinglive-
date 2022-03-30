package edu.wfit.liveteaching.controller;

import edu.wfit.liveteaching.interceptor.MySessionListener;
import edu.wfit.liveteaching.model.Student;
import edu.wfit.liveteaching.model.Teacher;
import edu.wfit.liveteaching.service.StudentService;
import edu.wfit.liveteaching.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class LoginController {
    //自动注入
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/login")
    public String toLoginPage() {
        return "login";
    }

    /**
     * 主登录接口 根据不同类型调用不同接口
     * @param id
     * @param password
     * @param kind
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam("kind") String kind,
            HttpSession session,
            Model model
    ) {
        boolean loginResult;
        switch (kind) {
            case "student":
                loginResult = studentLogin(id, password, session,model);
                break;
            case "teacher":
                loginResult = teacherLogin(id, password, session,model);
                break;
            default:
                return "redirect:/";
        }
        if (!loginResult) {
            model.addAttribute("message", "登录失败，用户名或密码错误");
            return "/login";
        }
        session.setAttribute("loginUserId", id);
        session.setAttribute("loginUserKind", kind);
        return "redirect:/";
    }




    /**
     * //学生登录接口
     * @param id
     * @param password
     * @param session
     * @return
     */
    private boolean studentLogin(String id, String password, HttpSession session,Model model) {
        Student student = studentService.findByIdAndPassword(id, password);
        if (student == null) {
            return false;
        }

        session.setAttribute("loginUserNickname", student.getNickname());
        AtomicInteger usercount= MySessionListener.userCount;
        model.addAttribute("usercount",usercount);
        System.out.println("教师登录 : 目前在线人数为 "+usercount);
        return true;
    }


    /**
     * //老师登录接口
     * @param id
     * @param password
     * @param session
     * @return
     */
    private boolean teacherLogin(String id, String password, HttpSession session, Model model) {
        Teacher teacher = teacherService.findByIdAndPassword(id, password);
        if (teacher == null) {
            return false;
        }
        session.setAttribute("loginUserNickname", teacher.getNickname());
        AtomicInteger usercount= MySessionListener.userCount;
        model.addAttribute("usercount","usercount");
        System.out.println("教师登录 : 目前在线人数为 "+usercount);
        return true;
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        AtomicInteger userCount = MySessionListener.userCount;
        System.out.println("下线成功！当前在线人数："+userCount);
        return "redirect:/login";
    }
}
