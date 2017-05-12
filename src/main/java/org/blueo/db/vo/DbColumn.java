package org.blueo.db.vo;


public class DbColumn {

	private String name;
	private boolean pk;
	private boolean nullable;
	private String comment;
	private boolean enumType;
	private DbType dbType;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbColumn [name=");
		builder.append(name);
		builder.append(", pk=");
		builder.append(pk);
		builder.append(", nullable=");
		builder.append(nullable);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", enumType=");
		builder.append(enumType);
		builder.append(", dbType=");
		builder.append(dbType);
		builder.append("]");
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isEnumType() {
		return enumType;
	}

	public void setEnumType(boolean enumType) {
		this.enumType = enumType;
	}

	public DbType getDbType() {
		return dbType;
	}

	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}

}
