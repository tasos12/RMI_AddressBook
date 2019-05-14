package webServiceServer;

import common.AddressBookImpl;

import javax.xml.ws.Endpoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static common.Constants.*;

public class AddressBookWebServiceServer {

    private static AddressBookImpl addressBook;

    public static void main(String[] args) throws Exception{
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement st = conn.createStatement();
        addressBook = new AddressBookImpl(st);
        Endpoint.publish("http://localhost:8881/addressBook", addressBook);
    }
}
