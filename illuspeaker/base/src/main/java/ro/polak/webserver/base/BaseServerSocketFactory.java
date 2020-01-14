
// Author: Pierce Brooks

package ro.polak.webserver.base;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;

public class BaseServerSocketFactory extends ServerSocketFactory {
    private ServerSocketFactory base;

    public BaseServerSocketFactory() {
        base = ServerSocketFactory.getDefault();
    }

    @Override
    public ServerSocket createServerSocket() throws IOException {
        return base.createServerSocket();
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return base.createServerSocket(port);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog) throws IOException {
        return base.createServerSocket(port, backlog);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
        return base.createServerSocket(port, backlog, ifAddress);
    }
}
