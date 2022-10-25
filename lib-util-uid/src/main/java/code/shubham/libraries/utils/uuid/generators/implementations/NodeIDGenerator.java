package code.shubham.libraries.utils.uuid.generators.implementations;

import code.shubham.libraries.utils.uuid.generators.IDGenerator;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;

import static code.shubham.libraries.utils.uuid.constants.Constants.NODE_ID_BIT_LENGTH;

public class NodeIDGenerator implements IDGenerator<Long> {

    private static final long MAX_NODE_ID_VALUE = (1L << NODE_ID_BIT_LENGTH) - 1;

    public Long generate() {
        long nodeId;
        try {
            StringBuilder builder = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        builder.append(String.format("%02X", mac[i]));
                    }
                }
            }
            nodeId = builder.toString().hashCode();
        } catch (final SocketException se) {
            //in case of exception get a code.shubham.random number limited by max node size
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId &= MAX_NODE_ID_VALUE;
        return nodeId;
    }

}
