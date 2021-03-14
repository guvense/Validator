package org.validator.generator.model;

import java.util.List;

public class ConditionObject {

    private List<ConditionModel> conditionModels;
    private List<PatternModel> patternModels;
    private List<String> imports;
    private FunctionDeclarationModel functionDeclarationModel;

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

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

    public List<PatternModel> getPatternModels() {
        return patternModels;
    }

    public void setPatternModels(List<PatternModel> patternModels) {
        this.patternModels = patternModels;
    }

    @Override
    public String toString() {
        return "ConditionObject{" +
                "conditionModels=" + conditionModels +
                ", imports=" + imports +
                ", functionDeclarationModel=" + functionDeclarationModel +
                '}';
    }
}
