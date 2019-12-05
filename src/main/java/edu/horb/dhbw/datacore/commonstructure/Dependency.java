package edu.horb.dhbw.datacore.commonstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependency extends PackageableElement {
    private List<NamedElement> client;
    private List<NamedElement> supplier;

    public void setClient(List<NamedElement> client) {

        this.client.addAll(client);
    }

    public void setSupplier(List<NamedElement> supplier) {

        this.supplier.addAll(supplier);
    }
}
