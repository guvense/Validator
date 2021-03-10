package org.validator.generator.writer;

import org.validator.generator.model.BaseModel;
import org.validator.generator.model.ConditionObject;

import java.util.List;

public class ConditionWritableObject extends BaseModel {

    List<ConditionObject> conditionObjects;

    public List<ConditionObject> getConditionObjects() {
        return conditionObjects;
    }

    public void setConditionObjects(List<ConditionObject> conditionObjects) {
        this.conditionObjects = conditionObjects;
    }

    @Override
    public String toString() {
        return "ConditionWritableObject{" +
                "conditionObjects=" + conditionObjects +
                '}';
    }
}
