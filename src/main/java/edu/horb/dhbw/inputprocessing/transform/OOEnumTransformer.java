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
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOEnumTransformer
        extends CachingTransformer<Enumeration, OOType> {
    /**
     * @param registry The registry to use.
     */
    public OOEnumTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOType> transform(final @NonNull List<?> elements) {

        List<Enumeration> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof Enumeration) {
                classes.add((Enumeration) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOType transform(final @NonNull Enumeration element) {

        String id = element.getId();
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        log.info("Beginning transformation of [{}]", id);
        OOType ooEnum = new OOType(OOType.Type.ENUMERATION);
        cache.put(id, ooEnum);
        log.debug("Set id for [{}]", id);
        ooEnum.setId(id);
        log.debug("Set name for [{}]", id);
        ooEnum.setName(element.getName());
        return ooEnum;
    }
}
