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

package edu.horb.dhbw.exception;


import lombok.NonNull;

public final class NotYetImplementedException extends RuntimeException {
    /**
     * Default Constructor.
     * Deprecated to make myself aware that the method throwing this
     * exception still has to be implemented.
     */
    @Deprecated
    public NotYetImplementedException() {

        super("The method you called has yet to be implemented.");
    }

    /**
     * Deprecated to make myself aware that the method throwing this
     * exception still has to be implemented.
     *
     * @param msg The msg for the exception
     */
    @Deprecated
    public NotYetImplementedException(final String msg) {

        super(msg);
    }

    /**
     * Deprecated to make myself aware that the method throwing this
     * exception still has to be implemented.
     *
     * @param clazz      The class in which the exception is thrown
     * @param methodName The name of the method that still needs to be
     *                   implemented
     */
    @Deprecated
    public NotYetImplementedException(
            @NonNull final Class<?> clazz, @NonNull final String methodName) {

        super(String.format(
                "The method %s in class %s has yet to be implemented",
                methodName, clazz.getSimpleName()));
    }
}
