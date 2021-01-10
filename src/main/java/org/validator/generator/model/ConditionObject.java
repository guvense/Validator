package org.validator.generator.model;



public class ConditionObject {

    // TODO Add as list
    private ConditionModel conditionModel;
    private FunctionDeclarationModel functionDeclarationModel;

    public ConditionModel getConditionModel() {
        return conditionModel;
    }

    public void setConditionModel(ConditionModel conditionModel) {
        this.conditionModel = conditionModel;
    }

    public FunctionDeclarationModel getFunctionDeclarationModel() {
        return functionDeclarationModel;
    }

    public void setFunctionDeclarationModel(FunctionDeclarationModel functionDeclarationModel) {
        this.functionDeclarationModel = functionDeclarationModel;
    }
}
