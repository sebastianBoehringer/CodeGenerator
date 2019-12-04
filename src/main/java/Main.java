import Config.Thymeleaf.ThymeleafConfig;
import datacore.Method;
import datacore.VisibilityModifier;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args)
            throws IOException {

        TemplateEngine engine = new TemplateEngine();
        engine.addTemplateResolver(ThymeleafConfig.textTemplateResolver(""));
        Context context = new Context();
        List<Method> methodList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Method method = new Method("methode" + i);
            method.setModifier(VisibilityModifier.PRIVATE);
            methodList.add(method);
        }
        context.setVariable("methods", methodList);

        String text = engine.process("test", context);

        Writer fileWriter = new FileWriter("./target/test.java");
        engine.process("test", context, fileWriter);
    }
}
