package edu.horb.dhbw.datacore.commonstructure;

import edu.horb.dhbw.datacore.CommonElements;
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
public abstract class Element extends CommonElements {
    private List<String> comment = new ArrayList<>();
    private List<Element> ownedElement = new ArrayList<>();
    private Element owner;


    public void setComment(List<String> comment) {

        this.comment.addAll(comment);
    }

    public void setOwnedElement(List<Element> elements) {

        ownedElement.addAll(elements);
    }


}
