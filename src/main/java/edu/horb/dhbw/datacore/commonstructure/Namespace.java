package edu.horb.dhbw.datacore.commonstructure;

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

    public void setElementImport(List<ElementImport> elementImport) {

        this.elementImport.addAll(elementImport);
    }

    public void setOwnedMember(List<NamedElement> ownedMember) {

        this.ownedMember.addAll(ownedMember);
    }

    public void setOwnedRule(List<Constraint> ownedRule) {

        this.ownedRule.addAll(ownedRule);
    }

    public void setPackageImport(List<PackageImport> packageImport) {

        this.packageImport.addAll(packageImport);
    }

    public void setImportedMember(List<PackageableElement> importedMember) {

        this.importedMember.addAll(importedMember);
    }
}
