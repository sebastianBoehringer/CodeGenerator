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

package edu.horb.dhbw.datacore.uml.commonbehavior;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElementImpl;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * A event fired when a boolean condition changes.
 * See subclauses 13.3 and 13.4.4 of the UML specification for more details.
 */
@Data
@NoArgsConstructor
public final class ChangeEvent extends PackageableElementImpl implements Event {
    /**
     * When this evaluates from {@code false} to {@code true} the event is
     * fired.
     */
    private ValueSpecification changeExpression;

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ChangeEvent changeEvent = (ChangeEvent) o;

        return Objects.equals(changeExpression, changeEvent.changeExpression);
    }

    @Override
    public int hashCode() {

        int result = getId().hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
