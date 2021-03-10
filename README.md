# Validator 

<img src="./validator.svg" width="200" height="200>

Validator is an annotation that validate your objects! That use annotation processing which is not effect your performance! It generates codes during compile time.
Declare only your intention!


You can write your validation in an interface and let validator generates your implementation.
```


public interface PersonValidator {


    @Valid(source = "name",
            errorMessage = "Name is not blank",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsNotBlank)
    @Valid(source = "age",
            errorMessage = "Age should be positive",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsPositive)
    @Valid(source = "count",
            errorMessage = "Count should be zero",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsZero)
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

        if(check(IsNotBlank,  person.getName())) {

            throw new java.lang.NullPointerException("Name is not blank");

        }
        if(check(IsPositive,  person.getAge())) {

            throw new java.lang.NullPointerException("Age should be positive");

        }
        if(check(IsZero,  person.getCount())) {

            throw new java.lang.NullPointerException("Count should be zero");

        }
    }
}


```
