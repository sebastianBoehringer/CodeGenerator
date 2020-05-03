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
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataTypeImpl;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class DatatypeRestructurer
        extends AbstractCachingRestructurer<DataType> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public DatatypeRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "datatype");
    }

    @Override
    public DataType restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of DataType [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading DataType from cache", id);
            return processed.get(id);
        }
        DataType dataType = new DataTypeImpl();
        dataType.setId(id);
        processed.putIfAbsent(id, dataType);

        log.debug("Processing umltype for DataType [{}]", id);
        dataType.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing name for DataType [{}]", id);
        dataType.setName(element.getName());

        log.debug("Processing abstract for DataType [{}]", id);
        dataType.setIsAbstract(
                Boolean.valueOf(element.getPlainAttribute("abstract")));

        log.debug("Processing leaf for DataType [{}]", id);
        dataType.setIsFinalSpecialization(
                Boolean.valueOf(element.getPlainAttribute("leaf")));

        log.debug("Processing visibility for DataType [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        dataType.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.debug("Processing generalization for DataType [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        dataType.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        log.debug("Processing substitution for DataType [{}]", id);
        Collection<ModelElement> substitutions =
                (Collection<ModelElement>) element
                        .getSetAttribute("substitution");
        dataType.setSubstitution(
                delegateMany(substitutions, Substitution.class));

        log.debug("Processing ownedattributes for DataType [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        dataType.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.debug("Processing ownedoperations for DataType [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        dataType.setOwnedOperation(delegateMany(operations, Operation.class));

        log.debug("Processing elementimports for DataType [{}]", id);
        Collection<ModelElement> elementImports =
                (Collection<ModelElement>) element
                        .getSetAttribute("elementimports");
        dataType.setElementImport(
                delegateMany(elementImports, ElementImport.class));

        log.debug("Processing packageimports for DataType [{}]", id);
        Collection<ModelElement> packageImports =
                (Collection<ModelElement>) element
                        .getSetAttribute("packageimports");
        dataType.setPackageImport(
                delegateMany(packageImports, PackageImport.class));

        log.info("Completed restructuring of DataType [{}]", id);
        return dataType;
    }
}
