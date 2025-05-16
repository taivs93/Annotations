package validator;

import annotations.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static List<String> validate(Object obj) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        Class<?> classOfObj = obj.getClass();
        Field[] fields = classOfObj.getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);
            Object value = field.get(obj);
            if (field.isAnnotationPresent(NotNull.class) && value == null){
                errors.add(field.getName() + " can not be null");
            }
            if (field.isAnnotationPresent(Length.class)){
                Length length = field.getAnnotation(Length.class);
                if (value != null){
                    String valueStr = value.toString();
                    if(valueStr.length() > length.max() || valueStr.length() < length.min()){
                        errors.add(field.getName() + "'s length must be in range from " + length.min() +" to " + length.max());
                    }
                }
            }
            if (field.isAnnotationPresent(Range.class)){
                Range range = field.getAnnotation(Range.class);
                if(value instanceof Integer){
                    int valueInt = (Integer) value;
                    if(valueInt < range.min() || valueInt > range.max()){
                        errors.add(field.getName() + " must be in range "+range.min() + " to "+range.max());
                    }
                }
            }
            if (field.isAnnotationPresent(Email.class)){
                Email email = field.getAnnotation(Email.class);
                if (value != null){
                    String valueEmail = value.toString();
                    if(!valueEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
                        errors.add("Invalid " + field.getName());
                    }
                }
            }
            if (field.isAnnotationPresent(Future.class)){
                Future future = field.getAnnotation(Future.class);
                if (value instanceof LocalDate date){
                    if (date.isBefore(LocalDate.now()) || date.equals(LocalDate.now())){
                        errors.add(field.getName() + " must be in the future");
                    }
                }
            }
            if (field.isAnnotationPresent(Past.class)){
                Past past = field.getAnnotation(Past.class);
                if (value instanceof LocalDate date){
                    if (date.isAfter(LocalDate.now()) || date.equals(LocalDate.now())){
                        errors.add(field.getName() + " must be in the past");
                    }
                }
            }

        }
        return errors;
    }
}
