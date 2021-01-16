package org.validator.parser.model;

import java.util.List;

public class ValidatorMethod {
    private String methodName;
    private String argumentType;
    private List<ValidatorDetail> validatorDetails;

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

    public List<ValidatorDetail> getValidatorDetails() {
        return validatorDetails;
    }

    public void setValidatorDetails(List<ValidatorDetail> validatorDetails) {
        this.validatorDetails = validatorDetails;
    }
}
