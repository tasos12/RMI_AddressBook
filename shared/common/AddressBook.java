package common;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.rmi.*;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AddressBook extends Remote{

    @WebMethod String insert(BookEntry entry) throws RemoteException;

    @WebMethod String update(int id, BookEntry entry) throws RemoteException;

    @WebMethod String select(int id) throws RemoteException;

    @WebMethod String delete(int id) throws RemoteException;

    @WebMethod String selectAll() throws RemoteException;
}
