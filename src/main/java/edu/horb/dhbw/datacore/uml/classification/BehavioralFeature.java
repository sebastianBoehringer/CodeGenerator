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

package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.CallConcurrencyKind;

import java.util.List;

public interface BehavioralFeature extends Namespace, Feature {
    CallConcurrencyKind getConcurrency();

    void setConcurrency(CallConcurrencyKind concurrency);

    Boolean getIsAbstract();

    void setIsAbstract(Boolean isAbstract);

    List<Behavior> getMethod();

    void setMethod(List<Behavior> method);

    List<Parameter> getOwnedParameter();

    void setOwnedParameter(List<Parameter> ownedParameter);

    List<Type> getRaisedException();

    void setRaisedException(List<Type> raisedException);
}
