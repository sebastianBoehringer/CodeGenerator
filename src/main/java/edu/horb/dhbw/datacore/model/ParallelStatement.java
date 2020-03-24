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
 * A statement that encapsulates other {@link IStatement} which execute in
 * parallel.
 */
@EqualsAndHashCode(callSuper = true)
@Value
public final class ParallelStatement extends BaseStatement
        implements IStatement {

    /**
     * The statements that are executed in a parallel fashion.
     * Each nested list is executed in parallel to the others.
     */
    private final List<List<IStatement>> parallel;


    @Override
    public StatementKind getStatementType() {

        return StatementKind.PARALLEL;
    }

    /**
     * Checks if the parallelStatement is executed sequentially because there
     * are no other {@link IStatement}s that are executed in parallel. If
     *
     * @return {@code True} if the parallel statement might as well be
     * sequential.
     */
    public boolean isSequential() {

        return parallel.size() <= 1;
    }

}
