import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import command.Command;

public class Servidor {
    public static void main(String[] args) throws Exception {

        final String HOME = System.getProperty("user.home");
        ServerSocket server = new ServerSocket(7000);

        System.out.println("----------------");
        System.out.println("=== SERVIDOR ===");
        System.out.println("----------------");

        Command command2 = new Command(HOME);
        command2.create();

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
                                    "*************************************************************************************"
                );


                while(true) {
                    String response  = input.readUTF();
                    String command   = response.split(" ")[0];
                    String resultado;


                    if(command.equalsIgnoreCase("readdir")) {
                        resultado = command2.list();

                    }
                    else if(command.equalsIgnoreCase("rename")) {
                        resultado = command2.rename(response.split(" ")[1], response.split(" ")[2]);


                    } else if(command.equalsIgnoreCase("create")) {
                        resultado = command2.create(response.split(" ")[1]);
                    }
                    else if(command.equalsIgnoreCase("remove")){
                        resultado = command2.remove(response.split(" ")[1]);
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
