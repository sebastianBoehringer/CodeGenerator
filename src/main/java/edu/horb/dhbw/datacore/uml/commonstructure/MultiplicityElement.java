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

package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;

public interface MultiplicityElement extends Element {
    Boolean getIsOrdered();

    void setIsOrdered(Boolean ordered);

    Boolean getIsUnique();

    void setIsUnique(Boolean uniqe);

    Integer getLower();

    void setLower(Integer lower);

    UnlimitedNatural getUpper();

    void setUpper(UnlimitedNatural upper);

    ValueSpecification getLowerValue();

    void setLowerValue(ValueSpecification lowerValue);

    ValueSpecification getUpperValue();

    void setUpperValue(ValueSpecification upperValue);
}
