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

package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.FeatureImpl;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.enums.ConnectorKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies a link between actual instances and not their associated
 * classifiers (which is what an {@link Association} does).
 * See subclauses 11.2 and 11.8.10 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Connector extends FeatureImpl {
    /**
     * The type of the connector.
     * This attribute can be derived
     */
    private ConnectorKind kind;
    /**
     * Behaviors that specify valid ways to interact via the connector.
     */
    private List<Behavior> contract = new ArrayList<>();
    /**
     * The ends of the connector.
     * By using those the instances connected by this connector can be
     * identified. The length of the list must always be greater than or
     * equal to {@code 2}. Otherwise the connector could not connect anything.
     */
    private List<ConnectorEnd> end = new ArrayList<>();
    /**
     * An optional {@link Association} that classifies the links created by
     * the connector.
     */
    private Association type;
}
