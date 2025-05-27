package validator;

import annotations.*;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Validator {

    public static List<String> validate(Object obj) throws IllegalAccessException {
        List<String> errors = new LinkedList<>();
        Class<?> classOfObj = obj.getClass();
        Field[] fields = classOfObj.getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);
            Object value = field.get(obj);
            processAnnotations(field, value, field.getName(), errors);
        }
        return errors;
    }
    public static List<String> validateMethod(Object obj, String methodName, Object... params ){
        List<String> errors = new LinkedList<>();
        Class<?> classOfObj = obj.getClass();
        Method targetMethod = null;
        for (Method method : classOfObj.getMethods()) {
            if (method.getName().equals(methodName) &&
                    method.isAnnotationPresent(ValidateMethod.class)) {

                Class<?>[] methodParamTypes = method.getParameterTypes();
                if (methodParamTypes.length != params.length) continue;

                boolean match = true;
                for (int i = 0; i < params.length; i++) {
                    if (params[i] != null) {
                        if (!methodParamTypes[i].isAssignableFrom(params[i].getClass())) {
                            match = false;
                            break;
                        }
                    }
                }
                if (match) {
                    targetMethod = method;
                    break;
                }
            }
        }

        if(targetMethod != null){
            Parameter[] parameters = targetMethod.getParameters();
            for (int i = 0; i< parameters.length;i++){
                Parameter param = parameters[i];
                Object value = params[i];
                processAnnotations(param, value, param.getName(), errors);
            }
        }
        return errors;
    }

    private static void processAnnotations(AnnotatedElement element, Object value, String objectName, List<String> errors){
        if(element.isAnnotationPresent(NotNull.class) && value == null){
            errors.add(objectName + " can not be null");
        }
        if (element.isAnnotationPresent(Length.class)){
            Length length = element.getAnnotation(Length.class);
            if(value != null){
                String strValue = String.valueOf(value);
                if (strValue.length() < length.min() || strValue.length() > length.max()){
                    errors.add(objectName + "'s length must be in range " + length.min() +" to "+length.max());
                }
            }
        }
        if (element.isAnnotationPresent(Range.class)){
            Range range = element.getAnnotation(Range.class);
            if (value instanceof Integer){
                Integer intOfValue = (Integer) value;
                if(intOfValue < range.min() || intOfValue > range.max()){
                    errors.add(objectName + " must be in range " + range.min() + " to " + range.max());
                }
            }
        }
        if (element.isAnnotationPresent(Email.class)){
            Email email = element.getAnnotation(Email.class);
            if (value != null){
                String valueEmail = value.toString();
                if(!valueEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
                    errors.add("Invalid " + objectName);
                }
            }
        }
        if (element.isAnnotationPresent(Future.class)){
            Future future = element.getAnnotation(Future.class);
            if (value instanceof LocalDate date){
                if (date.isBefore(LocalDate.now()) || date.equals(LocalDate.now())){
                    errors.add(objectName + " must be in the future");
                }
            }
        }
        if (element.isAnnotationPresent(Past.class)){
            Past past = element.getAnnotation(Past.class);
            if (value instanceof LocalDate date){
                if (date.isAfter(LocalDate.now()) || date.equals(LocalDate.now())){
                    errors.add(objectName + " must be in the past");
                }
            }
        }
    }
}
