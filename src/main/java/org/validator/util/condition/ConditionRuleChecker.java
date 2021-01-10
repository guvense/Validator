package org.validator.util.condition;

import org.validator.generator.constant.ConditionRule;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ConditionRuleChecker {
    private static Map<ConditionRule, Predicate<Object>> CONDITION_RULES = new HashMap<>();

    static {
        CONDITION_RULES.put(ConditionRule.IsNotNull,ConditionRuleChecker::isNotNull);
        CONDITION_RULES.put(ConditionRule.IsNull, ConditionRuleChecker::isNull);
    }
    private static boolean isNotNull(Object value) {
        return value != null ? Boolean.TRUE : Boolean.FALSE;
    }

    private static boolean isNull(Object value) {
        return value == null ? Boolean.TRUE : Boolean.FALSE;
    }

    public static boolean check(ConditionRule rule, Object o) {
        return CONDITION_RULES.get(rule).test(o);
    }



}
