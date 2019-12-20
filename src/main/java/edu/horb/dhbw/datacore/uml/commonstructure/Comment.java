package edu.horb.dhbw.datacore.uml.commonstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String body;
    private List<Element> annotatedElement = new ArrayList<>();
}
