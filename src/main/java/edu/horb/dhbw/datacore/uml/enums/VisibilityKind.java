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

/**
 * Determines the visibility of
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.Element}s in a Model.
 * <br/>
 * See subclauses 7.4 and 7.8.23 of the UML specification for more details.
 */
public enum VisibilityKind {
    /**
     * The element is visible to all other elements which have access to its
     * to the contents of the owning namespace.
     */
    PUBLIC,
    /**
     * The element is only visible to other elements inside its owning
     * namespace.
     */
    PRIVATE,
    /**
     * The element is visible to other elements having a
     * {@link edu.horb.dhbw.datacore.uml.classification.Generalization} type
     * relationship to its owning namespace.
     */
    PROTECTED,
    /**
     * The element is visible inside its enclosing
     * {@link edu.horb.dhbw.datacore.uml.packages.UMLPackage}. If said
     * package is part of another package that package has no access to the
     * element.
     */
    PACKAGE;

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        //TODO make the method rely on read in configuration.
        String ret = "";
        switch (this) {
            case PUBLIC:
                ret = "public";
                break;
            case PRIVATE:
                ret = "private";
                break;
            case PROTECTED:
                ret = "protected";
                break;
            case PACKAGE:
                ret = "";
                break;
            default:
        }
        return ret;
    }
}
