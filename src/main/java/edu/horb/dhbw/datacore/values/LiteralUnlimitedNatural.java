package edu.horb.dhbw.datacore.values;

import edu.horb.dhbw.datacore.primitivetypes.UnlimitedNatural;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiteralUnlimitedNatural extends LiteralSpecification {
    private UnlimitedNatural value = new UnlimitedNatural(0L);
}
