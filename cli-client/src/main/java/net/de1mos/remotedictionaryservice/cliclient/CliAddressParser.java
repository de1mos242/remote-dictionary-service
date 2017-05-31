package net.de1mos.remotedictionaryservice.cliclient;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Parse address to server
 */
public class CliAddressParser {
    private boolean isValid;
    private String addressPart;
    private int port;
    private String cmdArg;

    public CliAddressParser(String cmdArg) {
        this.cmdArg = cmdArg;
        parse();
    }

    public boolean isValid() {
        return isValid;
    }

    public String getAddressPart() {
        return addressPart;
    }

    public int getPort() {
        return port;
    }

    private void parse() {
        isValid = checkIsValid();
        if (!isValid) {
            return;
        }

        URL url = getAsURL(cmdArg);
        addressPart = url.getHost();
        port = url.getPort();
        if (port < 0) {
            // default port
            port = 80;
        }
    }

    private boolean checkIsValid() {
        URL url = getAsURL(cmdArg);
        if (url == null) {
            return false;
        }
        if (!url.getPath().isEmpty()) {
            return false;
        }
        return true;
    }

    private URL getAsURL(String url) {

        URL u;

        try {
            u = new URL(String.format("http://%s", url));
        } catch (MalformedURLException e) {
            return null;
        }

        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return null;
        }

        return u;
    }
}
