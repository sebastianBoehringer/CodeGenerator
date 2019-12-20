package edu.horb.dhbw.datacore.uml.primitivetypes;

import lombok.Data;

@Data
public class UnlimitedNatural {
    /**
     *
     */
    public static final UnlimitedNatural UNLIMITED = new UnlimitedNatural("*");
    private long value;

    public UnlimitedNatural(final String string) {

        try {
            value = Long.parseLong(string);
            assert value >= 0;
        } catch (NumberFormatException e) {
            if (string.equals("*")) {
                value = Long.MAX_VALUE;
            } else {
                throw new IllegalArgumentException(String.format(
                        "Could not parse [%s] as long "
                                + "and it wasnt [*] either", string));
            }
        }
    }

    public UnlimitedNatural(final Long value) {

        assert value >= 0;
        this.value = value;
    }
}
