package org.validator.generator.model;

import java.util.List;

public class BaseModel {
    private String packageName;
    private String className;
    private String interfaceName;
    private List<String> imports;

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", imports=" + imports +
                '}';
    }
}
