/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.datacore.uml.enums;

import lombok.NonNull;

/**
 * Specifies the semantics of a concurrent call to a
 * {@link edu.horb.dhbw.datacore.uml.classification.BehavioralFeature}.
 * See subclauses 9.4 and 9.9.3 of the UML specification for more details.
 */
public enum CallConcurrencyKind {
    /**
     * There is no management for concurrency. There may be trouble due to
     * that. The objects that invoke the
     * {@link edu.horb.dhbw.datacore.uml.classification.BehavioralFeature}
     * are responsible for coordination so that no trouble arises.
     */
    SEQUENTIAL,
    /**
     * Only one invocation may be carried out. Other invocations will be
     * blocked until the
     * {@link edu.horb.dhbw.datacore.uml.classification.BehavioralFeature}
     * has completed its execution.
     */
    GUARDED,
    /**
     * Multiple invocations may occur. None of these will be blocked.
     */
    CONCURRENT;

    /**
     * Case insensitive wrapper around {@link #valueOf(String)}.
     *
     * @param string The string identifying an enum constant
     * @return The enum constant identified by the string
     */
    public static CallConcurrencyKind from(@NonNull final String string) {

        return CallConcurrencyKind.valueOf(string.toUpperCase());
    }
}
