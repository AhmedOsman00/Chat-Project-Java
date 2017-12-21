package server;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.*;

public class DataBaseConnection implements Service {

    private Connection connection;
    private PreparedStatement adminInfoPrepStmt;
    private Statement stmt;
    private ResultSet resultSet;
    private static DataBaseConnection instance;
    private ServerImpl serverImpl;

    private DataBaseConnection() {
        instance = new DataBaseConnection();
        serverImpl = (ServerImpl) ServiceLocator.getService("ServerImpl");
        try {
            DriverManager.registerDriver(new OracleDriver());
            connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "chat", "year");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertAdminInfo() {
        try {
            adminInfoPrepStmt = connection.prepareStatement("INSERT INTO admin_data (admin_id,admin_user_name , admin_password , admin_gender , admin_address , admin_email ) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            adminInfoPrepStmt.setInt(1, 1);
            adminInfoPrepStmt.setString(2, "");
            adminInfoPrepStmt.setString(3, "");
            adminInfoPrepStmt.setString(4, "");
            adminInfoPrepStmt.setString(5, "");
            adminInfoPrepStmt.setString(6, "");
            adminInfoPrepStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean clientValidate() throws SQLException {
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        resultSet = stmt.executeQuery("select * from client_data where client_email = #");
        if (resultSet.first()) {
            serverImpl.register(null);
            return true;
        }
        return false;
    }

    public void insertClientInfo() {
        try {
            adminInfoPrepStmt = connection.prepareStatement("INSERT INTO client_data (client_id,client_pic,client_user_name,client_password,client_gender,client_status,client_address,client_email) VALUES (?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            adminInfoPrepStmt.setInt(1, 1);
            adminInfoPrepStmt.setString(2, "utl_raw.cast_to_raw('D:\\automobile.png')");
            adminInfoPrepStmt.setString(3, "");
            adminInfoPrepStmt.setString(4, "");
            adminInfoPrepStmt.setString(5, "");
            adminInfoPrepStmt.setString(6, "");
            adminInfoPrepStmt.setString(7, "");
            adminInfoPrepStmt.setString(8, "");
            adminInfoPrepStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStatus() throws SQLException {
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        resultSet = stmt.executeQuery("select client_status from client_data where client_id = #");
        if (resultSet.next()) {
            return resultSet.getString("client_status");
        }
        return null;
    }

    public void setStatus(String status) {
        try {
            adminInfoPrepStmt = connection.prepareStatement("INSERT INTO client_data (client_status) VALUES (?)",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            adminInfoPrepStmt.setString(1, "");
            adminInfoPrepStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DataBaseConnection getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "DBConn";
    }

    @Override
    public void excute() {
    }

}
