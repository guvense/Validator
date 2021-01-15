package ${conditionModel.packageName};

import static org.validator.generator.constant.ConditionRule.*;
import static org.validator.util.condition.ConditionRuleChecker.check;

@Component
public class ${conditionModel.className} implements ${conditionModel.interfaceName}
{

<#list conditionModel.conditionObjects as condition>

    public void ${condition.functionDeclarationModel.methodName}(${condition.functionDeclarationModel.parameterType} ${condition.functionDeclarationModel.parameterName}) {

        if(check(${condition.conditionModel.condition},  ${condition.functionDeclarationModel.parameterName}.get${condition.conditionModel.source}())) {

            throw new ${condition.conditionModel.exception}("${condition.conditionModel.exceptionCode}");

        }
    }
</#list>
}