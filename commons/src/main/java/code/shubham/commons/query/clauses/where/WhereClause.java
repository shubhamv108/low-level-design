package code.shubham.commons.query.clauses.where;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class WhereClause<Data> implements Function<Data, Boolean> {
    private final List<Condition> conditions = new ArrayList<>();

    public void add(Condition condition) {
        this.conditions.add(condition);
    }

    public Boolean apply(Data data) {
        return conditions.stream().allMatch(condition -> condition.apply(data));
    }
}
