package code.shubham.commons.query.clauses.select;

import code.shubham.commons.query.result.AbstractQueryResult;

import java.util.function.Function;

public class SelectClause<Data> implements Function<Data, AbstractQueryResult> {

    private Function<Data, AbstractQueryResult> selector;

    SelectClause(Function<Data, AbstractQueryResult> selector) {
        this.selector = selector;
    }

    public AbstractQueryResult apply(Data data) {
        return this.selector.apply(data);
    }

}
