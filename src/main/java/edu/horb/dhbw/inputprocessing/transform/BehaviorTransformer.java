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

import edu.horb.dhbw.datacore.model.OOBase;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior;
import edu.horb.dhbw.util.Config;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class BehaviorTransformer extends
                                       AbstractTransformer<Behavior,
                                               BehaviorTransformer.OOBaseStringWrapper> {
    /**
     * @param registry The registry to use.
     */
    public BehaviorTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    public static final class OOBaseStringWrapper extends OOBase {
        /**
         * A wrapper wrapping the empty string
         */
        public static final OOBaseStringWrapper EMPTY =
                new OOBaseStringWrapper("");
        /**
         * The wrapped string.
         * This is the extracted body of an {@link OpaqueBehavior}.
         */
        private final String body;

        /**
         * @param body The string to wrap
         */
        public OOBaseStringWrapper(String body) {

            this.body = body;
        }

        @Override
        protected OOBase getParent() {

            return null;
        }

        public String getBody() {

            return body;
        }
    }

    @Override
    public @NonNull List<OOBaseStringWrapper> transform(final @NonNull List<?> elements) {

        List<Behavior> behaviors = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof Behavior) {
                behaviors.add((Behavior) e);
            }
        }
        List<OOBaseStringWrapper> result = new ArrayList<>();
        for (Behavior behavior : behaviors) {
            OOBaseStringWrapper logic = transform(behavior);
            if (logic != null) {
                result.add(transform(behavior));
            }
        }
        return result;
    }

    @Override
    public OOBaseStringWrapper transform(final @NonNull Behavior element) {

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
                body = behavior.getBody().size() > 0 ? behavior.getBody().get(0)
                                                     : "";
            }
            OOBaseStringWrapper wrapper = new OOBaseStringWrapper(body);
            wrapper.setId(behavior.getId());
            return wrapper;
        }

        return null;
    }
}
