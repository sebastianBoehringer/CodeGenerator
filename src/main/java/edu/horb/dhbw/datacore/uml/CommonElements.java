package edu.horb.dhbw.datacore.uml;

import lombok.Data;

import java.net.URI;


/**
 * A class that holds all attributes defined by the XMI specification. You may
 * download the latest version at the official
 * <a href="https://www.omg.org/spec/XMI/"> homepage</a>.
 * Please be aware that this comment is aligned to XMI version 2.5.1.
 */
@Data
public abstract class CommonElements {

    /**
     * Holds the name of the type of the object which is serialized. See
     * subclause 7.6.3 of the specification for more details.
     */
    private String umlType;

    /**
     * An id that identifies exactly this element in the current xmi document.
     * This does not need to be globally unique. Part of the so called
     * IdentityAttribs of XMI. See subclause 7.6.1 of the specification for
     * more details.
     */
    private String id;

    /**
     * A globally unique identifier. Part of the so called
     * IdentityAttribs of XMI. See subclause 7.6.1 of the
     * specification for more details.
     */
    private String uuid;

    /**
     * A non formalized type of id that depends on the user. The attribute
     * should be ignored on import. Part of the so called
     * IdentityAttribs of XMI. See subclause 7.6.1 of the
     * specification for more details.
     */
    private String label;

    /**
     * Used for global linking instead of locally within the same document.
     * This uses the XPointer and XLink mechanisms. Part of the so called
     * LinkAttribs. See subclause 7.6.2 for more detail.
     */
    private URI href;

    /**
     * Allows referencing to another xml element using XML IDREF. This should
     * correspond to the value of another {@link #id} used in the same
     * document. Part of the so called LinkAttribs. See subclause 7.6.2 for
     * more detail.
     */
    private String idref;
}
