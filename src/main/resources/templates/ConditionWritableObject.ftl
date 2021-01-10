package ${conditionModel.packageName};

@Component
public class ${conditionModel.className} implements ${conditionModel.interfaceName}
{

<#list conditionModel.conditionObjects as condition>

    public void ${condition.functionDeclarationModel.methodName}(${condition.functionDeclarationModel.parameterType} ${condition.functionDeclarationModel.parameterName}) {

        if(${condition.conditionModel.condition.getConditionRule()}  ${condition.functionDeclarationModel.parameterName}.get${condition.conditionModel.source}()) {

            throw new ${condition.conditionModel.exception}("${condition.conditionModel.exceptionCode}");

        }

    }
</#list>
}