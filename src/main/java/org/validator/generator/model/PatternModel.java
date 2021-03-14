package org.validator.generator.model;

import org.validator.generator.constant.Pattern;

public class PatternModel  extends ExceptionModel {

    private Pattern pattern;
    private String source;

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
