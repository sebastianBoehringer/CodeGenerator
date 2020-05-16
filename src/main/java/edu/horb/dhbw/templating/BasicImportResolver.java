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

import edu.horb.dhbw.datacore.model.Cardinality;
import edu.horb.dhbw.datacore.model.ImportOptions;
import edu.horb.dhbw.datacore.model.Language;
import edu.horb.dhbw.datacore.model.OOField;
import edu.horb.dhbw.datacore.model.OOMethod;
import edu.horb.dhbw.datacore.model.OOParameter;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.util.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicImportResolver implements IImportResolver {
    /**
     * A placeholder that is inserted into the set if the type is primitive.
     * It is removed after processing all the imports.
     */
    private static final String PLACEHOLDER = "PLACEHOLDER";

    /**
     * {@inheritDoc}
     * Since {@link edu.horb.dhbw.datacore.model.OOBase#getFQName()} relies on
     * the configured language the param lang is ignored in this
     * implementation.
     *
     * @param lang The programming language in which the source code is
     *             generated
     * @param type The type whose imports should be resolved
     * @return A set of the fully qualified names of all types this type uses.
     */
    @Override
    public Set<String> resolveImports(final Language lang, final OOType type) {

        Set<String> fqNames = new HashSet<>(type.getImports());
        for (OOType superType : type.getSuperTypes()) {
            fqNames.add(superType.getFQName());
        }
        for (OOField field : type.getFields()) {
            fqNames.add(extractFQName(field.getType()));
            fqNames.addAll(resolveCardinality(field.getCardinality()));
        }
        for (OOMethod method : type.getMethods()) {
            fqNames.add(extractFQName(method.getReturnParam().getType()));
            fqNames.addAll(resolveCardinality(
                    method.getReturnParam().getCardinality()));
            for (OOParameter parameter : method.getParameters()) {
                fqNames.add(extractFQName(parameter.getType()));
                fqNames.addAll(resolveCardinality(parameter.getCardinality()));
            }
            for (OOType exception : method.getExceptions()) {
                fqNames.add(extractFQName(exception));
            }
        }
        fqNames.remove(PLACEHOLDER);
        fqNames.remove("");
        //A type does not need to import itself
        fqNames.remove(extractFQName(type));
        return fqNames;
    }

    private String extractFQName(final OOType type) {

        if (type.getType().equals(OOType.Type.PRIMITIVE)) {
            return PLACEHOLDER;
        } else {
            return type.getFQName();
        }
    }

    private List<String> resolveCardinality(final Cardinality cardinality) {

        ImportOptions options = Config.CONFIG.getLanguage().getImportOptions();
        return switch (cardinality) {
            case OPTIONAL -> options.getOptionalImports();
            case SINGLE -> options.getSingleImports();
            case LIST -> options.getListImports();
            case BAG -> options.getBagImports();
            case SET -> options.getSetImports();
            case ORDERED_SET -> options.getOrderedSetImports();
        };
    }
}
