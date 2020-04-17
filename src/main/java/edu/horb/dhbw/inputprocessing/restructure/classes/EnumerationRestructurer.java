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

package edu.horb.dhbw.inputprocessing.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.EnumerationLiteral;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashSet;

@Slf4j
public final class EnumerationRestructurer
        extends AbstractCachingRestructurer<Enumeration> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public EnumerationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "enumeration");
    }

    @Override
    public Enumeration restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Enumeration [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Enumeration from cache",
                     id);
            return processed.get(id);
        }
        Enumeration enumeration = new Enumeration();
        processed.put(id, enumeration);
        enumeration.setId(id);

        log.debug("Processing name for Enumeration [{}]", id);
        String name = element.getPlainAttribute("name");
        enumeration.setName(name);

        log.debug("Processing ownedliterals for Enumeration [{}]", id);
        Collection<ModelElement> literals = (Collection<ModelElement>) element
                .getSetAttribute("ownedliterals");
        enumeration.setOwnedLiteral(new LinkedHashSet<>(
                delegateMany(literals, EnumerationLiteral.class)));

        log.debug("Processing ownedattributes for Enumeration [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        enumeration.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.debug("Processing ownedoperations for Enumeration [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        enumeration
                .setOwnedOperation(delegateMany(operations, Operation.class));

        log.debug("Processing generalizations for Enumeration [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        enumeration.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        log.debug("Processing abstract for Enumeration [{}]", id);
        String isAbstract = element.getPlainAttribute("abstract");
        enumeration.setIsAbstract(Boolean.valueOf(isAbstract));

        log.debug("Processing leaf for Enumeration [{}]", id);
        String leaf = element.getPlainAttribute("leaf");
        enumeration.setIsFinalSpecialization(Boolean.valueOf(leaf));

        log.debug("Processing visibility for Enumeration [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        VisibilityKind kind =
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility);
        enumeration.setVisibility(kind);

        log.debug("Processing substitution for Enumeration [{}]", id);
        Collection<ModelElement> substitutions =
                (Collection<ModelElement>) element
                        .getSetAttribute("substitution");
        enumeration.setSubstitution(
                delegateMany(substitutions, Substitution.class));
        log.info("Completed restructuring of Enumeration [{}]", id);
        return enumeration;
    }
}
