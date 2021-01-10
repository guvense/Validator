package org.validator.parser;


import org.validator.generator.constant.ConditionRule;
import org.validator.parser.model.ValidatorDetail;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;

public class AnnotationFieldParser {

    @SuppressWarnings("unchecked")
    public static ValidatorDetail parseValidatorDetail(Element element) throws ClassNotFoundException {

        ValidatorDetail validatorDetail = new ValidatorDetail();

        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                    = annotationMirror.getElementValues();
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                    : elementValues.entrySet()) {
                String key = entry.getKey().getSimpleName().toString();
                Object value = entry.getValue().getValue();
                switch (key) {
                    case "source":
                        validatorDetail.setSource((String)value);
                        break;
                    case "condition":
                        VariableElement el = (VariableElement) value;
                        validatorDetail.setCondition(ConditionRule.valueOf(el.getSimpleName().toString()));
                        break;
                    case "targetException":
                        TypeMirror typeMirror = (TypeMirror) value;
                        Class<?> aClass = Class.forName(typeMirror.toString());
                        validatorDetail.setException((Class<? extends Throwable>)aClass);
                        break;
                    case "errorMessage":
                        validatorDetail.setErrorMessage((String)value);
                        break;
                }
            }
        }
        return validatorDetail;
    }

}
