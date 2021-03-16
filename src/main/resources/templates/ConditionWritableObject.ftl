package ${conditionModel.packageName};

<#list conditionModel.imports as import>
    import ${import};
</#list>

import org.springframework.stereotype.Component;

import static org.validator.generator.constant.ConditionRule.*;
import org.validator.util.condition.ConditionRuleChecker;

import static org.validator.generator.constant.Pattern.*;
import org.validator.util.pattern.PatternMatcher;


@Component
public class ${conditionModel.className} implements ${conditionModel.interfaceName}
{

<#list conditionModel.conditionObjects as condition>

    public void ${condition.functionDeclarationModel.methodName}(${condition.functionDeclarationModel.parameterType} ${condition.functionDeclarationModel.parameterName}) {

    <#list condition.conditionModels as conditionObj>
        if(ConditionRuleChecker.check(${conditionObj.condition},  ${condition.functionDeclarationModel.parameterName}.get${conditionObj.source}())) {
            throw new ${conditionObj.exception}("${conditionObj.exceptionCode}");
        }
    </#list>

    <#list condition.patternModels as patternObj>
        if(PatternMatcher.validate(${patternObj.pattern},  ${condition.functionDeclarationModel.parameterName}.get${patternObj.source}())) {
            throw new ${patternObj.exception}("${patternObj.exceptionCode}");
        }
    </#list>
    }
</#list>
}