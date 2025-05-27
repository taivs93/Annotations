import entity.Person;
import validator.Validator;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Person person1 = new Person("toi","vosytai0903@gmail.com",19, LocalDate.of(2004, 3, 9));

        person1.updatePersonInfo("tai","taivs0903@gmail.com", 3000, LocalDate.of(2004,3,9));
    }
}