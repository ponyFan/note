package com.test.mybatis;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author zelei.fan
 * @date 2019/11/12 17:49
 */
@Data
@Builder
@ToString
public class TableInfoDO {

	private String id;
	private String connectionId;
	private String db;
	private String dbCode;
	private String tableName;
	private String tableCode;
	private String columnName;
	private String columnType;
	private String remark;
}
