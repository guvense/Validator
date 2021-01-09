package org.validator.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SkeletonGenerator {

    private Configuration configuration = new Configuration(new Version("2.3.23"));

    Map<String, Object> templateData = new HashMap<>();
    ProcessingEnvironment processingEnv;
    Template template;

    public SkeletonGenerator(ProcessingEnvironment processingEnv) throws IOException {
        this.processingEnv = processingEnv;
        init();
    }
    private void init() throws IOException {
        template = configuration.getTemplate("./template/template.ftl");
    }

    public void fillData(String packageName, String className, String interfaceName, String parameterType, String parameterName) {
        templateData.put("packageName", packageName);
        templateData.put("className", className);
        templateData.put("interfaceName", interfaceName);
        templateData.put("parameterType", parameterType);
        templateData.put("parameterName", parameterName);
    }

    public void write(String classPath) throws IOException {
        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(classPath);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(templateData, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
