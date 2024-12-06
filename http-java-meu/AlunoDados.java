import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlunoDados{
    private List<Aluno> alunos = new ArrayList<>();
    private int idCounter = 0;

    public Aluno POSTAluno(){
    String nomeAleatorio = gerarNomeAleatorio();
      Aluno NovoAluno = new Aluno(nomeAleatorio, idCounter);
      alunos.add(NovoAluno);
      return NovoAluno;
    }

    public Aluno GETAluno(int id){
        for(Aluno aluno: alunos){
            if(aluno.getId() == id){
                return aluno;
            }
        }
        return null;
    }

    public boolean DELETEAluno(int id){
       Aluno aluno = GETAluno(id);
       if(aluno != null){
        alunos.remove(aluno);
        return true;
       }
       return false;
    }

     private String gerarNomeAleatorio() {
        String[] nomes = {"João", "Maria", "Lucas", "Andre","Pedro", "Ana", "Carlos", "Beatriz", "Marcio", "Fernanda", "Gustavo", "Cláudia"};
        Random rand = new Random();
        int indice = rand.nextInt(nomes.length); 
        return nomes[indice];
    }




}