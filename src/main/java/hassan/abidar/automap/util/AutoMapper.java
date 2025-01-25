package hassan.abidar.automap.util;


import hassan.abidar.automap.annotation.AutoMap;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AutoMapper {

    public static <S, T> T map(S source, Class<T> targetClass) {
        try {
            // Create an instance of the target class
            T target = targetClass.getDeclaredConstructor().newInstance();

            // Get all fields from the source class
            Field[] sourceFields = source.getClass().getDeclaredFields();

            // Map fields from source to target
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true); // Allow access to private fields

                // Check if the field has the @AutoMap annotation
                if (sourceField.isAnnotationPresent(AutoMap.class)) {
                    AutoMap autoMap = sourceField.getAnnotation(AutoMap.class);

                    // Get the target field name (if specified in the annotation)
                    String targetFieldName = autoMap.field().isEmpty() ? sourceField.getName() : autoMap.field();

                    // Get the target field
                    Field targetField = Arrays.stream(targetClass.getDeclaredFields())
                            .filter(f -> f.getName().equals(targetFieldName))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Target field not found: " + targetFieldName));

                    targetField.setAccessible(true); // Allow access to private fields

                    // Set the value of the target field
                    targetField.set(target, sourceField.get(source));
                }
            }

            return target;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map objects", e);
        }
    }
}
