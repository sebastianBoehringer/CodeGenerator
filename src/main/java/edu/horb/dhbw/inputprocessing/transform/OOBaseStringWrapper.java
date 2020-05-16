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

package edu.horb.dhbw.inputprocessing.transform;

import edu.horb.dhbw.datacore.model.OOBase;
import lombok.NonNull;

public final class OOBaseStringWrapper extends OOBase {
    /**
     * A wrapper wrapping the empty string.
     */
    public static final OOBaseStringWrapper EMPTY = new OOBaseStringWrapper("");
    /**
     * The wrapped string.
     * This is the extracted body of an
     * {@link edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior}.
     */
    private final String body;

    /**
     * @param body The string to wrap
     */
    public OOBaseStringWrapper(final String body) {

        this.body = body;
    }

    @Override
    protected OOBase getParent() {

        throw new UnsupportedOperationException();
    }

    public String getBody() {

        return body;
    }

    /**
     * @param string The string to prepend
     * @return A new wrapper where the original value has the string prepended
     */
    public OOBaseStringWrapper prepend(@NonNull final String string) {

        if (this.equals(EMPTY) && "".equals(string)) {
            return EMPTY;
        }
        return new OOBaseStringWrapper(string + body);
    }

    /**
     * @param string The string to append
     * @return A new wrapper where the original value has the string appended
     */
    public OOBaseStringWrapper append(@NonNull final String string) {
        if (this.equals(EMPTY) && "".equals(string)) {
            return EMPTY;
        }
        return new OOBaseStringWrapper(body + string);
    }

}
