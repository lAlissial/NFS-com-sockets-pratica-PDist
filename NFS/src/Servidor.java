import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Servidor {
    public static void main(String[] args) throws Exception {
        final String RAIZ = "///home/ifpb/Downloads/NFS-20220906T195609Z-001/NFS/home/";

        ServerSocket server = new ServerSocket(7000);
        File file = new File(RAIZ);
        
        System.out.println("== SERVIDOR ==");

        while(true) {
            Socket client = server.accept();

            System.out.println(String.format("Cliente %s:%s", client.getInetAddress(), client.getPort()));

            try {
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                DataInputStream  input  = new DataInputStream(client.getInputStream());

                output.writeUTF("Comandos:\nreaddir - Listar arquivos do diretório;\ncreate {nome do arquivo} - Criar arquivo" +
                "\nremove {nome do arquivo} - Excluir arquivo\nrename {nome do arquivo} {novo nome para o arquivo} - Renomear um arquivo");

                while(true) {
                    List<String> dir = new ArrayList<>();
                    String response  = input.readUTF();
                    String command   = response.split(" ")[0];

                    if(command.equalsIgnoreCase("readdir")) {
                        dir = Arrays.stream(file.list()).toList();

                        output.writeUTF(dir.toString());
                    }
                    else if(command.equalsIgnoreCase("rename")) {
                        File old = new File(RAIZ + response.split(" ")[1]);
                        boolean success = old.renameTo(new File(RAIZ + response.split(" ")[2]));

                        if(success) {
                            output.writeUTF("O arquivo foi renomeado");
                        }
                        else {
                            output.writeUTF("Não foi possível renomear o arquivo");
                        }
                    } else if(command.equalsIgnoreCase("create")) {
                        File arq  = new File(RAIZ + response.split(" ")[1]);

                        boolean success = arq.createNewFile();

                        if(success) {
                            output.writeUTF("O arquivo foi criado");
                        }
                        else {
                            output.writeUTF("Não foi possível criar o arquivo");
                        }
                    }
                    else if(command.equalsIgnoreCase("remove")){
                        File arq  = new File(RAIZ + response.split(" ")[1]);

                        boolean success = arq.delete();

                        if(success) {
                            output.writeUTF("O arquivo foi excluído");
                        }
                        else {
                            output.writeUTF("Não foi possível excluir o arquivo");
                        }
                    }
                    else {
                        output.writeUTF("Comando não identificado");
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
