package org.validator.generator;

import org.validator.generator.model.ConditionModel;

import java.util.HashMap;
import java.util.Map;

public class ConditionWriter implements Writable<ConditionModel> {

    public ConditionWriter(){};

    @Override
    public ModelType getType() {
        return ModelType.CONDITION;
    }

    @Override
    public Map<String, Object> prepareData(ConditionModel conditionModel) {
        Map<String, Object> templateData = new HashMap<>();
        conditionModel.setCondition(conditionModel.getCondition());
        conditionModel.setClassName(conditionModel.getClassName());
        conditionModel.setInterfaceName(conditionModel.getInterfaceName());
        conditionModel.setMethodName(conditionModel.getMethodName());
        conditionModel.setPackageName(conditionModel.getPackageName());
        conditionModel.setParameterName(conditionModel.getParameterName());
        conditionModel.setParameterType(conditionModel.getParameterType());
        templateData.put("condition", conditionModel);
        return templateData;
    }

    @Override
    public String getTemplateName(ConditionModel baseModel) {
        return baseModel.getClass().getSimpleName();
    }

}
