/*
 * Copyright (c) 2020 Sebastian Boehringer.
 *  This file is part of the CodeGenerator.
 *
 *  CodeGenerator is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 *  option) any later version.
 * CodeGenerator is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 * along with CodeGenerator.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.horb.dhbw.inputprocessing.transform;

import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.util.Config;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class OOPrimitiveTransformer
        extends AbstractTransformer<PrimitiveType, OOType> {
    private final OOType primitiveBool;
    private final OOType primitiveInt;
    private final OOType primitiveString;
    private final OOType primitiveReal;
    private final OOType primitiveUnlimitedNatural;

    public OOPrimitiveTransformer(final ITransformerRegistry registry) {

        super(registry);
        primitiveBool = new OOType(OOType.Type.PRIMITIVE);
        primitiveBool.setName(Config.CONFIG.getLanguage().getBooleanName());
        primitiveInt = new OOType(OOType.Type.PRIMITIVE);
        primitiveInt.setName(Config.CONFIG.getLanguage().getIntegerName());
        primitiveString = new OOType(OOType.Type.PRIMITIVE);
        primitiveString.setName(Config.CONFIG.getLanguage().getStringName());
        primitiveReal = new OOType(OOType.Type.PRIMITIVE);
        primitiveReal.setName(Config.CONFIG.getLanguage().getRealName());
        primitiveUnlimitedNatural = new OOType(OOType.Type.PRIMITIVE);
        primitiveUnlimitedNatural
                .setName(Config.CONFIG.getLanguage().getUnlimitedNaturalName());

    }

    @Override
    public @NonNull List<OOType> transform(final @NonNull List<?> elements) {

        List<PrimitiveType> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof PrimitiveType) {
                classes.add((PrimitiveType) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOType transform(final @NonNull PrimitiveType element) {

        switch (element.getName().toLowerCase()) {
            case "string":
                return primitiveString;
            case "real":
                return primitiveReal;
            case "integer":
                return primitiveInt;
            case "boolean":
                return primitiveBool;
            case "unlimitednatural":
                return primitiveUnlimitedNatural;
            default:
                //TODO prolly better exception handling
                return null;
        }
    }
}
