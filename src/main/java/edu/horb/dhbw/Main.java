package edu.horb.dhbw;

import edu.horb.dhbw.config.SDMetrics.SDMetricsConfig;
import edu.horb.dhbw.config.thymeleaf.ThymeleafConfig;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args)
            throws Exception {

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

        String xmiFile = "src/main/resources/xmi/2SMs.xmi";       // XMI file with the UML model


        Writer fileWriter = new FileWriter("./target/test.java");
        fileWriter.write(System.currentTimeMillis() + "\n");
        engine.process("test", context, fileWriter);
        System.out.println(engine.process("test", context));

        System.out.println(SDMetricsConfig.parseXMI(xmiFile));
    }
}
