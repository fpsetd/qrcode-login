package com.qrcode.login.config;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * 自定义物理表名和字段名的命名规则，驼峰转下划线
 * @author bogon
 *
 */
public class SimplePhysicalNamingStrategy implements PhysicalNamingStrategy, Serializable {

	private static final long serialVersionUID = -7478087936936133837L;

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return this.converter(name);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return this.converter(name);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return this.converter(name);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return this.converter(name);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return this.converter(name);
	}

	private Identifier converter(Identifier name) {
		if (name == null || StringUtils.isBlank(name.getText()))
			return name;
		String regex = "([a-z])([A-Z])";
		String replacement = "$1_$2";
		String newName = name.getText().replaceAll(regex, replacement).toLowerCase();
		return Identifier.toIdentifier(newName);
	}
}
