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
/**
 * Contains classes that are responsible for transforming
 * {@link com.sdmetrics.model.ModelElement}s into classes with the respective
 * attributes. This is necessary and helpful as accessing the attributes via
 * the provided methods of {@link com.sdmetrics.model.ModelElement} requires
 * a lot of knowledge and does not provide (type)safety.
 *
 * @author sebastianBoehringer
 * @since 0.2
 */
package edu.horb.dhbw.restructure;
