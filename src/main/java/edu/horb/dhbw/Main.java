package edu.horb.dhbw;

import edu.horb.dhbw.util.Config;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@Slf4j
public final class Main {

    private Main() {

    }

    public static void main(final String[] args)
            throws Exception {

        CodeGenerator gen = new CodeGenerator(
                Path.of("src/main/resources/default.properties"));
        gen.generateCode(Path.of("src/test/resources/uml.xmi"),
                         Config.CONFIG.getLanguage());
    }
}
