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

package edu.horb.dhbw.datacore.model;

import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.inputprocessing.transform.OOBaseStringWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OOMethod extends OOBase {
    /**
     * Special type indicating that nothing is returned.
     * Specialized for Java where said type is named "void".
     */
    private static final OOParameter VOID_RETURN;

    static {
        VOID_RETURN = new OOParameter();
        VOID_RETURN.setName("void");
        VOID_RETURN.setDirection(ParameterDirectionKind.RETURN);
        VOID_RETURN.setCardinality(Cardinality.SINGLE);
        VOID_RETURN.setType(new OOType((OOType.Type.PRIMITIVE)));
        VOID_RETURN.getType().setName("void");
    }

    /**
     * The type that owns the method.
     */
    private OOType parent;
    /**
     * Defines the logic used when executing this method.
     */
    private OOBaseStringWrapper logic = OOBaseStringWrapper.EMPTY;
    /**
     * The parameters used for the invocation of the method.
     * This does not include {@link #returnParam}. I. e. {@code parameters
     * .contains(returnParam) == false}.
     */
    private List<OOParameter> parameters = Collections.emptyList();

    /**
     * The return parameter of the method.
     */
    private OOParameter returnParam;
    /**
     * The visibility of the method.
     */
    private VisibilityKind visibility;

    /**
     * If this is {@code true} the method is abstract.
     */
    private boolean isAbstract;

    /**
     * If this is {@code true} the method is static.
     */
    private boolean isStatic;

    /**
     * Collects all the exceptions that the execution of the method can
     * possibly cause.
     */
    private List<OOType> exceptions = Collections.emptyList();

    /**
     * @return The return param or {@link #VOID_RETURN} if none is specified
     */
    public OOParameter getReturnParam() {

        return this.returnParam == null ? VOID_RETURN : returnParam;
    }
}
