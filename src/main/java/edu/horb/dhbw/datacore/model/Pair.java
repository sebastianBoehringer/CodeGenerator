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

public final class Pair<F, S> {
    /**
     * The first value in this pair.
     */
    private final F first;
    /**
     * The second value in this pair.
     */
    private final S second;

    /**
     * @param first  The first element in this pair
     * @param second The second element in this pair
     */
    public Pair(final F first, final S second) {

        this.first = first;
        this.second = second;
    }

    /**
     * @return The first value in this pair
     */
    public F first() {

        return first;
    }

    /**
     * @return The second value in this pair
     */
    public S second() {

        return second;
    }

}
