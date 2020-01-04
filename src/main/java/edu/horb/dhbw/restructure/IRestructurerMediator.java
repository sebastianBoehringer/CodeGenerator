/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.restructure;


import edu.horb.dhbw.datacore.uml.CommonElements;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.restructure.classes.PropertyRestructurer;
import edu.horb.dhbw.restructure.classes.UMLClassRestructurer;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class IRestructurerMediator {

    /**
     * The number of {@link IRestructurer}s registered when using the default
     * constructor.
     */
    private static final int DEFAULT_SIZE = 8;

    /**
     * The mappings to use.
     */
    private final Map<Class<?>, IRestructurer<?>> classToRestructurer;

    /**
     * The default {@link IRestructurer} to use if no specialized one is
     * registered in {@link #classToRestructurer}.
     */
    private final IRestructurer<?> defaultImplementation =
            new IRestructurerDefImpl(this);

    /**
     * Default Constructor.
     * Initializes {@link #classToRestructurer} with some default
     * {@link IRestructurer}s.
     */
    public IRestructurerMediator() {

        classToRestructurer = new HashMap<>(DEFAULT_SIZE);
        classToRestructurer.put(UMLClass.class, new UMLClassRestructurer(this));
       /* classToRestructurer.put(Connector.class, new ConnectorRestructurer());
        classToRestructurer
                .put(Generalization.class, new GeneralizationRestructurer());
        classToRestructurer.put(InterfaceRealization.class,
                                new InterfaceRealizationRestructurer());*/
        classToRestructurer.put(Property.class, new PropertyRestructurer(this));
    }

    /**
     * @param clazz        The class for which a restructurer is added
     * @param restructurer The restructurer to add
     * @param <T>          Ensuring that the class and the restructurer match
     */
    public <T extends CommonElements> void register(final Class<T> clazz,
                                                    final IRestructurer<T> restructurer) {

        classToRestructurer.put(clazz, restructurer);
    }

    /**
     * @param clazz The class to restructure to
     * @param <T>   The class that the IRestructurer restructures to. Upper
     *              bound is {@link CommonElements}.
     * @return An IRestructurer transforming a
     * {@link com.sdmetrics.model.Model} to a T
     */
    public <T extends CommonElements> IRestructurer<T> getIRestructurer(final Class<T> clazz) {

        return (RestructurerBase<T>) classToRestructurer
                .getOrDefault(clazz, defaultImplementation);
    }
}
