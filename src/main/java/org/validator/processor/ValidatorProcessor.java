package org.validator.processor;


import com.google.auto.service.AutoService;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.validator.generator.ModelType;
import org.validator.generator.Generator;
import org.validator.generator.writer.ConditionWritableObject;
import org.validator.parser.WritableParser;
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

@SupportedAnnotationTypes({
        "org.validator.annotation.Valids",
        "org.validator.annotation.Valid"})
@AutoService(Processor.class)
public class ValidatorProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
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
                generateSource(ValidatorParser.parse(validators));
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "Error: " + ExceptionUtils.getStackTrace(e));
            }
        }
        return true;
    }

    private void generateSource(
            Map<String, List<ValidatorMethod>> validatorObjects)
            throws IOException {

        for (Map.Entry<String, List<ValidatorMethod>> entry : validatorObjects.entrySet()) {
            String className = entry.getKey();
            List<ValidatorMethod> validatorMethods = entry.getValue();
            WritableParser writableParser = new WritableParser();
            ConditionWritableObject conditionWritableObject = writableParser.parseToWritable(className, validatorMethods);

            Generator generator = new Generator();
            String classNameImpl = className + "Impl";
            JavaFileObject builderFile = processingEnv.getFiler()
                    .createSourceFile(classNameImpl);
            generator.write(builderFile, conditionWritableObject, ModelType.CONDITION);
        }
    }
}