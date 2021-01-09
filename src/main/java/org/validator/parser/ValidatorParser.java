package org.validator.parser;


import org.validator.model.ValidatorDetail;
import org.validator.model.ValidatorMethod;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidatorParser {

    public static Map<String, List<ValidatorMethod>> parse(List<Element> validators) throws ClassNotFoundException {
        Map<String, List<ValidatorMethod>> validatorObjects = new HashMap<>();

        for (Element validator : validators) {
            String className = ((TypeElement) validator.getEnclosingElement()).getQualifiedName().toString();
            String methodName = validator.getSimpleName().toString();
            String argumentType = ((ExecutableType) validator.asType())
                    .getParameterTypes().get(0).toString();

            ValidatorDetail validatorDetail = AnnotationFieldParser.parseValidatorDetail(validator);

            ValidatorMethod newValidatorMethod = new ValidatorMethod();
            newValidatorMethod.setArgumentType(argumentType);
            newValidatorMethod.setMethodName(methodName);
            newValidatorMethod.setValidatorDetail(validatorDetail);


            if (null != validatorObjects.get(className)) {
                List<ValidatorMethod> validatorMethods = validatorObjects.get(className);
                List<ValidatorMethod> copied = new ArrayList<>(validatorMethods);
                copied.add(newValidatorMethod);
                validatorObjects.put(className, copied);
            } else {
                List<ValidatorMethod> createdList = Collections.singletonList(newValidatorMethod);
                validatorObjects.put(className, createdList);
            }
        }
        return validatorObjects;
    }
}
