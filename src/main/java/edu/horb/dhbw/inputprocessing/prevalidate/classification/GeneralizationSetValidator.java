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

package edu.horb.dhbw.inputprocessing.prevalidate.classification;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.GeneralizationSet;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;

public final class GeneralizationSetValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof GeneralizationSet;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        GeneralizationSet set = (GeneralizationSet) base;
        Generalization current;
        Generalization next;
        for (int i = 0; i < set.getGeneralization().size() - 1; i++) {
            current = set.getGeneralization().get(i);
            next = set.getGeneralization().get(i + 1);
            if (!current.getId().equals(next.getId())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "GeneralizationSet [%s] has different general types",
                        set.getId()));
            }
        }
        return VALID;
    }
}
