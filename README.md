# Validator
Validator is an annotation that validate your fields!


```


    @Valid(source = "name",
            errorMessage = "name.is.not.blank",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsNotNull)
     void validate(Person person);
            
```
