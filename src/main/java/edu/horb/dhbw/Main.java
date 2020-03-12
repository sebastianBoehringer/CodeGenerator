package edu.horb.dhbw;

import com.sdmetrics.model.Model;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.templating.ITemplateEngineAdapter;
import edu.horb.dhbw.templating.ThymeleafAdapter;
import edu.horb.dhbw.util.Config;
import edu.horb.dhbw.util.SDMetricsUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class Main {

    private Main() {

    }

    public static void main(final String[] args)
            throws Exception {

        CodeGenerator gen = new CodeGenerator(
                Path.of("C:/Users/SBG/IdeaProjects/CodeGenerator/src"
                                + "/main/resources/default.properties"));
        ITemplateEngineAdapter adapter = new ThymeleafAdapter();
        gen.generateCode(Path.of("src/main/resources/xmi/uml.xmi"),
                         Config.CONFIG.getLanguage());

        // mediator.restructure(model);
        //Writer fileWriter = new
        // FileWriter("./target/test
        // .java");
        //fileWriter.write(System
        // .currentTimeMillis() + "\n");
        //engine.process("test", context, fileWriter);
        //System.out.println(engine.process("test", context));
    }
}