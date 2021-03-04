# Validator
Validator is an annotation that validate your fields! That use annotation processing which is not effect your performance! 
Declare only your intention!


```


    @Valid(source = "name",
            errorMessage = "name.is.not.blank",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsNotNull)
     void validate(Person person);
            
```
