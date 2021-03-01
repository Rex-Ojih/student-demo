package com.group.studentdemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student rex = new Student(
                    "Rex",
                    "rex.ojih@mavs.uta.edu",
                    LocalDate.of(1997, Month.DECEMBER, 6)
            );

            Student darian = new Student(
                    "Darian",
                    "darian.gibson@mavs.uta.edu",
                    LocalDate.of(1997, Month.NOVEMBER, 21)
            );

            repository.saveAll(List.of(rex, darian));
        };
    }
}
