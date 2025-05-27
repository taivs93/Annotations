package entity;

import annotations.*;
import validator.Validator;

import java.time.LocalDate;
import java.util.List;

public class Person {
    @NotNull
    @Length(min = 1,max = 10)
    private String name;
    @NotNull
    @Email
    private String email;
    @Range(min = 1,max = 100)
    private Integer age;
    @Past
    private LocalDate birthDate;

    public Person(String name, String email, Integer age, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
    }

    @ValidateMethod
    public void updatePersonInfo(
            @NotNull @Length(min = 1, max = 10) String name,
            @NotNull @Email String email,
            @Range(min = 1, max = 100) Integer age,
            @Past LocalDate birthDate
    ) throws IllegalAccessException {
        List<String> errors = Validator.validateMethod(this, "updatePersonInfo", name, email, age, birthDate);
        if (!errors.isEmpty()) {
            System.out.println("Validate unsuccessfully.");
            errors.forEach(System.out::println);
            return;
        }
        this.name = name;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
        System.out.println("Person updated successfully!");
    }
}
