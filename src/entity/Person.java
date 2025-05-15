package entity;

import annotations.*;

import java.time.LocalDate;

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
    public void love(Person person2){}
}
