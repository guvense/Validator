package org.validator.generator.constant;

public enum ConditionRule {
    NOOP(""),
    IsNotNull("null == ");

    private final String conditionRule;

    ConditionRule(String conditionRule) {
        this.conditionRule = conditionRule;
    }

    public String getConditionRule() {
        return conditionRule;
    }
}
