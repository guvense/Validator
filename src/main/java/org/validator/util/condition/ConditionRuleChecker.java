package org.validator.util.condition;

import org.validator.generator.constant.ConditionRule;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ConditionRuleChecker {
    private static Map<ConditionRule, Predicate<Object>> CONDITION_RULES = new HashMap<>();
    private static Map<ConditionRule, BiPredicate<Number, Number>> CONDITION_NUMBER_RULES = new HashMap<>();

    static {
        CONDITION_RULES.put(ConditionRule.IsNotNull, ConditionRuleChecker::isNotNull);
        CONDITION_RULES.put(ConditionRule.IsNull, ConditionRuleChecker::isNull);
        CONDITION_RULES.put(ConditionRule.IsBlank, ConditionRuleChecker::isBlank);
        CONDITION_RULES.put(ConditionRule.IsNotBlank, ConditionRuleChecker::isNotBlank);
        CONDITION_RULES.put(ConditionRule.IsPositive, ConditionRuleChecker::isPositive);
        CONDITION_RULES.put(ConditionRule.IsZero, ConditionRuleChecker::isZero);
        CONDITION_RULES.put(ConditionRule.IsNegative, ConditionRuleChecker::isNegative);


        //CONDITION_NUMBER_RULES.put(ConditionRule.IsGreaterThan, ConditionRuleChecker::isGreaterThan);
    }

    private static boolean isNotNull(Object... value) {
        return value[0] != null ? Boolean.TRUE : Boolean.FALSE;
    }

    private static boolean isNull(Object... value) {
        return value[0] == null ? Boolean.TRUE : Boolean.FALSE;
    }

    private static boolean isPositive(Object... value) {
         Number val = (Number)value[0];
         return val.doubleValue() > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private static boolean isZero(Object... value) {
        Number val = (Number)value[0];
        return val.doubleValue() == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private static boolean isNegative(Object... value) {
        Number val = (Number)value[0];
        return val.doubleValue() < 0 ? Boolean.TRUE : Boolean.FALSE;
    }


    private static boolean isBlank(Object... value) {
        return value[0] == null || value[0] == ""  ? Boolean.TRUE : Boolean.FALSE;
    }

    private static boolean isNotBlank(Object... value) {
        return value[0] != null && value[0] != ""  ? Boolean.TRUE : Boolean.FALSE;
    }

    private static boolean isGreaterThan(Number value, Number th) {
        return value.doubleValue() > th.doubleValue() ? Boolean.TRUE : Boolean.FALSE;
    }

    public static boolean check(ConditionRule rule, Object o) {
        return CONDITION_RULES.get(rule).test(o);
    }

    public static boolean biCheck(ConditionRule rule, Number o, Number th) {
        return CONDITION_NUMBER_RULES.get(rule).test(o,th);
    }

}
