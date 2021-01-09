package org.validator.model;


import org.validator.constant.Condition;

public class ValidatorDetail {
    private String source;
    private Condition condition;
    private Class<? extends Throwable>  exception;
    private String errorMessage;


    public ValidatorDetail(){};

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
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
}
