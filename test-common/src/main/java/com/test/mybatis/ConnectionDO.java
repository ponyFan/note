package com.test.mybatis;

import lombok.Data;
import lombok.ToString;

/**
 * @author zelei.fan
 * @date 2019/11/12 17:47
 */
@Data
@ToString
public class ConnectionDO {

	private String id;
	private String name;
	private String type;
	private String url;
	private String user;
	private String pwd;
	private String driver;
}
