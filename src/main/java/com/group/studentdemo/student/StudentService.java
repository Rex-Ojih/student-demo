package com.group.studentdemo.student;
//Service Layer: receives requests from API Layer, performs business logic and sends response back to API Layer

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        //Optional means may or may not contain student object.
        //If student object is obtained, the email is already in the database
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email already registered");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {

        //Optional means may or may not contain student object.
        boolean exists = studentRepository.existsById(studentId);

        if(exists) {
            studentRepository.deleteById(studentId);
        }
        else {
            throw new IllegalStateException("student ID: " + studentId + " does not exist");
        }
    }
    //@Transactional puts entity in a 'manage state' while using this function.
    //getter and setter function will update database.
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        //if a student with id 'studentId' exist, store it in student object, else throw error
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("student with ID: " + studentId + " does not exist"));

        //if new name is not empty or the same as the current name

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {

            student.setName(name);
        }
        else {
            System.out.println(name);
            throw new IllegalStateException("name not sufficient");
        }

        //if new email is not empty or the same as current email
        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {

            //check if new email is already in use by another student
            Optional<Student> optionalStudent = studentRepository.findStudentByEmail(email);

            //if email is already taken
            if(optionalStudent.isPresent()) {
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }

    }
}
