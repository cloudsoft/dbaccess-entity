package io.cloudsoft.dbaccess.client;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class MySqlAccessClient extends AbstractDatabaseAccessClient {
    
    private static final String CREATE_USER = "CREATE USER '%s'@'%s' IDENTIFIED BY '%s'";
    private static final String GRANT_PERMISSIONS = "GRANT SELECT ON %s.* TO '%s'@'%s';";
    private static final String DROP_USER = "DROP USER '%s'@'%s';";

    public MySqlAccessClient(String endpoint, String adminUsername, String adminPassword, String database) {
        super(endpoint, adminUsername, adminPassword, database);
    }

    @Override
    protected String getDriverClass() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    protected List<String> getGrantPermissionsStatements(String username, String password) {
        return ImmutableList.of(
                String.format(GRANT_PERMISSIONS, getDatabase(), username, "localhost"),
                String.format(GRANT_PERMISSIONS, getDatabase(), username, "%"));
    }

    @Override
    protected List<String> getCreateUserStatements(String username, String password) {
        return ImmutableList.of(String.format(CREATE_USER, username, "%", password),
                String.format(CREATE_USER, username, "localhost", password));
    }

    @Override
    protected List<String> getDeleteUserStatements(String username) {
        return ImmutableList.of(String.format(DROP_USER, username, "%"),
                String.format(DROP_USER, username, "localhost"));
    }

}
