import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {

        System.out.println("== Cliente ==");


        Socket socket = new Socket("10.0.4.65", 7000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        DataInputStream dis = new DataInputStream(socket.getInputStream());


        while (true) {
            Scanner teclado = new Scanner(System.in);

            dos.writeUTF(teclado.nextLine());

            String mensagem = dis.readUTF();
            System.out.println("Servidor falou: " + mensagem);
        }
    }
}