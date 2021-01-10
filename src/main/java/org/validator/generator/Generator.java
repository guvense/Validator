package org.validator.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.validator.generator.model.BaseModel;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Generator {

    private Configuration configuration = new Configuration(new Version("2.3.23"));
    Template template;

    public Generator() {
        init();
    }
    private void init(){
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
    }

    @SuppressWarnings("ALL")
    public void write(JavaFileObject builderFile, BaseModel baseModel, ModelType modelType) throws IOException {

        Writable writable = WriterFactory.getWritable(modelType);
        String templateName = writable.getTemplateName(baseModel);
        Map map = writable.prepareData(baseModel);
        template = configuration.getTemplate(templateName + ".ftl");
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(map, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
