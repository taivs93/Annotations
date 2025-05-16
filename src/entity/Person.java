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
    public void love(Person person2) throws IllegalAccessException {
        List<String> errors = Validator.validate(person2);
        if(errors.isEmpty()){
            System.out.println("Validate successfully.");
        }
        else errors.forEach(System.out::println);
    }
}
