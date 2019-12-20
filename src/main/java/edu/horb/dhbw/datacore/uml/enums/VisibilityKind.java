package edu.horb.dhbw.datacore.uml.enums;

public enum VisibilityKind {
    PUBLIC,
    PRIVATE,
    PROTECTED,
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
