public class User {
    private final int port;
    private final String username;
    private final String ipAddress;

    public User(int port, String ipAddress, String username) {
        this.port = port;
        this.username = username;
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString(){
        return String.format("username: %s ipAddress: %s port: %d", this.username, this.ipAddress, this.port);
    }

    public String getAddress(){
        return String.format("%s:%d", this.ipAddress, this.port);
    }
}
