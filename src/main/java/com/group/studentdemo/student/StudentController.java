package com.group.studentdemo.student;
//API Layer: receives request from client and sends to Service Layer

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //@GetMapping allows function to receive GET requests from client
    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    //@PostMapping allows function to receive POST requests from client
    @PostMapping
    //@RequestBody takes the JSON body from the request and maps it to Student object
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    //@DeleteMapping allows function to receive DELETE requests from client at url ending with student id
    //@PathVariable maps the variable from the url path to the 'studentId' variable
    //Example: http://localhost:8080/api/v1/student/{studentId}
    //@RequestParam maps the variables given by client in url denoted by '?' to corresponding parameters of function
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
    {
        studentService.updateStudent(studentId, name, email);
    }
}
