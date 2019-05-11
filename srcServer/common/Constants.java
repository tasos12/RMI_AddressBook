package common;

import java.rmi.registry.Registry;

public class Constants {

    public static final String HOST = "localhost";
    public static final int PORT_DB = 3306;
    public static final String TABLE = "addressbook";
    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT_DB + "/" + TABLE;
    public static final String USERNAME = "tasos";
    public static final String PASSWORD = "password";
    public static final int PORT_RMI = Registry.REGISTRY_PORT;
    public static final String OBJ_NAME = "common.common.AddressBook";

    public static final String ADDRESSBOOK_QUEUE = "AddressBook";
    public static final String METHOD_KEY = "method";
    public static final String ID_KEY = "id";

    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String SELECT = "select";
    public static final String DELETE = "delete";
    public static final String SELECTALL = "selectall";
}
