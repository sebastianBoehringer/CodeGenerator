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

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A class that corresponds to
 * {@link edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior}.
 * Its meaning thus cannot be derived by the CodeGenerator.
 */
@EqualsAndHashCode(callSuper = true)
@Value
public final class OpaqueStatement extends BaseStatement {
    /**
     * The statement to execute.
     */
    private final String statement;

    @Override
    public StatementKind getStatementType() {

        return StatementKind.OPAQUE;
    }
}
