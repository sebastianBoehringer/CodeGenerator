package edu.horb.dhbw.datacore.uml.primitivetypes;

import lombok.Data;

@Data
public class UnlimitedNatural {
    public final static UnlimitedNatural UNLIMITED = new UnlimitedNatural("*");
    private long value;

    public UnlimitedNatural(String string) {

        try {
            value = Long.parseLong(string);
            assert value >= 0;
        } catch (NumberFormatException e) {
            value = Long.MAX_VALUE;
        }
    }

    public UnlimitedNatural(Long value) {

        assert value >= 0;
        this.value = value;
    }
}
