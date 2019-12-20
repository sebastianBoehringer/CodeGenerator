package edu.horb.dhbw.datacore.uml.packages;

import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
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
public class UMLPackage extends Namespace {
    private String URI;
    private List<UMLPackage> nestedPackage = new ArrayList<>();
    private List<Stereotype> ownedStereotype = new ArrayList<>();
    private List<Type> ownedType = new ArrayList<>();
    private List<PackageMerge> packageMerge = new ArrayList<>();
    private List<ProfileApplication> profileApplication = new ArrayList<>();
    private List<PackageImport> packageImport = new ArrayList<>();
    private List<PackageableElement> packagedElement = new ArrayList<>();
}
