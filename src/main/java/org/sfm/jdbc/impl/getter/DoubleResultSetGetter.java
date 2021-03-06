package org.sfm.jdbc.impl.getter;

import org.sfm.reflect.Getter;
import org.sfm.reflect.primitive.DoubleGetter;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DoubleResultSetGetter implements DoubleGetter<ResultSet>, Getter<ResultSet, Double> {

	private final int column;
	
	public DoubleResultSetGetter(final int column) {
		this.column = column;
	}

	@Override
	public double getDouble(final ResultSet target) throws SQLException {
		return target.getDouble(column);
	}

	@Override
	public Double get(final ResultSet target) throws Exception {
		final double d = getDouble(target);
		if (d == 0d && target.wasNull()) {
			return null;
		} else {
			return d;
		}
	}

    @Override
    public String toString() {
        return "DoubleResultSetGetter{" +
                "column=" + column +
                '}';
    }
}
