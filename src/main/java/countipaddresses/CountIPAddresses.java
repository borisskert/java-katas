package countipaddresses;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <a href="https://www.codewars.com/kata/526989a41034285187000de4/train/java">Count IP Addresses</a>
 */
public class CountIPAddresses {

    public static long ipsBetween(String start, String end) {
        return IpV4.parse(start)
                .diff(IpV4.parse(end));
    }
}

class IpV4 {
    private final InetAddress address;

    private IpV4(InetAddress address) {
        this.address = address;
    }

    public long diff(IpV4 other) {
        long result = 0;

        byte[] startBytes = this.address.getAddress();
        byte[] endBytes = other.address.getAddress();

        for (int i = 0; i < 4; i++) {
            result <<= 8;
            result += (endBytes[i] & 0xFF) - (startBytes[i] & 0xFF);
        }

        return result;
    }

    public static IpV4 parse(String address) {
        try {
            return new IpV4(Inet4Address.getByName(address));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
