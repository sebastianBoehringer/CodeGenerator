package edu.horb.dhbw.datacore.packages;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import edu.horb.dhbw.datacore.commonstructure.Namespace;
import edu.horb.dhbw.datacore.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.commonstructure.Type;
import edu.horb.dhbw.datacore.structuredclassifiers.UMLClass;
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
@JacksonXmlRootElement(localName = "Package")
public class UMLPackage extends Namespace {
    private String href;
    private String URI;
    private List<UMLPackage> nestedPackage = new ArrayList<>();
    private List<Stereotype> ownedStereotype = new ArrayList<>();
    private List<Type> ownedType = new ArrayList<>();
    private List<PackageMerge> packageMerge = new ArrayList<>();
    private List<ProfileApplication> profileApplication = new ArrayList<>();
    private List<PackageImport> packageImport = new ArrayList<>();
    /**
     * Not compliant with the UML-Specification where a package may contain any type of
     * PackageableElement. StateMachine is the most specialized of them all tho and should thus
     * be capable of holding it all
     */
    @JacksonXmlProperty(isAttribute = false)
    private List<UMLClass> packagedElement = new ArrayList<>();

    public void setPackagedElement(List<UMLClass> packagedElement) {

        this.packagedElement.addAll(packagedElement);
    }


    public void setNestedPackage(List<UMLPackage> nestedPackage) {

        this.nestedPackage.addAll(nestedPackage);
    }

    public void setOwnedStereotype(List<Stereotype> ownedStereotype) {

        this.ownedStereotype.addAll(ownedStereotype);
    }

    public void setOwnedType(List<Type> ownedType) {

        this.ownedType.addAll(ownedType);
    }

    public void setPackageMerge(List<PackageMerge> packageMerge) {

        this.packageMerge.addAll(packageMerge);
    }


    public void setProfileApplication(List<ProfileApplication> profileApplication) {

        this.profileApplication.addAll(profileApplication);
    }

    public void setPackageImport(List<PackageImport> packageImport) {

        this.packageImport.addAll(packageImport);
    }
}
