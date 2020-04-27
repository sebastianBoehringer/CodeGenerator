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

import java.util.List;

/**
 * A simple wrapper around a list of {@link IStatement}s so
 * that they can be used as the second type parameter of
 * {@link edu.horb.dhbw.inputprocessing.transform.ITransformer}.
 */
@EqualsAndHashCode(callSuper = true)
@Value
public final class OOLogic extends OOBase {
    /**
     * The statements to execute.
     */
    private final List<IStatement> statements;

    /**
     * Throws an {@link UnsupportedOperationException}.
     */
    @Override
    protected OOBase getParent() {

        throw new UnsupportedOperationException();
    }

    /**
     * Throws an {@link UnsupportedOperationException}.
     */
    @Override
    public String getFQName() {

        throw new UnsupportedOperationException();
    }
}
