package code.shubham.commons.query.clauses.where;

import code.shubham.common.utils.ReflectionUtils;

import java.util.Arrays;
import java.util.function.Function;

public class Condition<Data> implements Function<Data, Boolean> {
    String[] fieldPath;
    Operation operation;
    String[] values;

    public Boolean apply(Data data) {
        try {
            Object dataValue = ReflectionUtils.getValueForField(fieldPath, 0, data);
            if (Operation.EQUALS.equals(operation)) {
                return values[0].equals(dataValue);
            }
            if (Operation.IN.equals(operation)) {
                return Arrays.stream(values).anyMatch(value -> value.equals(dataValue));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "fieldPath=" + Arrays.toString(fieldPath) +
                ", operation=" + operation +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
