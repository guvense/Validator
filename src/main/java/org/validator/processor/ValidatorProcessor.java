package org.validator.processor;


import com.google.auto.service.AutoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.validator.constant.Condition;
import org.validator.model.ValidatorDetail;
import org.validator.model.ValidatorMethod;
import org.validator.parser.AnnotationFieldParser;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes(
        "org.validator.annotation.Valid")
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
                    loop(ValidatorParser.parse(validators));
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

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            
            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            validatorMethods.forEach(validatorMethod -> {
                out.print("import ");
                out.print(validatorMethod.getArgumentType());
                out.println(";\n");
            });
            out.print("@Component");
            out.println();
            out.print("public class ");
            out.print(builderSimpleClassName);
            out.print(" implements ");
            out.print(interfaceName);
            out.println(" {");
            out.println();


            validatorMethods.forEach(validatorMethod -> {
                String argumentTypeCore = validatorMethod.getArgumentType()
                        .substring(validatorMethod.getArgumentType().lastIndexOf(".") + 1);
                String uncapitalizedParameter = StringUtils.uncapitalize(argumentTypeCore);

                ValidatorDetail validatorDetail = validatorMethod.getValidatorDetail();
                out.print("    public void ");
                out.print(validatorMethod.getMethodName());
                out.print("(");
                out.print(argumentTypeCore);
                out.print(" ");
                out.print(uncapitalizedParameter);
                out.print(")");
                out.println("{");

                if (validatorDetail.getCondition() == Condition.IsNotNull) {
                    out.print("\t\t\tif( null == ");
                    out.print(uncapitalizedParameter);
                    out.print(".");
                    out.print("get");
                    out.print(StringUtils.capitalize(validatorDetail.getSource()));
                    out.print("())");
                    out.println("{");
                    out.print("\t\t\t\tthrow new ");
                    out.print(validatorDetail.getException().getName());
                    out.print("(\"");
                    out.print(validatorDetail.getErrorMessage());
                    out.print("\");");
                    out.println("}");
                }
                out.println("}");
            });

            out.println("}");

        }
    }
}