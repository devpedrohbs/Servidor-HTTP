import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientProcessor implements Runnable {

    private Socket clienteSocket;
    private AlunoDados alunoDados;

    public ClientProcessor(Socket clienteSocket, AlunoDados alunoDados) {
        this.clienteSocket = clienteSocket;
        this.alunoDados = alunoDados;
    }

    @Override
    public void run() {
        try {
           
            BufferedReader input = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clienteSocket.getOutputStream(), true);

         
            String linha = input.readLine();
            if (linha != null && !linha.isEmpty()) {
                String[] requestParts = linha.split(" ");

                if (requestParts.length > 1) {
                    String metodo = requestParts[0];  
                    String caminho = requestParts[1]; 

                    if (metodo.equals("GET")) {
                        processarGET(caminho, output);
                    } else if (metodo.equals("DELETE")) {
                        processarDELETE(caminho, output);
                    } else if (metodo.equals("POST")) {
                        processarPOST(output);
                    } else {
                        respostaErro(output, "Método não suportado");
                    }
                }
            }

           
            input.close();
            output.close();
            clienteSocket.close();
        } catch (IOException e) {
            System.err.println("Erro ao processar requisição: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    private void processarGET(String caminho, PrintWriter output) {
        int id = Integer.parseInt(caminho.split("/")[2]);
        Aluno aluno = alunoDados.GETAluno(id);

        if (aluno != null) {
        
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=UTF-8");
            output.println();  
            output.println(aluno.toHtml());  
        } else {
            
            output.println("HTTP/1.1 404 Not Found");
            output.println("Content-Type: text/html; charset=UTF-8");
            output.println();  
            output.println("<html><body><h1>Aluno não encontrado</h1></body></html>");
        }
    }

  
    private void processarDELETE(String caminho, PrintWriter output) {
        int id = Integer.parseInt(caminho.split("/")[2]); 
        boolean deletado = alunoDados.DELETEAluno(id);

        if (deletado) {
            output.println("HTTP/1.1 200 OK\nContent-Type: text/html\n\n" + "<html><body><h1>Aluno excluído com sucesso</h1></body></html>");
        } else {
            respostaErro(output, "Aluno não encontrado para exclusão");
        }
    }

  
    private void processarPOST(PrintWriter output) {
        Aluno novoAluno = alunoDados.POSTAluno();
        output.println("HTTP/1.1 201 Created\nContent-Type: text/html\n\n" +
                "<html><body><h1>Aluno criado com sucesso</h1><p>ID: " + novoAluno.getId() + "</p></body></html>");
    }


    private void respostaErro(PrintWriter output, String mensagem) {
        output.println("HTTP/1.1 404 Not Found\nContent-Type: text/html\n\n" +
                "<html><body><h1>" + mensagem + "</h1></body></html>");
    }
}
