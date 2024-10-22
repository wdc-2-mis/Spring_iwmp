package app.util;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 {
	public  String stringify(String input) 
    { 
		 try {
	            MessageDigest md = MessageDigest.getInstance("SHA-512");
	            byte[] hashedBytes = md.digest(input.getBytes());
	            StringBuilder sb = new StringBuilder();
	            for (byte b : hashedBytes) {
	                sb.append(String.format("%02x", b));
	            }
	            return sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
  
        
    } 
}
