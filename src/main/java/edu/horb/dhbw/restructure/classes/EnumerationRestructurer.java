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

package edu.horb.dhbw.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.EnumerationLiteral;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

public final class EnumerationRestructurer
        extends RestructurerBase<Enumeration> {
    /**
     * A map holding all the {@link Enumeration}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Enumeration> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public EnumerationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "enumeration");
    }

    @Override
    public Enumeration restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            return ALREADY_PROCESSED.get(id);
        }
        Enumeration enumeration = new Enumeration();
        enumeration.setId(id);

        Collection<ModelElement> literals = (Collection<ModelElement>) element
                .getRefAttribute("ownedliterals");
        enumeration.setOwnedLiteral(new LinkedHashSet<>(
                delegateMany(literals, EnumerationLiteral.class)));

        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        enumeration.setOwnedAttribute(delegateMany(attributes, Property.class));

        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        enumeration
                .setOwnedOperation(delegateMany(operations, Operation.class));

        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        enumeration.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        String isAbstract = element.getPlainAttribute("abstract");
        enumeration.setIsAbstract(Boolean.valueOf(isAbstract));

        String leaf = element.getPlainAttribute("leaf");
        enumeration.setIsFinalSpecialization(Boolean.valueOf(leaf));

        String visibility = element.getPlainAttribute("visibility");
        VisibilityKind kind =
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility);
        enumeration.setVisibility(kind);

        Collection<ModelElement> substitutions =
                (Collection<ModelElement>) element
                        .getSetAttribute("substitution");
        enumeration.setSubstitution(
                delegateMany(substitutions, Substitution.class));

        //TODO collaborationuses ?
        ALREADY_PROCESSED.put(enumeration.getId(), enumeration);

        return enumeration;
    }

    @Override
    public Optional<Enumeration> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
