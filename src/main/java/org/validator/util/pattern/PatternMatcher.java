package org.validator.util.pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class PatternMatcher {

    private static Map<org.validator.generator.constant.Pattern, Predicate<Object>> PATTERN_MATCHER = new HashMap<>();

    static {
        PATTERN_MATCHER.put(org.validator.generator.constant.Pattern.EMAIL, org.validator.util.pattern.PatternMatcher::validateIsNotEmail);
    }


    private static boolean validateIsNotEmail(Object... value) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return !pattern.matcher((String) value[0]).matches();
    }

    public static boolean validate(org.validator.generator.constant.Pattern rule, Object o) {
        return PATTERN_MATCHER.get(rule).test(o);
    }

}
