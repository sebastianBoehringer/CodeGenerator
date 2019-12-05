package edu.horb.dhbw;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.horb.dhbw.config.jackson.JacksonConfig;
import edu.horb.dhbw.config.thymeleaf.ThymeleafConfig;
import edu.horb.dhbw.datacore.classification.Operation;
import edu.horb.dhbw.datacore.commonstructure.Constraint;
import edu.horb.dhbw.datacore.packages.Model;
import edu.horb.dhbw.datacore.values.OpaqueExpression;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args)
            throws IOException {

        TemplateEngine engine = ThymeleafConfig.templateEngine("");
        Context context = new Context();
        List<Operation> operations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Operation op = new Operation();
            op.setName("operation" + i);
            op.setBodyCondition(new Constraint());
            operations.add(op);
        }
        context.setVariable("methods", operations);
        OpaqueExpression opaque = new OpaqueExpression();
        opaque.setName("MARTIN");
        opaque.setBody(Arrays.asList("martin", "georg", "hadododdo"));
        context.setVariable("opa", "opaque");
        XmlMapper mapper = JacksonConfig.xmlMapper();
        FileInputStream classesXMI = new FileInputStream("./src/main/resources/xmi/classes.xmi");
        FileInputStream stateMachineXMI = new FileInputStream("./src/main/resources/xmi" +
                "/statemachine.xmi");
        Model model = mapper.readValue(classesXMI, Model.class);
        System.out.println(model.toString());
        Model state = mapper.readValue(stateMachineXMI, Model.class);
        Writer fileWriter = new FileWriter("./target/test.java");
        fileWriter.write(System.currentTimeMillis() + "\n");
        engine.process("test", context, fileWriter);
        System.out.println(state.toString());

    }
}
