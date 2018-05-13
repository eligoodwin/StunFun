import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class StunFun {
    private static Set<User> userList = new HashSet<>();

    public static void main(String[] Args) throws IOException {
        ServerSocket listener = new ServerSocket(8080);
        try {
            while (true) {
                Socket clientSocket = listener.accept();

                SocketAddress clientSocketAddress = clientSocket.getRemoteSocketAddress();
                String[] socketAddressInfo = getAddressInfo(clientSocketAddress.toString());
                //get info
                BufferedReader bufferedReader = getBuffer(clientSocket);
                String username = bufferedReader.readLine();
                String parsedAddress = String.format("IP Address: %s\t Port: %s", socketAddressInfo[1], socketAddressInfo[2]);
                String message = String.format("Client Address: %s\tDate: %s", parsedAddress, new Date().toString());
                try {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println(message);

                    System.out.printf("Connection info: %s\t%s\n", username, parsedAddress);
                    //save user
                    userList.add(new User(Integer.parseInt(socketAddressInfo[2]), socketAddressInfo[1], username));

                    //wait for user info request
                    bufferedReader = getBuffer(clientSocket);
                    String targetUser = bufferedReader.readLine();
                    System.out.printf("User:\t%s whats to initiate chat with:\t%s\n", username, targetUser);
                    String userInfo = findByUsername(targetUser);
                    if(userInfo == null){
                        out.println("User not found");
                        System.out.println("User could not be found");
                        clientSocket.close();
                    }
                    else{
                        out.println(userInfo);
                    }

                } finally {
                    clientSocket.close();
                }
            }
        }
        finally{
            listener.close();
        }
    }

    public static String[] getAddressInfo(String clientSocket){
        final String delims = "[/:]";
        return clientSocket.split(delims);
    }
    public static PrintWriter writeToBuffer(Socket socket) throws IOException{
        OutputStream out = socket.getOutputStream();
        return new PrintWriter(out, true);
    }

    public static BufferedReader getBuffer(Socket connectionClient) throws IOException{
        InputStream inputStream = connectionClient.getInputStream();
        return new BufferedReader((new InputStreamReader(inputStream)));
    }

    private static String findByUsername(String username){
        for(User user : userList){
            if(user.getUsername().equals(username)){
                return user.getAddress();
            }
        }
        return null;
    }

    private static void itrThroughList(){
        for(User user : userList){
            System.out.println(user.toString());
        }
    }


}
