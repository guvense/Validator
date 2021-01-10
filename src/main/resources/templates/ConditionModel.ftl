package ${condition.packageName};

@Component
public class ${condition.className} implements ${condition.interfaceName}
{
    public void ${condition.methodName}(${condition.parameterType} ${condition.parameterName}) {

        if(${condition.condition.getConditionRule()} get${condition.source}()) {

            throw new ${condition.exception}("${condition.exceptionCode}");

        }
    }
}