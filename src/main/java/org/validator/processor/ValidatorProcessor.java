package org.validator.processor;


import com.google.auto.service.AutoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.validator.generator.ModelType;
import org.validator.generator.Generator;
import org.validator.generator.model.ConditionModel;
import org.validator.generator.model.ConditionObject;
import org.validator.generator.model.FunctionDeclarationModel;
import org.validator.generator.writer.ConditionWritableObject;
import org.validator.parser.model.ValidatorDetail;
import org.validator.parser.model.ValidatorMethod;
import org.validator.parser.ValidatorParser;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes(
        "org.validator.annotation.Valids")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class ValidatorProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            for (TypeElement annotation : annotations) {

                Set<? extends Element> annotatedElements
                        = roundEnv.getElementsAnnotatedWith(annotation);

                Map<Boolean, List<Element>> annotatedMethods =
                        annotatedElements.stream().collect(
                                Collectors.partitioningBy(element ->
                                        ((ExecutableType) element.asType()).getParameterTypes().size() == 1));

                List<Element> validators = annotatedMethods.get(true);
                if (validators.isEmpty()) {
                    continue;
                }
                try {
                    loop(ValidatorParser.parse(validators, processingEnv));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "Error: " + ExceptionUtils.getStackTrace(e));
                }
            }
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Error" + ExceptionUtils.getStackTrace(e));
        }
        return true;
    }

    private void loop(
            Map<String, List<ValidatorMethod>> validatorObjects)
            throws IOException {

        for (Map.Entry<String, List<ValidatorMethod>> entry : validatorObjects.entrySet()) {
            String className = entry.getKey();
            List<ValidatorMethod> validatorMethods = entry.getValue();
            fill(className, validatorMethods);
        }
    }

    private void fill(String className, List<ValidatorMethod> validatorMethods) throws IOException {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String classNameImpl = className + "Impl";
        String builderSimpleClassName = classNameImpl
                .substring(lastDot + 1);
        String interfaceName = className.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(classNameImpl);

        List<ConditionObject> conditionObjects = new ArrayList<>();

        validatorMethods.forEach(validatorMethod -> {
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

        try {
            Generator generator = new Generator();
            generator.write(builderFile, conditionWritableObject, ModelType.CONDITION);

        } catch (Exception e) {
            printError(ExceptionUtils.getStackTrace(e));
        }
    }

    private void printError(String errorMessage) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, errorMessage);
    }
}