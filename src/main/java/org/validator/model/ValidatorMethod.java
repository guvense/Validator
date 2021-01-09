package org.validator.model;

public class ValidatorMethod {
    private String methodName;
    private String argumentType;
    private ValidatorDetail validatorDetail;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgumentType() {
        return argumentType;
    }

    public void setArgumentType(String argumentType) {
        this.argumentType = argumentType;
    }

    public ValidatorDetail getValidatorDetail() {
        return validatorDetail;
    }

    public void setValidatorDetail(ValidatorDetail validatorDetail) {
        this.validatorDetail = validatorDetail;
    }
}
