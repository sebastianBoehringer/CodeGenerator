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

package edu.horb.dhbw.util;

import edu.horb.dhbw.datacore.model.IStatement;
import edu.horb.dhbw.datacore.model.ParallelStatement;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class IStatements {
    private IStatements() {

    }

    /**
     * @param statements The statements to flatten
     * @return A flattened list of statements, i. e. each unnecessary wrapper
     * into a {@link ParallelStatement} is removed
     */
    public static List<IStatement> deepFlatten(final @NonNull List<IStatement> statements) {

        List<IStatement> flattened = new ArrayList<>();
        for (IStatement statement : statements) {
            if (statement instanceof ParallelStatement) {
                ParallelStatement parallel = (ParallelStatement) statement;
                parallel.getParallel().removeIf(List::isEmpty);
                if (parallel.getParallel().size() == 1) {
                    flattened
                            .addAll(deepFlatten(parallel.getParallel().get(0)));
                    continue;
                } else {
                    for (int i = 0; i < parallel.getParallel().size(); i++) {
                        parallel.getParallel().set(i, deepFlatten(
                                parallel.getParallel().get(i)));
                        }
                    }
            }
            flattened.add(statement);
        }
        return flattened;
    }
}
