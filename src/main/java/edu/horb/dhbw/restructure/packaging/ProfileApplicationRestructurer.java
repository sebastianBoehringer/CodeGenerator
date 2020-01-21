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
import edu.horb.dhbw.datacore.uml.packages.Profile;
import edu.horb.dhbw.datacore.uml.packages.ProfileApplication;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ProfileApplicationRestructurer
        extends RestructurerBase<ProfileApplication> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ProfileApplicationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "profileapplication");
    }

    @Override
    public ProfileApplication restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Processing name for profileapplication [{}]", id);
        ProfileApplication application = new ProfileApplication();
        application.setId(id);

        log.info("Processing strict for profileapplication [{}]", id);
        String strict = element.getPlainAttribute("strict");
        application.setIsStrict(Boolean.valueOf(strict));

        log.info("Processing applied for profileapplication [{}]", id);
        ModelElement applied = element.getRefAttribute("applied");
        application.setAppliedProfile(
                delegateRestructuring(applied, Profile.class));

        log.info("Processing applying for profileapplication [{}]", id);
        ModelElement applying = element.getRefAttribute("applying");
        application.setApplyingPackage(
                delegateRestructuring(applying, UMLPackage.class));

        return application;
    }
}
