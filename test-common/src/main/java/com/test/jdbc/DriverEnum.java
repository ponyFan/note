package com.test.jdbc;

/**
 * @author zelei.fan
 * @date 2019/11/11 18:07
 */
public enum DriverEnum {

    DB2("com.ibm.db2.jcc.DB2Driver"),
    HSQLDB("org.hsqldb.jdbcDriver"),
    MARIADB("org.mariadb.jdbc.Driver"),
    MYSQL("com.mysql.jdbc.Driver"),
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    POSTGRESQL("org.postgresql.Driver"),
    SQLSERVER("net.sourceforge.jtds.jdbc.Driver"),
    HIVE("org.apache.hive.jdbc.HiveDriver");

    private String clazz;

    DriverEnum(String clazz) {
        this.clazz = clazz;
    }

    public String getDriverClass() {
        return clazz;
    }

}
