import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class Encrypt {

    private static final SecureRandom random = new SecureRandom();
    private static  MessageDigest md = null;

    //Generate the salt
    private static byte[] generateSalt(){

        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    //Generate SHA-512 hash
    public static String hashSha512(String userPassword){

        byte[] hashedPassword = null;
        byte[] salt = generateSalt();
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

    //Generate PBKDF2 hash
    protected final static int ITERATION_COUNT = 10000;

    public static String hashPBKDF2(String userPassword){

        byte[] hash = null;
        byte[] salt = generateSalt();

        KeySpec spec = new PBEKeySpec(userPassword.toCharArray(),salt,ITERATION_COUNT,128);
        SecretKeyFactory keyFactory = null;

        try{
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        }catch (NoSuchAlgorithmException nsaEx){
            System.out.println("There is a NoSuchAlgorithmException at hashPBKDF2:\n"+nsaEx);
        }

        if(keyFactory!= null) {
            try {
                hash = keyFactory.generateSecret(spec).getEncoded();
            } catch (InvalidKeySpecException iksEx) {
                System.out.println("There is a InvalidKeySpecException at hashPBKDF2:\n"+iksEx);
            }
        }

        if(hash != null)
            return Arrays.toString(salt) + ":" + Arrays.toString(hash);
        else
            return "Hiba";
    }

}
