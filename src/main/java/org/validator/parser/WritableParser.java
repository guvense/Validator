package org.validator.parser;

import org.apache.commons.lang3.StringUtils;
import org.validator.generator.model.ConditionModel;
import org.validator.generator.model.ConditionObject;
import org.validator.generator.model.FunctionDeclarationModel;
import org.validator.generator.writer.ConditionWritableObject;
import org.validator.parser.model.ValidatorDetail;
import org.validator.parser.model.ValidatorMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WritableParser {

    public  ConditionWritableObject parseToWritable(String className, List<ValidatorMethod> validatorMethods) throws IOException {
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

        ArrayList<String> imports = new ArrayList<>();
        validatorMethods.forEach(validatorMethod -> {
            String argumentPath = validatorMethod.getArgumentType();

            imports.add(argumentPath);
            String argumentTypeCore = validatorMethod.getArgumentType()
                    .substring(validatorMethod.getArgumentType().lastIndexOf(".") + 1);
            String parameterName = StringUtils.uncapitalize(argumentTypeCore);
            List<ValidatorDetail> validatorDetails = validatorMethod.getValidatorDetails();
            String parameterType = StringUtils.capitalize(argumentTypeCore);
            String methodName = validatorMethod.getMethodName();

            FunctionDeclarationModel functionDeclarationModel = new FunctionDeclarationModel();
            functionDeclarationModel.setMethodName(methodName);
            functionDeclarationModel.setParameterName(parameterName);
            functionDeclarationModel.setParameterType(parameterType);

            List<ConditionModel> conditionModels = new ArrayList<>();

            for(ValidatorDetail validatorDetail : validatorDetails) {
                ConditionModel conditionModel = new ConditionModel();
                conditionModel.setExceptionCode(validatorDetail.getErrorMessage());
                conditionModel.setException(validatorDetail.getException().getName());
                conditionModel.setSource(StringUtils.capitalize(validatorDetail.getSource()));
                conditionModel.setCondition(validatorDetail.getCondition());
                conditionModels.add(conditionModel);
            }


            ConditionObject conditionObject = new ConditionObject();
            conditionObject.setConditionModels(conditionModels);
            conditionObject.setFunctionDeclarationModel(functionDeclarationModel);
            conditionObjects.add(conditionObject);
        });
        ConditionWritableObject conditionWritableObject = new ConditionWritableObject();
        conditionWritableObject.setPackageName(packageName);
        conditionWritableObject.setInterfaceName(interfaceName);
        conditionWritableObject.setClassName(builderSimpleClassName);
        conditionWritableObject.setConditionObjects(conditionObjects);
        conditionWritableObject.setImports(imports);
        return conditionWritableObject;
    }
}
