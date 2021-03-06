package org.validator.parser.model;


import org.validator.generator.constant.ConditionRule;
import org.validator.generator.constant.Pattern;

public class ValidatorDetail {
    private String source;
    private ConditionRule condition;
    private Pattern pattern;
    private Class<? extends Throwable>  exception;
    private String errorMessage;

    public ValidatorDetail(){};

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ConditionRule getCondition() {
        return condition;
    }

    public void setCondition(ConditionRule condition) {
        this.condition = condition;
    }

    public Class<? extends Throwable> getException() {
        return exception;
    }

    public void setException(Class<? extends Throwable> exception) {
        this.exception = exception;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
