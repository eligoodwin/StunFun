import java.net.Socket;

public class ClientHandler extends Thread{
    final Socket clientSocket;
    public ClientHandler(Socket socket){
        clientSocket = socket;
    }

    @Override
    public void run(){
        
    }


}
