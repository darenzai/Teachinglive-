package edu.wfit.liveteaching.interceptor;

import edu.wfit.liveteaching.dao.LiveDao;
import edu.wfit.liveteaching.dao.StudentDao;
import edu.wfit.liveteaching.dao.TeacherDao;
import edu.wfit.liveteaching.model.Student;
import edu.wfit.liveteaching.model.Teacher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class LiveCountInterceptor implements HandlerInterceptor {
    private LiveDao liveDao;
    private StudentDao studentDao;
    private TeacherDao teacherDao;

    public LiveCountInterceptor(LiveDao liveDao, StudentDao studentDao, TeacherDao teacherDao) {
        this.liveDao = liveDao;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginUserIdObj = session.getAttribute("loginUserId");
        if (loginUserIdObj == null) {
            return true;
        }
        Date yesterday = new Date(System.currentTimeMillis() - 86400000);
        String loginUserId = loginUserIdObj.toString();
        String loginUserKind = session.getAttribute("loginUserKind").toString();
        Integer todayLiveCount = 0;
        switch (loginUserKind) {
            case "student":
                Student student = studentDao.findById(loginUserId).orElse(null);
                if (student == null) {
                    break;
                }
                todayLiveCount = liveDao.countByDateAfterAndMajorIdAndGrade(yesterday, student.getMajorId(), student.getGrade());
                break;
            case "teacher":
                Teacher teacher = teacherDao.findById(loginUserId).orElse(null);
                if (teacher == null) {
                    break;
                }
                todayLiveCount = liveDao.countByTeacherId(teacher.getId());
        }
        session.setAttribute("todayLiveCount", todayLiveCount);
        return true;
    }
}
