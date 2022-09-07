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
        //final String RAIZ = "///home/ifpb/Downloads/NFS-20220906T195609Z-001/NFS/home/";
        String RAIZ = "D:/TCSI/pVI/ProgramacaoDistribuida/NFS-com_sockets/NFS/home/";

        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("windows")){
            RAIZ = RAIZ.replace("/","\\"); //File.separator
        }

        ServerSocket server = new ServerSocket(7000);
        File file = new File(RAIZ);

        System.out.println("----------------");
        System.out.println("=== SERVIDOR ===");
        System.out.println("----------------");


        while(true) {
            Socket client = server.accept();

            System.out.println(String.format("Cliente %s:%s", client.getInetAddress(), client.getPort()));

            try {
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                DataInputStream  input  = new DataInputStream(client.getInputStream());

                output.writeUTF(
                            "*************************************************************************************\n" +
                                    "--------------------------------------- COMANDOS ------------------------------------\n" +
                                    "readdir                                             -> Listar arquivos do diretório\n" +
                                    "create {nome do arquivo}                            -> Criar arquivo\n" +
                                    "remove {nome do arquivo}                            -> Excluir arquivo\n" +
                                    "rename {nome do arquivo} {novo nome para o arquivo} -> Renomear um arquivo\n" +
                                    "*************************************************************************************");


                while(true) {
                    List<String> dir = new ArrayList<>();
                    String response  = input.readUTF();
                    String command   = response.split(" ")[0];
                    String resultado;


                    if(command.equalsIgnoreCase("readdir")) {
                        dir = Arrays.stream(file.list()).toList();

                        resultado = String.join("\n", dir);

                    }
                    else if(command.equalsIgnoreCase("rename")) {
                        File old = new File(RAIZ + response.split(" ")[1]);

                        boolean success = old.renameTo(new File(RAIZ + response.split(" ")[2]));
                        resultado = success ? "O arquivo foi renomeado" : "Não foi possível renomear o arquivo";


                    } else if(command.equalsIgnoreCase("create")) {
                        File arq  = new File(RAIZ + response.split(" ")[1]);

                        boolean success = arq.createNewFile();
                        resultado = success ? "O arquivo foi criado" : "Não foi possível criar o arquivo";

                    }
                    else if(command.equalsIgnoreCase("remove")){
                        File arq  = new File(RAIZ + response.split(" ")[1]);

                        boolean success = arq.delete();
                        resultado = success ? "O arquivo foi excluído" : "Não foi possível excluir o arquivo";

                    }
                    else {
                        resultado = "Comando não identificado";
                    }

                    output.writeUTF(resultado);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
