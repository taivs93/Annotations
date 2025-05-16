import entity.Person;
import validator.Validator;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Person person1 = new Person("","vosytai0903@gmail.com",19, LocalDate.of(2004, 3, 9));
        List<String> errors = Validator.validate(person1);
        if(errors.isEmpty()) System.out.println("Person is valid");
        else errors.forEach(System.out::println);
        System.out.println();

        Person person2 = new Person("TAI","taivs0903@gmail.com",21,LocalDate.of(2004,3,9));
        person1.love(person2);
    }
}