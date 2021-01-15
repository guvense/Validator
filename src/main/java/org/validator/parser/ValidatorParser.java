package org.validator.parser;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.validator.generator.Generator;
import org.validator.generator.ModelType;
import org.validator.generator.model.ConditionModel;
import org.validator.generator.model.ConditionObject;
import org.validator.generator.model.FunctionDeclarationModel;
import org.validator.generator.writer.ConditionWritableObject;
import org.validator.parser.model.ValidatorDetail;
import org.validator.parser.model.ValidatorMethod;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.JavaFileObject;
import java.io.IOException;
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

        for (Map.Entry<String, List<ValidatorMethod>> entry : validatorObjects.entrySet()) {
            String className = entry.getKey();
            List<ValidatorMethod> validatorMethods = entry.getValue();
            parse(className, validatorMethods);
        }

        return validatorObjects;
    }

    private static ConditionWritableObject parse(String className, List<ValidatorMethod> validatorMethods) {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String classNameImpl = className + "Impl";
        String builderSimpleClassName = classNameImpl
                .substring(lastDot + 1);
        String interfaceName = className.substring(lastDot + 1);
        List<ConditionObject> conditionObjects = new ArrayList<>();

        validatorMethods.forEach(validatorMethod -> {
            String argumentTypeCore = validatorMethod.getArgumentType()
                    .substring(validatorMethod.getArgumentType().lastIndexOf(".") + 1);
            String parameterName = StringUtils.uncapitalize(argumentTypeCore);
            ValidatorDetail validatorDetail = validatorMethod.getValidatorDetail();
            String parameterType = StringUtils.capitalize(argumentTypeCore);
            String methodName = validatorMethod.getMethodName();

            FunctionDeclarationModel functionDeclarationModel = new FunctionDeclarationModel();
            functionDeclarationModel.setMethodName(methodName);
            functionDeclarationModel.setParameterName(parameterName);
            functionDeclarationModel.setParameterType(parameterType);

            ConditionModel conditionModel = new ConditionModel();
            conditionModel.setExceptionCode(validatorDetail.getErrorMessage());
            conditionModel.setException(validatorDetail.getException().getName());
            conditionModel.setSource(StringUtils.capitalize(validatorDetail.getSource()));
            conditionModel.setCondition(validatorDetail.getCondition());

            ConditionObject conditionObject = new ConditionObject();
            conditionObject.setConditionModel(conditionModel);
            conditionObject.setFunctionDeclarationModel(functionDeclarationModel);
            conditionObjects.add(conditionObject);
        });

        ConditionWritableObject conditionWritableObject = new ConditionWritableObject();
        conditionWritableObject.setPackageName(packageName);
        conditionWritableObject.setInterfaceName(interfaceName);
        conditionWritableObject.setClassName(builderSimpleClassName);
        conditionWritableObject.setConditionObjects(conditionObjects);

            return conditionWritableObject;
    }

}
