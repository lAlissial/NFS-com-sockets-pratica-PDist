package command;

import java.io.File;
import java.util.Arrays;

public class Command {

    private String PATH;
    private String OS;

    public Command(String caminho) {
        this.PATH   = caminho;
        this.OS     = System.getProperty("os.name");
    }

    public void create() {
        File raiz = this.OS.equalsIgnoreCase("windows") ? new File(PATH + "\\" + "home") : new File(PATH + "/" + "home");
        raiz.mkdir();
    }

    public String list() {
        File pasta    = this.OS.equalsIgnoreCase("windows") ? new File(PATH + "\\" + "home\\") : new File(PATH + "/" + "home/");
        String result = String.join("\n", Arrays.stream(pasta.list()).toList());
        return result;
    }

    public String create(String nome) throws Exception {
        File pasta    = this.OS.equalsIgnoreCase("windows") ? new File(PATH + "\\" + "home\\" + nome) : new File(PATH + "/" + "home/" + nome);
        String result = pasta.createNewFile() ? "O arquivo foi criado" : "Não foi possível criar o arquivo";
        return result;
    }

    public String rename(String antigo, String novo) {
        File old      = this.OS.equalsIgnoreCase("windows") ? new File(PATH + "\\" + "home\\" + antigo) : new File(PATH + "/" + "home/" + antigo);
        String result = old.renameTo(
            this.OS.equalsIgnoreCase("windows") ? new File(PATH + "\\" + "home\\" + novo) : new File(PATH + "/" + "home/" + novo)
        ) ? "O arquivo foi renomeado" : "Não foi possível renomear o arquivo";
        return result;
    }

    public String remove(String nome) {
        File pasta    = this.OS.equalsIgnoreCase("windows") ? new File(PATH + "\\" + "home\\" + nome) : new File(PATH + "/" + "home/" + nome);
        String result = pasta.delete() ? "O arquivo foi excluído" : "Não foi possível excluir o arquivo";
        return result;
    }

}
