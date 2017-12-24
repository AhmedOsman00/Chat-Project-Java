package server;

import java.rmi.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import oracle.jdbc.*;
import rmiinterfaces.*;

public class DataBaseConnection implements Service {

    private Connection connection;
    private PreparedStatement preparedStmt;
    private Statement stmt;
    private ResultSet resultSet;
    private static DataBaseConnection instance = new DataBaseConnection();
    private ServerImpl serverImpl;
    private ArrayList<Client> contactlist;
    private ArrayList<Client> requestsList;

    private DataBaseConnection() {
        serverImpl = (ServerImpl) ServiceLocator.getService("serverImpl");
        try {
            DriverManager.registerDriver(new OracleDriver());
            connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "chat", "year");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DataBaseConnection getInstance() {
        return instance;
    }

    public void insertAdminInfo() {
        try {
            preparedStmt = connection.prepareStatement("INSERT INTO admin_data (admin_id, admin_user_name,"
                    + " admin_password, admin_gender ,admin_address, admin_email ) VALUES (?,?,?,?,?,?)",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStmt.setInt(1, 1);
            preparedStmt.setString(2, "");
            preparedStmt.setString(3, "");
            preparedStmt.setString(4, "");
            preparedStmt.setString(5, "");
            preparedStmt.setString(6, "");
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Client> searchUserName(String client_user_name) {
        ArrayList<Client> matchedUsers = new ArrayList<>();
        try {
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery("select client_user_name  from client_data where client_user_name like %#%");
            while (resultSet.next()) {
                Client client = new Client();
                client.setClient_image(resultSet.getString("client_pic"));
                client.setClient_name(resultSet.getString("client_name"));
                client.setClient_user_name(resultSet.getString("client_user_name"));
                matchedUsers.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matchedUsers;
    }

    public Boolean clientValidate(String client_user_name, String password) throws SQLException {
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        resultSet = stmt.executeQuery("select * from client_data where client_user_name = #");
        if (resultSet.first()) {
            if (resultSet.getString("client_password").equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void insertClientInfo(ClientRegData clientRegData) {
        try {
            preparedStmt = connection.prepareStatement("INSERT INTO client_data (client_pic,"
                    + "client_user_name,client_password,client_gender,client_status,client_address,client_name) "
                    + "VALUES (?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStmt.setString(1, "utl_raw.cast_to_raw('D:\\automobile.png')");
            preparedStmt.setString(2, clientRegData.getClient_user_name());
            preparedStmt.setString(3, clientRegData.getPassword());
            preparedStmt.setString(4, clientRegData.getGender());
            preparedStmt.setString(5, "online");
            preparedStmt.setString(6, clientRegData.getAddress());
            preparedStmt.setString(7, clientRegData.getClient_name());
            preparedStmt.executeUpdate();
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

    public Client fillData(String username) throws SQLException, RemoteException {
        Client client = new Client();
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        resultSet = stmt.executeQuery("select * from client_data where client_user_name = #");
        if (resultSet.next()) {
            client.setClient_image(resultSet.getString("client_pic"));
            client.setClient_status(resultSet.getString("client_status"));
            client.setClient_name(resultSet.getString("client_name"));
            return client;
        }
        return null;
    }

    public void setStatus(String status) {
        try {
            preparedStmt = connection.prepareStatement("INSERT INTO client_data (client_status) VALUES (?)",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStmt.setString(1, "");
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Client> getContactList() {
        try {
            contactlist = new ArrayList<>();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery("select * from contact_list cl join client_data cd "
                    + "on cl.client_user_name = cd.client_user_name where cl.client_user_name= #");
            if (resultSet.next()) {
                Client client = new Client();
                client.setClient_user_name(resultSet.getString("client_user_name"));
                client.setClient_name("client_name");
                client.setClient_image("client_pic");
                client.setClient_status("client_status");
                contactlist.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contactlist;
    }

    public ArrayList<Client> getAllRequests() {
        try {
            requestsList = new ArrayList<>();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery("select * from requests rq join client_data cd "
                    + "on rq.client_user_name = cd.client_user_name where rq.client_user_name= #");
            if (resultSet.next()) {
                Client client = new Client();
                client.setClient_user_name(resultSet.getString("client_user_name"));
                client.setClient_name("client_name");
                client.setClient_image("client_pic");
                client.setClient_status("client_status");
                requestsList.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requestsList;
    }

    public void acceptContact(Client client, Client currClient) throws RemoteException {
        try {
            preparedStmt = connection.prepareStatement("INSERT INTO contact_list (contact_user_name, client_user_name)"
                    + "VALUES (?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStmt.setString(1, client.getClient_user_name());
            preparedStmt.setString(2, currClient.getClient_user_name());
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeRequest(String reqSender, String reqReceiver) throws SQLException, RemoteException {
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.executeUpdate("delete from requests where sender_user_name = # and receiver_user_name = #");
    }

    public void addToRequestsTable(Client client, Client currClient) {
        try {
            preparedStmt = connection.prepareStatement("INSERT INTO requests (receiver_user_name, sender_user_name)"
                    + "VALUES (?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStmt.setString(1, currClient.getClient_user_name());
            preparedStmt.setString(2, client.getClient_user_name());
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Boolean uniqueUserName(String username) throws SQLException {
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        resultSet = stmt.executeQuery("select * from client_data where client_user_name = #");
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "dbConn";
    }

    @Override
    public void excute() {
    }

}
