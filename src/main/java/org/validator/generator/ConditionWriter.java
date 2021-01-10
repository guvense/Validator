package org.validator.generator;

import org.validator.generator.writer.ConditionWritableObject;

import java.util.HashMap;
import java.util.Map;

public class ConditionWriter implements Writable<ConditionWritableObject> {

    public ConditionWriter(){};

    @Override
    public ModelType getType() {
        return ModelType.CONDITION;
    }

    @Override
    public Map<String, Object> prepareData(ConditionWritableObject conditionModel) {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("conditionModel", conditionModel);
        return templateData;
    }

    @Override
    public String getTemplateName(ConditionWritableObject baseModel) {
        return baseModel.getClass().getSimpleName();
    }

}
