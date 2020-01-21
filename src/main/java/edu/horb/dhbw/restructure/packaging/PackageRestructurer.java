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

package edu.horb.dhbw.restructure.packaging;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.packages.ProfileApplication;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.restructure.CachingRestructurer;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class PackageRestructurer extends CachingRestructurer<UMLPackage> {

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
        if (processed.containsKey(id)) {
            log.info("Found id [{}] for package in cache", id);
            return processed.get(id);
        }
        UMLPackage umlPackage = new UMLPackage();
        processed.put(id, umlPackage);
        umlPackage.setId(id);

        log.info("Processing name for package [{}]", id);
        String name = element.getPlainAttribute("name");
        umlPackage.setName(name);

        log.info("Processing ownedmember for package [{}]", id);
        Collection<ModelElement> members = (Collection<ModelElement>) element
                .getSetAttribute("ownedmembers");
        umlPackage.setOwnedMember(delegateMany(members, NamedElement.class));

        log.info("Processing profileapplications for package [{}]", id);
        Collection<ModelElement> applications =
                (Collection<ModelElement>) element
                        .getSetAttribute("profileapplications");
        umlPackage.setProfileApplication(
                delegateMany(applications, ProfileApplication.class));

        return umlPackage;
    }
}
