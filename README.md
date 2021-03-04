# Validator
Validator is an annotation that validate your fields! That use annotation processing which is not effect your performance! It generates codes during compile time.
Declare only your intention!


You can write your validation in a interface and let validator generates your implementation.
```


public interface PersonValidator {

    @Valid(source = "name",
            errorMessage = "Name is not blank",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsNotBlank)
    @Valid(source = "age",
            errorMessage = "Age is not null",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsNotNull)
    @Valid(source = "count",
            errorMessage = "Count not be negative",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsPositive)
    void validate(Person person);

}
            
```


Validator will generate


```

package validator;

    import model.Person;

import static org.validator.generator.constant.ConditionRule.*;
import static org.validator.util.condition.ConditionRuleChecker.check;

@Component
public class PersonValidatorImpl implements PersonValidator
{


    public void validate(Person person) {

        if(check(IsPositive,  person.getCount())) {

            throw new java.lang.NullPointerException("Count not be negative");

        }
        if(check(IsPositive,  person.getCount())) {

            throw new java.lang.NullPointerException("Count not be negative");

        }
        if(check(IsPositive,  person.getCount())) {

            throw new java.lang.NullPointerException("Count not be negative");

        }
    }
}

```
