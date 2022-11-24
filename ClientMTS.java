package mts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMTS {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner scanner = new Scanner(System.in);
		String serverMessage, clientMessage, name, host, port;
		System.out.println("ENTER HOSTNAME: ");
		host = scanner.nextLine();
		System.out.println("ENTER PORT: ");
		port = scanner.nextLine();
		
		System.out.println("ENTER YOUR NAME: ");
		name = scanner.nextLine();
		System.out.println("YOUR CHAT NAME IS " + name);
		Socket socket = new Socket(host, Integer.parseInt(port));
		
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		
		
		
		while(true) {
			serverMessage = inputStream.readLine();
			System.out.println(serverMessage);
			
			if(serverMessage.contains("quit")) {
				
				CloseEverything(socket, inputStream, outputStream, scanner);
				break;
			}
			System.out.print(name + "> ");
			clientMessage = scanner.nextLine();
			SendMessage(outputStream, name + "> " + clientMessage);
		}
		
	}
	
	public static void SendMessage(BufferedWriter outputBuffer, String message) throws IOException {
		outputBuffer.write(message);
		outputBuffer.newLine();
		outputBuffer.flush();
	}
	
	public static void CloseEverything(Socket s, BufferedReader inputStream, BufferedWriter outputStream, Scanner scanner) throws IOException {
		SendMessage(outputStream, "quit");
		System.out.println("!CLIENT CLOSING..");
		s.close();
		scanner.close();
		inputStream.close();
		outputStream.close();
	}

}
