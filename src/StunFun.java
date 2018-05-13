
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;


public class StunFun {
    private static List<User> userList = new ArrayList<>();
    public static void main(String[] Args) throws IOException {
        userList.add(new User("Anna"));
        userList.add(new User("Bob"));

        ServerSocket listener = new ServerSocket(8080);
        try {
            while (true) {
                Socket clientSocket = listener.accept();
                new ClientThread(clientSocket, userList).start();
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


    static class ClientThread extends Thread {
        private Socket clientSocket;
        private List<User> userList;

        public ClientThread(Socket client, List<User> users) {
            this.clientSocket = client;
            this.userList = users;
        }

        @Override
        public void run() {
            super.run();
            SocketAddress clientSocketAddress = clientSocket.getRemoteSocketAddress();
            String[] socketAddressInfo = getAddressInfo(clientSocketAddress.toString());
            //get info
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = getBuffer(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username = null;
            try {
                username = bufferedReader.readLine();
                System.out.printf("Requesting User: %s\n", username);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //1. display username and address info
            String message = String.format("User: %s ipAddress: %s Port: %s", username, socketAddressInfo[1], socketAddressInfo[2]);
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(message);
                System.out.printf("Connection info: %s\n", message);
                //2. put info into into anna/bob user
                if (username.equals("Anna")) {
                    userList.get(0).setIpAddress(socketAddressInfo[1]);
                    userList.get(0).setPort(Integer.parseInt(socketAddressInfo[2]));
                } else {
                    userList.get(1).setIpAddress(socketAddressInfo[1]);
                    userList.get(1).setPort(Integer.parseInt(socketAddressInfo[2]));
                }
                //3. parse request
                bufferedReader = getBuffer(clientSocket);
                String request = bufferedReader.readLine();
                if(request.equals("Bob")){
                    out.println(userList.get(1).getAddress());
                }
                else{
                    out.println(userList.get(0).getAddress());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
