package org.sfm.jdbc.getter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sfm.reflect.Getter;
import org.sfm.reflect.primitive.IntGetter;

public class IntNamedResultSetGetter implements IntGetter<ResultSet>, Getter<ResultSet, Integer> {

	private final String column;
	
	public IntNamedResultSetGetter(String column) {
		this.column = column;
	}

	@Override
	public int getInt(ResultSet target) throws SQLException {
		return target.getInt(column);
	}

	@Override
	public Integer get(ResultSet target) throws Exception {
		return getInt(target);
	}
}