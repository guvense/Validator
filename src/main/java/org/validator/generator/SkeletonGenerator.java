package org.validator.generator;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SkeletonGenerator {

    private Configuration configuration = new Configuration(new Version("2.3.23"));

    Map<String, Object> templateData = new HashMap<>();
    Template template;

    public SkeletonGenerator() throws IOException {
        init();
    }
    private void init() throws IOException {
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        template = configuration.getTemplate("template.ftl");
    }

    public void fillData(String packageName, String methodName, String className, String interfaceName, String parameterType, String parameterName) {
        templateData.put("packageName", packageName);
        templateData.put("className", className);
        templateData.put("methodName", methodName);
        templateData.put("interfaceName", interfaceName);
        templateData.put("parameterType", parameterType);
        templateData.put("parameterName", parameterName);
    }

    public void write(JavaFileObject builderFile) throws IOException {
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(templateData, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}
