package org.validator.generator;


public class WriterFactory {
    @SuppressWarnings("rawtypes")
    public  static Writable getWritable(ModelType modelType) {
        if(ModelType.CONDITION.equals(modelType)) {
            return new ConditionWriter();
        }else {
            throw  new RuntimeException();
        }
    }
}
