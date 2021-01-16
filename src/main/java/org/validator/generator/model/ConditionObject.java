package org.validator.generator.model;


import java.util.List;

public class ConditionObject {


    private List<ConditionModel> conditionModels;
    private FunctionDeclarationModel functionDeclarationModel;

    public List<ConditionModel> getConditionModels() {
        return conditionModels;
    }

    public void setConditionModels(List<ConditionModel> conditionModels) {
        this.conditionModels = conditionModels;
    }

    public FunctionDeclarationModel getFunctionDeclarationModel() {
        return functionDeclarationModel;
    }

    public void setFunctionDeclarationModel(FunctionDeclarationModel functionDeclarationModel) {
        this.functionDeclarationModel = functionDeclarationModel;
    }
}
