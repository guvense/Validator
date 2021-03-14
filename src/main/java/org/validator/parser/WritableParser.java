package org.validator.parser;

import org.apache.commons.lang3.StringUtils;
import org.validator.generator.model.ConditionModel;
import org.validator.generator.model.ConditionObject;
import org.validator.generator.model.FunctionDeclarationModel;
import org.validator.generator.model.PatternModel;
import org.validator.generator.writer.ConditionWritableObject;
import org.validator.parser.model.ValidatorDetail;
import org.validator.parser.model.ValidatorMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WritableParser {

    public ConditionWritableObject parseToWritable(String className, List<ValidatorMethod> validatorMethods) throws IOException {
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
            fillImportPaths(imports, validatorMethod);

            String parameterName = extractParameterName(validatorMethod);
            String parameterType = extractParameterType(parameterName);
            String methodName = validatorMethod.getMethodName();

            FunctionDeclarationModel functionDeclarationModel = new FunctionDeclarationModel();
            functionDeclarationModel.setMethodName(methodName);
            functionDeclarationModel.setParameterName(parameterName);
            functionDeclarationModel.setParameterType(parameterType);

            List<ValidatorDetail> validatorDetails = validatorMethod.getValidatorDetails();
            List<ConditionModel> conditionModels = new ArrayList<>();
            List<PatternModel> patternModels = new ArrayList<>();


            for(ValidatorDetail validatorDetail : validatorDetails) {

                if(null != validatorDetail.getCondition()) {
                    ConditionModel conditionModel = new ConditionModel();
                    conditionModel.setExceptionCode(validatorDetail.getErrorMessage());
                    conditionModel.setException(validatorDetail.getException().getName());
                    conditionModel.setSource(StringUtils.capitalize(validatorDetail.getSource()));
                    conditionModel.setCondition(validatorDetail.getCondition());
                    conditionModels.add(conditionModel);
                } else if(null != validatorDetail.getPattern()) {
                    PatternModel patternModel = new PatternModel();
                    patternModel.setExceptionCode(validatorDetail.getErrorMessage());
                    patternModel.setException(validatorDetail.getException().getName());
                    patternModel.setSource(StringUtils.capitalize(validatorDetail.getSource()));
                    patternModel.setPattern(validatorDetail.getPattern());
                    patternModels.add(patternModel);
                }
            }

            ConditionObject conditionObject = new ConditionObject();
            conditionObject.setConditionModels(conditionModels);
            conditionObject.setPatternModels(patternModels);
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

    private String extractParameterType (String parameterName){
            return StringUtils.capitalize(parameterName);
        }

        private String extractParameterName (ValidatorMethod validatorMethod){
            String argumentTypeCore = validatorMethod.getArgumentType()
                    .substring(validatorMethod.getArgumentType().lastIndexOf(".") + 1);
            return StringUtils.uncapitalize(argumentTypeCore);
        }

        private void fillImportPaths (ArrayList < String > imports, ValidatorMethod validatorMethod){
            String argumentPath = validatorMethod.getArgumentType();

            imports.add(argumentPath);
        }
    }
