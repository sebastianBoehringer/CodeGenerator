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
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.packages.Profile;
import edu.horb.dhbw.datacore.uml.packages.ProfileApplication;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class ProfileRestructurer extends RestructurerBase<Profile> {
    /**
     * A map holding all the {@link Profile}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Profile> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ProfileRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "profile");
    }

    @Override
    public Profile restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] for profile in cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        Profile profile = new Profile();
        ALREADY_PROCESSED.put(id, profile);
        profile.setId(id);

        log.info("Processing name for profile [{}]", id);
        String name = element.getPlainAttribute("name");
        profile.setName(name);

        log.info("Processing ownedmember for profile [{}]", id);
        Collection<ModelElement> members = (Collection<ModelElement>) element
                .getSetAttribute("ownedmembers");
        profile.setOwnedMember(delegateMany(members, NamedElement.class));

        log.info("Processing profileapplications for profile [{}]", id);
        Collection<ModelElement> applications =
                (Collection<ModelElement>) element
                        .getSetAttribute("profileapplications");
        profile.setProfileApplication(
                delegateMany(applications, ProfileApplication.class));

        log.info("Processing metaclasses for profile [{}]", id);
        Collection<ModelElement> metaClasses =
                (Collection<ModelElement>) element
                        .getSetAttribute("metaclasses");
        profile.setMetaclassReference(
                delegateMany(metaClasses, ElementImport.class));

        log.info("Processing metapackages for profile [{}]", id);
        Collection<ModelElement> metaPackages =
                (Collection<ModelElement>) element
                        .getSetAttribute("metapackages");
        profile.setMetamodelReference(
                delegateMany(metaPackages, PackageImport.class));

        return profile;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<Profile> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
