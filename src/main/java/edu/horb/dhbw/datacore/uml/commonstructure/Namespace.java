package edu.horb.dhbw.datacore.uml.commonstructure;

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
public abstract class Namespace extends NamedElement {
    private List<ElementImport> elementImport = new ArrayList<>();
    private List<NamedElement> ownedMember = new ArrayList<>();
    private List<Constraint> ownedRule = new ArrayList<>();
    private List<PackageImport> packageImport = new ArrayList<>();
    private List<PackageableElement> importedMember = new ArrayList<>();
}
