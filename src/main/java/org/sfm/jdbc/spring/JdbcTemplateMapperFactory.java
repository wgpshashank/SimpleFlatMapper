package org.sfm.jdbc.spring;

import org.sfm.jdbc.JdbcColumnKey;
import org.sfm.jdbc.JdbcMapperFactory;
import org.sfm.map.FieldMapperErrorHandler;
import org.sfm.map.MapperBuilderErrorHandler;
import org.sfm.map.MapperBuildingException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public final class JdbcTemplateMapperFactory {
	
	public static JdbcTemplateMapperFactory newInstance() {
		return new JdbcTemplateMapperFactory();
	}
	
	private final JdbcMapperFactory jdbcMapperFactory = JdbcMapperFactory.newInstance();

	public JdbcTemplateMapperFactory fieldMapperErrorHandler(FieldMapperErrorHandler<JdbcColumnKey> fieldMapperErrorHandler) {
		jdbcMapperFactory.fieldMapperErrorHandler(fieldMapperErrorHandler);
		return this;
	}

	public JdbcTemplateMapperFactory mapperBuilderErrorHandler(
			MapperBuilderErrorHandler mapperBuilderErrorHandler) {
		jdbcMapperFactory.mapperBuilderErrorHandler(mapperBuilderErrorHandler);
		return this;
	}

	public JdbcTemplateMapperFactory useAsm(boolean useAsm) {
		jdbcMapperFactory.useAsm(useAsm);
		return this;
	}
	
	public <T> RowMapper<T> newRowMapper(Class<T> target)
			throws MapperBuildingException {
		return newMapper(target);
	}

	public <T> JdbcTemplateMapper<T> newMapper(Class<T> target) {
		return new JdbcTemplateMapper<T>(jdbcMapperFactory.newMapper(target));
	}
	
	public <T> PreparedStatementCallback<List<T>> newPreparedStatementCallback(Class<T> target)
			throws MapperBuildingException {
		return newMapper(target);
	}
	
	public <T> ResultSetExtractor<List<T>> newResultSetExtractor(Class<T> target)
			throws MapperBuildingException {
		return newMapper(target);
	}
}
