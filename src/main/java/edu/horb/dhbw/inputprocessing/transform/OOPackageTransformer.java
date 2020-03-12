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

import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class OOPackageTransformer
        implements ITransformer<UMLPackage, OOPackage> {

    private final TransformerRegistry registry;

    @Override
    public @NonNull List<OOPackage> transform(final @NonNull List<?> elements) {

        List<UMLPackage> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e != null && UMLPackage.class.equals(e.getClass())) {
                classes.add((UMLPackage) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOPackage transform(@NonNull final UMLPackage element) {

        OOPackage ooPackage = new OOPackage();
        ooPackage.setName(element.getName());
        return ooPackage;
    }
}
