package app.util;

 public class RandomStringGenerator {  
     
     public static final int MIN_LENGTH = 8;  
    
     protected static java.util.Random r = new java.util.Random();  
    
      
     protected static char[] goodChar = {  
         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',  
         'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',  
         'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N',  
         'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',  
         '2', '3', '4', '5', '6', '7', '8', '9',  
         '@', '_', '#', '$' 
     };  
    
     /* Generate a Password object with a random password. */  
     public static String getNext() {  
         return getNext(MIN_LENGTH);  
     }  
    
     /* Generate a Password object with a random password. */  
     public static String getNext(int length) {  
         if (length < 1) {  
             throw new IllegalArgumentException(  
                     "Ridiculous password length " + length);  
         }  
         StringBuffer sb = new StringBuffer();  
         for (int i = 0; i < length; i++) {  
             sb.append(goodChar[r.nextInt(goodChar.length)]);  
         }  
         return sb.toString();  
     }  
     /*public static void main(String[] args) {
        System.out.println(getNext());
        System.out.println(getNext());
        System.out.println(getNext());
        System.out.println(getNext());
        System.out.println(getNext());
        System.out.println(getNext());
        System.out.println(getNext());
    }*/
 }
