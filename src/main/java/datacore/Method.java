package datacore;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Method {
    private VisibilityModifier modifier = VisibilityModifier.NONE;
    private final String name;
    private final List<String> parameters = new ArrayList<>();
}
