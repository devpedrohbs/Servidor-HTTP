import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
    
        int porta = 12345;  

 
        AlunoDados alunoDados = new AlunoDados();

        try {
            ServerSocket serverSocket = new ServerSocket(porta);
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clienteSocket.getInetAddress());

                ClientProcessor clientProcessor = new ClientProcessor(clienteSocket, alunoDados);
                new Thread(clientProcessor).start();
            }

        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
