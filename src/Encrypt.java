import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Encrypt {

    private static final SecureRandom random = new SecureRandom();
    private static  MessageDigest md = null;


    private static byte[] generateSalt(){

        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashSha512(String userPassword){

        byte[] hashedPassword = null;
        byte[] salt = "Bimbi".getBytes();
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(userPassword.getBytes(StandardCharsets.UTF_8));
        }catch (NoSuchAlgorithmException nsaEx){
            System.out.print(nsaEx);
        }finally {
            if(hashedPassword != null) {
                return Arrays.toString(salt) + ":"+ Arrays.toString(hashedPassword);
            }
        }
        return "Hiba";
    }

}
