package org.validator.parser;


import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.util.Pair;
import org.validator.generator.constant.ConditionRule;
import org.validator.generator.constant.Pattern;
import org.validator.parser.model.ValidatorDetail;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnotationFieldParser {

    @SuppressWarnings("unchecked")
    public static List<ValidatorDetail> parseValidatorDetail(Element element) throws ClassNotFoundException {

        List<ValidatorDetail> validatorDetails = new ArrayList<>();

        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                    = annotationMirror.getElementValues();

            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                    : elementValues.entrySet()) {
                List<Attribute.Compound> values = (List<Attribute.Compound>) entry.getValue().getValue();

                for (Attribute.Compound value : values) {
                    List<Pair<Symbol.MethodSymbol, Attribute>> attributes = value.values;

                    ValidatorDetail validatorDetail = new ValidatorDetail();
                    for (Pair<Symbol.MethodSymbol, Attribute> att : attributes) {
                        Attribute snd = att.snd;
                        switch (att.fst.getSimpleName().toString()) {
                            case "source":
                                validatorDetail.setSource((String) snd.getValue());
                                break;
                            case "condition":
                                VariableElement el = (VariableElement) snd.getValue();
                                validatorDetail.setCondition(ConditionRule.valueOf(el.getSimpleName().toString()));
                                break;
                            case "pattern":
                                VariableElement pattern = (VariableElement) snd.getValue();
                                validatorDetail.setPattern(Pattern.valueOf(pattern.getSimpleName().toString()));
                                break;
                            case "targetException":
                                TypeMirror typeMirror = (TypeMirror) snd.getValue();
                                Class<?> aClass = Class.forName(typeMirror.toString());
                                validatorDetail.setException((Class<? extends Throwable>) aClass);
                                break;
                            case "errorMessage":
                                validatorDetail.setErrorMessage((String) snd.getValue());
                                break;
                        }
                    }
                    validatorDetails.add(validatorDetail);
                }
            }
        }
        return validatorDetails;
    }
}


