package org.sfm.benchmark.sfm;

import java.sql.Connection;
import java.sql.SQLException;

import org.sfm.beans.DbObject;
import org.sfm.benchmark.BenchmarkRunner;
import org.sfm.benchmark.QueryExecutor;
import org.sfm.benchmark.SysOutBenchmarkListener;
import org.sfm.jdbc.DbHelper;
import org.sfm.jdbc.JdbcMapperFactory;

public class DynamicJdbcMapperForEachBenchmark extends ForEachMapperQueryExecutor implements QueryExecutor {
	public DynamicJdbcMapperForEachBenchmark(Connection conn)
			throws NoSuchMethodException, SecurityException, SQLException {
		super(JdbcMapperFactory.newInstance().newMapper(DbObject.class), conn);
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, SQLException, Exception {
		new BenchmarkRunner(-1, new DynamicJdbcMapperForEachBenchmark(DbHelper.benchmarkDb())).run(new SysOutBenchmarkListener(DynamicJdbcMapperForEachBenchmark.class, "BigQuery"));
		new BenchmarkRunner(1, new DynamicJdbcMapperForEachBenchmark(DbHelper.benchmarkDb())).run(new SysOutBenchmarkListener(DynamicJdbcMapperForEachBenchmark.class, "SmallQuery"));
	}
}