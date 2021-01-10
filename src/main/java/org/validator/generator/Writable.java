package org.validator.generator;


import org.validator.generator.model.BaseModel;

import java.util.Map;


public interface Writable<T extends BaseModel> {

     ModelType getType();

     Map<String, Object>  prepareData(T baseModel);

     String getTemplateName(T baseModel);
}
