/*
 * Copyright (c) 2020 Sebastian Boehringer.
 *  This file is part of the CodeGenerator.
 *
 *  CodeGenerator is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 *  option) any later version.
 * CodeGenerator is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 * along with CodeGenerator.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.horb.dhbw.templating;

import edu.horb.dhbw.datacore.model.Language;
import edu.horb.dhbw.exception.CodeGenerationException;
import edu.horb.dhbw.util.Config;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Files;

@NoArgsConstructor
@Slf4j
public final class ThymeleafAdapter implements ITemplateEngineAdapter {
    /**
     * The context used by the template engine.
     */
    private Context context;
    /**
     * The thymeleaf template engine this adapter adapts.
     */
    private TemplateEngine engine;
    /**
     * The language that this adapter produces.
     */
    private Language lang;

    @Override
    public void initialize(final Language language) {

        lang = language;
        log.info("Started configuration for Thymeleaf Engine");
        context = new Context();

        log.info("Configuring fileresolver");
        FileTemplateResolver fileResolver = new FileTemplateResolver();
        fileResolver.setTemplateMode(TemplateMode.TEXT);
        String separator = FileSystems.getDefault().getSeparator();
        String prefix =
                language.getTemplateLocation().toAbsolutePath().toString()
                        + separator;
        fileResolver.setPrefix(prefix);
        log.info("Set fileresolver to search with prefix [{}]", prefix);
        fileResolver.setSuffix(language.getExtension());
        log.info("Set suffix to [{}]", language.getExtension());
        fileResolver.setCheckExistence(true);
        fileResolver.setCharacterEncoding("UTF8");
        fileResolver.setOrder(1);

        log.info("Finished configuring file resolver, starting "
                         + "configuration of classloadresolver now");
        ClassLoaderTemplateResolver classLoaderResolver =
                new ClassLoaderTemplateResolver();
        classLoaderResolver.setTemplateMode(TemplateMode.TEXT);
        classLoaderResolver.setPrefix(
                "/templates/" + language.getName().toLowerCase() + separator);
        classLoaderResolver.setCharacterEncoding("UTF8");
        classLoaderResolver.setSuffix(language.getExtension());
        classLoaderResolver.setCheckExistence(true);
        classLoaderResolver.setOrder(2);

        log.info("Creating engine");
        engine = new TemplateEngine();
        engine.addTemplateResolver(fileResolver);
        engine.addTemplateResolver(classLoaderResolver);
        log.info("Finished initializing ThymeleafAdapter");
    }

    @Override
    public void addToContext(final String name, final Object o) {

        context.setVariable(name, o);
    }

    @Override
    public void process(final String templateName, final String fileName)
            throws CodeGenerationException {

        String outputFileName =
                Config.CONFIG.getOutputDirectory().toString() + FileSystems
                        .getDefault().getSeparator() + fileName + lang
                        .getExtension();

        try {
            Files.createDirectories(Config.CONFIG.getOutputDirectory());
            Writer writer = new BufferedWriter(new FileWriter(outputFileName));
            engine.process(templateName, context, writer);
            context.clearVariables();
        } catch (IOException e) {
            throw new CodeGenerationException(e);
        }
    }
}
