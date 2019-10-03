package com.cybozu.kintone.client.connection;

import java.net.*;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.io.*;
import javax.net.ssl.*;

/**
 * SSLSocket used to tunnel through a SSL-secured proxy
 */
class SSLSocketFactoryForHttpsProxy extends SSLSocketFactory {
    private String proxyHost;
    private int proxyPort;
    private SSLSocketFactory dfactory;
    private String proxyPassword;
    private String proxyUser;
    private boolean socketConnected = false;

    /**
     * Constructor for the SSLSocketFactoryForHttpsProxy object
     *
     * @param dfactory The socket factory used for setting client certificate
     */
    public SSLSocketFactoryForHttpsProxy(SSLSocketFactory dfactory) {
        System.err.println("creating Socket Factory");
        this.dfactory = dfactory;
    }

    /**
     * Constructor for the SSLSocketFactoryForHttpsProxy object
     *
     * @param proxyHost The url of the proxy host
     * @param proxyPort the port of the proxy
     */
    public SSLSocketFactoryForHttpsProxy(String proxyHost, String proxyPort) {
        System.err.println("creating Socket Factory");
        this.proxyHost = proxyHost;
        this.proxyPort = Integer.parseInt(proxyPort);
        dfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    /**
     * Constructor for the SSLSocketFactoryForHttpsProxy object
     *
     * @param dfactory  The socket factory used for setting client certificate
     * @param proxyHost The url of the proxy host
     * @param proxyPort the port of the proxy
     */
    public SSLSocketFactoryForHttpsProxy(SSLSocketFactory dfactory, String proxyHost, String proxyPort) {
        System.err.println("creating Socket Factory");
        this.proxyHost = proxyHost;
        this.proxyPort = Integer.parseInt(proxyPort);
        this.dfactory = dfactory;
    }

    /**
     * Constructor for the SSLSocketFactoryForHttpsProxy object
     *
     * @param proxyHost     The url of the proxy host
     * @param proxyPort     the port of the proxy
     * @param proxyUserName username for authenticating with the proxy
     * @param proxyPassword password for authenticating with the proxy
     */
    public SSLSocketFactoryForHttpsProxy(String proxyHost, String proxyPort, String proxyUserName,
                                         String proxyPassword) {
        System.err.println("creating Socket Factory with password/username");
        this.proxyHost = proxyHost;
        this.proxyPort = Integer.parseInt(proxyPort);
        this.proxyUser = proxyUserName;
        this.proxyPassword = proxyPassword;
        this.dfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    /**
     * Constructor for the SSLSocketFactoryForHttpsProxy object
     *
     * @param dfactory      The socket factory used for setting client certificate
     * @param proxyHost     The url of the proxy host
     * @param proxyPort     the port of the proxy
     * @param proxyUserName username for authenticating with the proxy
     * @param proxyPassword password for authenticating with the proxy
     */
    public SSLSocketFactoryForHttpsProxy(SSLSocketFactory dfactory, String proxyHost, String proxyPort,
                                         String proxyUserName, String proxyPassword) {
        System.err.println("creating Socket Factory with password/username");
        this.proxyHost = proxyHost;
        this.proxyPort = Integer.parseInt(proxyPort);
        this.proxyUser = proxyUserName;
        this.proxyPassword = proxyPassword;
        this.dfactory = dfactory;
    }

    public SSLSocketFactoryForHttpsProxy() {
        this.dfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    /**
     * Sets the proxyHost attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @param proxyHost The new proxyHost value
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * Sets the proxyPort attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @param proxyPort The new proxyPort value
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * Sets the proxyUserName attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @param proxyUserName The new proxyUserName value
     */
    public void setProxyUserName(String proxyUserName) {
        proxyUser = proxyUserName;
    }

    /**
     * Sets the proxyPassword attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @param proxyPassword The new proxyPassword value
     */
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    /**
     * Gets the supportedCipherSuites attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @return The supportedCipherSuites value
     */
    public String[] getSupportedCipherSuites() {
        return dfactory.getSupportedCipherSuites();
    }

    /**
     * Gets the defaultCipherSuites attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @return The defaultCipherSuites value
     */
    public String[] getDefaultCipherSuites() {
        return dfactory.getDefaultCipherSuites();
    }

    /**
     * Gets the socketConnected attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @return The socketConnected value
     */
    public synchronized boolean getSocketConnected() {
        return socketConnected;
    }

    /**
     * Creates a new SSL Tunneled Socket
     *
     * @param s         Ignored
     * @param host      destination host
     * @param port      destination port
     * @param autoClose wether to close the socket automaticly
     * @return proxy tunneled socket
     * @throws IOException          raised by an IO error
     * @throws UnknownHostException raised when the host is unknown
     */
    public Socket createSocket(Socket s, String host, int port, boolean autoClose)
            throws IOException, UnknownHostException {
        SSLSocket tunnel = (SSLSocket) dfactory.createSocket(proxyHost, proxyPort);
        doTunnelHandshake(tunnel, host, port);
        SSLSocket result = (SSLSocket) dfactory.createSocket(tunnel, host, port, autoClose);
        result.addHandshakeCompletedListener(new HandshakeCompletedListener() {
            public void handshakeCompleted(HandshakeCompletedEvent event) {
                System.out.println("Handshake Finished!");
                System.out.println("\t CipherSuite :" + event.getCipherSuite());
                System.out.println("\t SessionId: " + event.getSession());
                System.out.println("\t PeerHost: " + event.getSession().getPeerHost());
                setSocketConnected(true);
            }
        });
        return result;
    }

    /**
     * Creates a new SSL Tunneled Socket
     *
     * @param host destination host
     * @param port destination port
     * @return tunneled SSL Socket
     * @throws IOException          raised by IO error
     * @throws UnknownHostException raised when the host is unknown
     */
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return createSocket(null, host, port, true);
    }

    /**
     * Creates a new SSL Tunneled Socket
     *
     * @param host       Destination Host
     * @param port       Destination Port
     * @param clientHost Ignored
     * @param clientPort Ignored
     * @return SSL Tunneled Socket
     * @throws IOException          Raised when IO error occurs
     * @throws UnknownHostException Raised when the destination host is unknown
     */
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
            throws IOException, UnknownHostException {
        return createSocket(null, host, port, true);
    }

    /**
     * Creates a new SSL Tunneled Socket
     *
     * @param host destination host
     * @param port destination port
     * @return tunneled SSL Socket
     * @throws IOException raised when IO error occurs
     */
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return createSocket(null, host.getHostName(), port, true);
    }

    /**
     * Creates a new SSL Tunneled Socket
     *
     * @param address       destination host
     * @param port          destination port
     * @param clientAddress ignored
     * @param clientPort    ignored
     * @return tunneled SSL Socket
     * @throws IOException raised when IO exception occurs
     */
    public Socket createSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort)
            throws IOException {
        return createSocket(null, address.getHostName(), port, true);
    }

    /**
     * Sets the socketConnected attribute of the SSLSocketFactoryForHttpsProxy object
     *
     * @param b The new socketConnected value
     */
    private synchronized void setSocketConnected(boolean b) {
        socketConnected = b;
    }

    /**
     * Description of the Method
     *
     * @param tunnel tunnel socket
     * @param host   destination host
     * @param port   destination port
     * @throws IOException raised when an IO error occurs
     */
    private void doTunnelHandshake(SSLSocket tunnel, String host, int port) throws IOException {
        OutputStream out = tunnel.getOutputStream();
        // generate connection string
        String msg = "CONNECT " + host + ":" + port + " HTTP/1.1\n" + "User-Agent: " + Connection.userAgent;
        if (proxyUser != null && proxyPassword != null) {
            // add basic authentication header for the proxy
            Encoder enc = Base64.getEncoder();
            String encodedPassword = new String(enc.encode((proxyUser + ":" + proxyPassword).getBytes()));
            msg = msg + "\nProxy-Authorization: Basic " + encodedPassword;
        }
        msg = msg + "\nContent-Length: 0";
        msg = msg + "\nPragma: no-cache";
        msg = msg + "\r\n\r\n";

        System.err.println(msg);
        byte b[];
        try {
            // we really do want ASCII7 as the http protocol doesnt change with locale
            b = msg.getBytes("ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            // If ASCII7 isn't there, something is seriously wrong!
            b = msg.getBytes();
        }
        out.write(b);
        out.flush();

        byte reply[] = new byte[200];
        int replyLen = 0;
        int newlinesSeen = 0;
        boolean headerDone = false;

        InputStream in = tunnel.getInputStream();
        while (newlinesSeen < 2) {
            int i = in.read();
            if (i < 0) {
                throw new IOException("Unexpected EOF from Proxy");
            }
            if (i == '\n') {
                headerDone = true;
                ++newlinesSeen;
            } else if (i != '\r') {
                newlinesSeen = 0;
                if (!headerDone && replyLen < reply.length) {
                    reply[replyLen++] = (byte) i;
                }
            }
        }

        // convert byte array to string
        String replyStr;
        try {
            replyStr = new String(reply, 0, replyLen, "ASCII7");
        } catch (UnsupportedEncodingException ignored) {
            replyStr = new String(reply, 0, replyLen);
        }

        // we check for connection established because our proxy returns http/1.1
        // instead of 1.0
        if (replyStr.toLowerCase().indexOf("200 connection established") == -1) {
            System.err.println(replyStr);
            throw new IOException(
                    "Unable to tunnel through " + proxyHost + ":" + proxyPort + ". Proxy returns\"" + replyStr + "\"");
        }
        // tunneling hanshake was successful
    }
}
