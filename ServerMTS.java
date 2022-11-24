import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServerMTS {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(5555);
		String name;
		System.out.println("ENTER YOUR NAME: ");
		Scanner scanner = new Scanner(System.in);
		name = scanner.nextLine();
		System.out.println("YOUR CHAT NAME IS " + name);
		
		Socket socket = serverSocket.accept();
		BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter outputBuffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		SendMessage(outputBuffer, "!SERVER CONNECTED...");
		String clientMessage, serverResponse;
		
		
		while(true) {
			clientMessage = inputBuffer.readLine();
			if(clientMessage.contains("quit")) {
				CloseEverything(serverSocket, socket, outputBuffer, inputBuffer, scanner);
				break;
			}
			
			System.out.println(clientMessage);
			System.out.print(name + "> ");
			serverResponse = scanner.nextLine();
			SendMessage(outputBuffer, name + "> " + serverResponse);
			
		}
		
		
	}
	
	public static void SendMessage(BufferedWriter outputBuffer, String message) throws IOException {
		outputBuffer.write(message);
		outputBuffer.newLine();
		outputBuffer.flush();
	}
	public static void CloseEverything(ServerSocket ss, Socket s, BufferedWriter outputBuffer, BufferedReader inputBuffer, Scanner scanner) throws IOException {
		SendMessage(outputBuffer, "quit");
		System.out.println("!SERVER CLOSING...");
		scanner.close();
		s.close();
		ss.close();
		outputBuffer.close();
		inputBuffer.close();
		
	}

}
