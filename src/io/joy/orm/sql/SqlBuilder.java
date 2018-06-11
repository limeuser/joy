package io.joy.orm.sql;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/6/11
 */
public class SqlBuilder implements Sql {
    public static char space = ' ';
    public static String column_delimiter = ", ";
    public static String newline = "\n";

    protected StringBuilder sql = new StringBuilder();

    private SqlBuilder(String sqlPart) { }

    public static SqlBuilder create() {
        return new SqlBuilder("");
    }

    public static SqlBuilder create(String sqlPart) {
        return new SqlBuilder(sqlPart);
    }

    public SqlBuilder append(String sqlPart) {
        if (sql.charAt(sql.length() - 1) != space) {
            sql.append(space);
        }

        sql.append(sqlPart);

        return this;
    }

    public SqlBuilder appendValue(Object value) {
        this.append(buildValue(value));

        return this;
    }

    public SqlBuilder binaryExpr(String left, String operator, String right) {
        this.append(left).append(operator).append(right);
        return this;
    }

    public SqlBuilder twoExpr(String leftExpr, String operator, String rightExpr) {
        return this.binaryExpr(leftExpr, operator, rightExpr);
    }

    public SqlBuilder exprValue(String expr, String operator, Object value) {
        return this.binaryExpr(expr, operator, buildValue(value));
    }

    public SqlBuilder twoValue(Object left, String operator, Object right) {
        return this.binaryExpr(buildValue(left), operator, buildValue(right));
    }

    public SqlBuilder exprValueNotNull(String expr, String operator, Object value) {
        return value == null ? this : exprValue(expr, operator, value);
    }

    public enum Operator {
        equal("="),
        notEqual("!="),
        like("like"),
        and("and"),
        or("or");

        private String name;

        Operator(String name) {
            this.name = name;
        }
    }

    public String buildValue(Object value) {
        if (value instanceof String) {
            return buildString(value.toString());
        } else {
            return value.toString();
        }
    }

    public String buildString(String str) {
        return "'" + str + "'";
    }

    @Override
    public String sql() {
        return sql.toString();
    }
}