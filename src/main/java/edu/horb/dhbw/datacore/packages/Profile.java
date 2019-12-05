package edu.horb.dhbw.datacore.packages;

import edu.horb.dhbw.datacore.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.commonstructure.PackageImport;
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
public class Profile extends UMLPackage {
    private List<ElementImport> metaclassReference = new ArrayList<>();
    private List<PackageImport> metamodelReference = new ArrayList<>();

    public void setMetaclassReference(List<ElementImport> metaclassReference) {

        this.metaclassReference.addAll(metaclassReference);
    }

    public void setMetamodelReference(List<PackageImport> metamodelReference) {

        this.metamodelReference.addAll(metamodelReference);
    }
}
