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

package edu.horb.dhbw.inputprocessing.restructure.packaging;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.packages.UMLPackageImpl;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public final class PackageRestructurer
        extends AbstractCachingRestructurer<UMLPackage> {

    private static final String PROCESSED_METAMODEL_ELEMENT = "package";

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public PackageRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "package");
    }

    @Override
    public UMLPackage restructure(@NonNull final ModelElement element) {

        String umlType = XMIUtil.getUMLType(element);
        if (!PROCESSED_METAMODEL_ELEMENT.equals(umlType)) {
            log.info("Trying to delegate from package to specialized type "
                     + "for [{}]", umlType);
            Class<? extends UMLPackage> aClass =
                    LookupUtil.packageFromUMLType(umlType);
            if (aClass == null) {
                log.warn("Did not find matching class for [{}], restructuring "
                         + "as class", umlType);
            } else {
                return delegateRestructuring(element, aClass);
            }
        }
        String id = element.getXMIID();
        log.info("Beginning restructuring of Package [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] for package in cache", id);
            return processed.get(id);
        }
        UMLPackage umlPackage = new UMLPackageImpl();
        processed.putIfAbsent(id, umlPackage);
        umlPackage.setId(id);

        log.debug("Processing umltype for Package [{}]", id);
        umlPackage.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing name for Package [{}]", id);
        String name = element.getPlainAttribute("name");
        umlPackage.setName(name);

        log.debug("Processing ownedmember for Package [{}]", id);
        Collection<ModelElement> members = (Collection<ModelElement>) element
                .getSetAttribute("ownedmembers");
        umlPackage.setPackagedElement(
                delegateMany(members, PackageableElement.class));
        umlPackage.getPackagedElement().removeIf(Objects::isNull);
        umlPackage.getPackagedElement().forEach(m -> m.setOwner(umlPackage));

        log.debug("Processing ownedType for Package [{}]", id);
        umlPackage.setOwnedType(umlPackage.getPackagedElement().stream()
                                        .filter(p -> p instanceof Type)
                                        .map(p -> (Type) p)
                                        .collect(Collectors.toList()));
        for (Type type : umlPackage.getOwnedType()) {
            type.setAPackage(umlPackage);
        }
        log.debug("Processing nestedPackage for Package [{}]", id);
        umlPackage.setNestedPackage(umlPackage.getPackagedElement().stream()
                                            .filter(p -> p instanceof UMLPackage)
                                            .map(p -> (UMLPackage) p)
                                            .collect(Collectors.toList()));
        for (UMLPackage aPackage : umlPackage.getNestedPackage()) {
            aPackage.setNestingPackage(umlPackage);
        }
        log.info("Completed restructuring of Package [{}]", id);
        return umlPackage;
    }
}
