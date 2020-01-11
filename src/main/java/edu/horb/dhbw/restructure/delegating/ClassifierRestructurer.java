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

package edu.horb.dhbw.restructure.delegating;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;

import java.util.Optional;

public final class ClassifierRestructurer extends RestructurerBase<Classifier> {

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ClassifierRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "classifier");
    }

    @Override
    public Classifier restructure(@NonNull final ModelElement element) {

        Class<? extends Classifier> clazz =
                LookupUtil.classifierFromUMLType(XMIUtil.getUMLType(element));
        return delegateRestructuring(element, clazz);
    }

    /**
     * No op as this restructurer does not cache.
     */
    @Override
    public void cleanCache() {

    }

    /**
     * @param id The id of an element
     * @return {@link Optional#EMPTY} as this restructurer does not cache
     */
    @Override
    public Optional<Classifier> getProcessed(@NonNull final String id) {

        return Optional.empty();
    }
}
