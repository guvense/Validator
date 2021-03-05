package ${conditionModel.packageName};

<#list conditionModel.imports as import>
    import ${import};
</#list>

import static org.validator.generator.constant.ConditionRule.*;
import static org.validator.util.condition.ConditionRuleChecker.check;

@Component
public class ${conditionModel.className} implements ${conditionModel.interfaceName}
{

<#list conditionModel.conditionObjects as condition>

    public void ${condition.functionDeclarationModel.methodName}(${condition.functionDeclarationModel.parameterType} ${condition.functionDeclarationModel.parameterName}) {

    <#list condition.conditionModels as conditionObj>
        if(check(${conditionObj.condition},  ${condition.functionDeclarationModel.parameterName}.get${conditionObj.source}())) {

            throw new ${conditionObj.exception}("${conditionObj.exceptionCode}");

        }
    </#list>
    }
</#list>
}