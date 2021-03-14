# Validator 

<p align="center">
<img src="./validator.svg" width="200">
 </p>

Validator is an annotation that validate your objects! That use annotation processing which is not effect your performance! It generates codes during compile time.
Declare only your intention!


- Condition Checker
- Pattern Matcher


You can write your validation in an interface and let validator generates your implementation.
```

public interface PersonValidator {


    @Valid(source = "name",
            errorMessage = "Name is not blank",
            targetException =
                    NullPointerException.class,
            condition = ConditionRule.IsNotBlank)
    @Valid(source = "age",
            errorMessage = "Age should be positive",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsPositive)
    @Valid(source = "count",
            errorMessage = "Count should be zero",
            targetException = NullPointerException.class,
            condition = ConditionRule.IsZero)
    @Valid(
            source = "email",
            errorMessage = "Not valid email",
            targetException = NullPointerException.class,
            pattern = Pattern.EMAIL
    )
    void validate(Person person);

}


            
```


Validator will generate


```
package validator;

    import model.Person;


import static org.validator.generator.constant.ConditionRule.*;
import org.validator.util.condition.ConditionRuleChecker;

import static org.validator.generator.constant.Pattern.*;
import org.validator.util.pattern.PatternMatcher;


@Component
public class PersonValidatorImpl implements PersonValidator
{


    public void validate(Person person) {

        if(ConditionRuleChecker.check(IsNotBlank,  person.getName())) {
            throw new java.lang.NullPointerException("Name is not blank");
        }
        if(ConditionRuleChecker.check(IsPositive,  person.getAge())) {
            throw new java.lang.NullPointerException("Age should be positive");
        }
        if(ConditionRuleChecker.check(IsZero,  person.getCount())) {
            throw new java.lang.NullPointerException("Count should be zero");
        }

        if(PatternMatcher.validate(EMAIL,  person.getEmail())) {
            throw new java.lang.NullPointerException("Not valid email");
        }
    }
}

```
