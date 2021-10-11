package com.sp.fc.controller;

import com.sp.fc.student.Student;
import com.sp.fc.student.StudentManager;
import com.sp.fc.teacher.Teacher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class MobileTeacherController {
    private final StudentManager studentManager;

    public MobileTeacherController(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/students")
    public List<Student> students(@AuthenticationPrincipal Teacher teacher){
        return studentManager.myStudentList(teacher.getId());
    }
}
