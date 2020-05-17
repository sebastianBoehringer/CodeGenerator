package edu.horb.dhbw;

import edu.horb.dhbw.exception.CodeGenerationException;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public final class Main {

    private Main() {

    }

    public static void main(final String[] args)
            throws Exception {

        List<String> fileNames = new ArrayList<>(Arrays.asList(args));
        fileNames.removeIf(s -> !s.endsWith(".xmi") && !s.endsWith(".uml"));

        CodeGenerator gen = new CodeGenerator();
        for (String name : fileNames) {
            System.out.println("Generating code for file " + name);
            try {
                gen.generateCode(Path.of(name));
            } catch (CodeGenerationException e) {
                e.printStackTrace();
                System.out.println("Codegeneration failed for file " + name);
            }
        }
        if (fileNames.isEmpty()) {
            System.out.println("Please enter the path to a file with an .xmi "
                               + "or .uml extension");
        }
    }
}
