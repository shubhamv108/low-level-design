package code.shubham.commons.query;

import code.shubham.commons.query.clauses.select.SelectClause;
import code.shubham.commons.query.clauses.where.WhereClause;
import code.shubham.commons.query.result.AbstractQueryResult;
import code.shubham.commons.query.result.concrete.NullQueryResult;

import java.util.function.Function;

public class Query<Data> implements Function<Data, AbstractQueryResult> {

    private final SelectClause<Data> selectClause;
    private final WhereClause<Data> whereClause;

    public Query(SelectClause<Data> selectClause,
                 WhereClause<Data> whereClause) {
        this.selectClause = selectClause;
        this.whereClause = whereClause;
    }

    public AbstractQueryResult apply(Data data) {
        if (whereClause.apply(data))
            return this.selectClause.apply(data);
        return new NullQueryResult();
    }

    @Override
    public String toString() {
        return "Query{" +
                "whereClause=" + whereClause +
                '}';
    }
}