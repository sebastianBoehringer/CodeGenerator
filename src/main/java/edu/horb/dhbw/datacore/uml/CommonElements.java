package edu.horb.dhbw.datacore.uml;

import lombok.Data;

import java.net.URI;


@Data
public abstract class CommonElements {
    private String umlType;
    private String id;
    private String name;
    private String uuid;
    private String label;
    private URI href;
    private String idref;
}
