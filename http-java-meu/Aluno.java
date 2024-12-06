public class Aluno{
    private static int idContador;
    private String nome;
    private int id;

    public Aluno(String nome, int id){
        this.nome = nome;
        this.id = idContador++;
    }

    public String getNome(){
        return this.nome;
    }

    public int getId(){
        return this.id;
    }

    public String toHtml(){
        return "<html><body>" +
        "<h1>Aluno: " + nome + "</h1>" +
        "<p>ID: " + id + "</p>" +
        "</body></html>";
    }
}