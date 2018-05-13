public class User {
    private  int port;
    private String username;
    private String ipAddress;

    public User(String username) {
        this.username = username;
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

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString(){
        return String.format("username: %s ipAddress: %s port: %d", this.username, this.ipAddress, this.port);
    }

    public String getAddress(){
        return String.format("%s:%d", this.ipAddress, this.port);
    }
}
