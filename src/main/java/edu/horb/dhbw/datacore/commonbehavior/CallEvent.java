package edu.horb.dhbw.datacore.commonbehavior;

import edu.horb.dhbw.datacore.classification.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallEvent extends MessageEvent {
    private Operation operation;
}
