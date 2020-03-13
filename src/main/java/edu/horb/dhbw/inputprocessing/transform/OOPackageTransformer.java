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

package edu.horb.dhbw.inputprocessing.transform;

import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOPackageTransformer
        extends CachingTransformer<UMLPackage, OOPackage> {
    /**
     * @param registry The registry to use.
     */
    public OOPackageTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOPackage> transform(final @NonNull List<?> elements) {

        List<UMLPackage> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof UMLPackage) {
                classes.add((UMLPackage) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOPackage doTransformation(@NonNull final UMLPackage element) {

        String id = element.getId();
        log.info("Beginning transformation of [{}]", id);
        log.debug("Set id for [{}]", id);
        OOPackage ooPackage = new OOPackage();
        cache.put(id, ooPackage);
        ooPackage.setId(id);
        log.debug("Set name for [{}]", id);
        ooPackage.setName(element.getName());
        log.debug("Set container for [{}]", id);
        UMLPackage umlPackage = element.getNestingPackage();
        if (umlPackage == null) {
            ooPackage.setContainer(null);
        } else {
            ooPackage.setContainer(transform(umlPackage));
        }
        return ooPackage;
    }
}
