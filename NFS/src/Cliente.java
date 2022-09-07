import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {

        System.out.println("---------------");
        System.out.println("=== CLIENTE ===");
        System.out.println("---------------");


        //Socket socket = new Socket("10.0.4.65", 7000);
        Socket socket = new Socket("localhost",7000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        DataInputStream dis = new DataInputStream(socket.getInputStream());

        System.out.println(dis.readUTF());

        while (true) {
            Scanner teclado = new Scanner(System.in);

            System.out.print("> ");
            dos.writeUTF(teclado.nextLine());

            String mensagem = dis.readUTF();
            System.out.println("Servidor falou: ");
            System.out.println(mensagem);
        }
    }
}