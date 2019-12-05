package edu.horb.dhbw.config.jackson.typeresolvers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import edu.horb.dhbw.datacore.classification.Operation;
import edu.horb.dhbw.datacore.classification.Property;
import edu.horb.dhbw.datacore.statemachines.StateMachine;
import edu.horb.dhbw.datacore.structuredclassifiers.UMLClass;

public class UMLClassIdResolver extends TypeIdResolverBase {
    @Override
    public String idFromValue(Object o) {

        return idFromValueAndType(o, o.getClass());
    }

    @Override
    public String idFromValueAndType(Object o, Class<?> aClass) {

        String typeId;
        String name = aClass.getSimpleName();
        return null;
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {

        return JsonTypeInfo.Id.NAME;
    }

    public JavaType typeFromId(DatabindContext context, String id) {

        String canonicalClassName = "";
        switch (id) {
            case "uml:Class":
                canonicalClassName = UMLClass.class.getCanonicalName();
                break;
            case "uml:StateMachine":
                canonicalClassName = StateMachine.class.getCanonicalName();
                break;
            case "uml:Property":
                canonicalClassName = Property.class.getCanonicalName();
                break;
            case "uml:Operation":
                canonicalClassName = Operation.class.getCanonicalName();
            default:
                throw new IllegalArgumentException("unexpected id: " + id);
        }
        return TypeFactory.defaultInstance().constructFromCanonical(canonicalClassName);
    }
}
