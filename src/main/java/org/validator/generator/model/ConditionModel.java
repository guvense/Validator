package org.validator.generator.model;

import org.validator.generator.constant.ConditionRule;

public class ConditionModel extends ExceptionModel {

    private ConditionRule condition;
    private String source;

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

    @Override
    public String toString() {
        return "ConditionModel{" +
                "condition=" + condition +
                ", source='" + source + '\'' +
                '}';
    }
}
