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

import edu.horb.dhbw.datacore.model.OOLogic;
import edu.horb.dhbw.datacore.model.OpaqueStatement;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.util.Config;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public final class BehaviorTransformer
        extends BaseTransformer<Behavior, OOLogic> {
    /**
     * @param registry The registry to use.
     */
    public BehaviorTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOLogic> transform(final @NonNull List<?> elements) {

        List<Behavior> behaviors = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof Behavior) {
                behaviors.add((Behavior) e);
            }
        }
        List<OOLogic> result = new ArrayList<>();
        for (Behavior behavior : behaviors) {
            OOLogic logic = transform(behavior);
            if (logic != null) {
                result.add(transform(behavior));
            }
        }
        return result;
    }

    @Override
    public OOLogic transform(final @NonNull Behavior element) {

        if (element instanceof StateMachine) {
            ITransformer<StateMachine, OOLogic> transformer =
                    getTransformer(StateMachine.class);
            return transformer.transform((StateMachine) element);
        }
        if (element instanceof OpaqueBehavior) {
            String body;
            OpaqueBehavior behavior = (OpaqueBehavior) element;
            int index = behavior.getLanguage()
                    .indexOf(Config.CONFIG.getLanguage().getName());
            if (index >= 0) {
                body = behavior.getBody().get(index);
            } else {
                log.warn("Language [{}] has no corresponding body in opaque "
                                 + "behavior [{}]",
                         Config.CONFIG.getLanguage().getName(),
                         behavior.getId());
                body = "";
            }
            return new OOLogic(
                    Collections.singletonList(new OpaqueStatement(body)));
        }

        return null;
    }
}
