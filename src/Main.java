import java.util.Scanner;

public class Main {

    private static final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args){

        System.out.print("Enter password: ");
        String password = userInput.nextLine();
        System.out.println("SHA-512");
        System.out.println(Encrypt.hashSha512(password));
        System.out.println("PBKDF2");
        System.out.println(Encrypt.hashPBKDF2(password));
    }
}
