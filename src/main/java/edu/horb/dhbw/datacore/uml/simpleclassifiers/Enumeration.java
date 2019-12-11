package edu.horb.dhbw.datacore.uml.simpleclassifiers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enumeration extends DataType {
    private List<EnumerationLiteral> ownedLiteral = new ArrayList<>();

    public void setOwnedLiteral(List<EnumerationLiteral> ownedLiteral) {

        this.ownedLiteral.addAll(ownedLiteral);
    }
}
