package org.sfm.jdbc.impl.getter;

import org.sfm.reflect.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;

public final class SQLXMLResultSetGetter implements Getter<ResultSet, SQLXML> {
	private final int column;
	
	public SQLXMLResultSetGetter(final int column) {
		this.column = column;
	}

	public SQLXML get(final ResultSet target) throws SQLException {
		return target.getSQLXML(column);
	}

    @Override
    public String toString() {
        return "SQLXMLResultSetGetter{" +
                "column=" + column +
                '}';
    }
}
