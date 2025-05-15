import entity.Person;
import validator.Validator;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Person person1 = new Person("","vosytai0903@gmail.com",19, LocalDate.of(2004, 3, 9));
        List<String> errors = Validator.validate(person1);
        errors.forEach(System.out::println);
    }
}