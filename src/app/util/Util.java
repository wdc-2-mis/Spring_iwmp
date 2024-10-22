package app.util;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {
	public static String generateCaptchaTextMethod1() 	 {
		
	   	Random rdm=new Random();
		int rl=rdm.nextInt(); // Random numbers are generated. 
		String hash1 = Integer.toHexString(rl); // Random numbers are converted to Hexa Decimal.
		
		return hash1;
		
}


public static String generateCaptchaTextMethod2(int captchaLength) 	 {
	
	 String saltChars = "abcdefghjkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
	 StringBuffer captchaStrBuffer = new StringBuffer();
	        java.util.Random rnd = new java.util.Random();
	        
	        // build a random captchaLength chars salt        
	        while (captchaStrBuffer.length() < captchaLength)
	        {
	            int index = (int) (rnd.nextFloat() * saltChars.length());
	            captchaStrBuffer.append(saltChars.substring(index, index+1));
	        }

	    return captchaStrBuffer.toString();
	    
}

public static String getCaptchaSession(HttpServletRequest request)
{
	String ch = null;
    try
    {
    	Random r = new Random();
  	  	String token = Long.toString(Math.abs(r.nextLong()), 36);
  	  	ch = token.substring(0,6);
  	  	HttpSession session = request.getSession(true);
  	  	session.setAttribute("cptchaString", ch);
  	  	//System.out.println("chchchch: - " + ch);
  	  	
    }
    catch (Exception e)
    {
        
    }
    return ch;
}

public static boolean getCSRFflag(String csrfCode, HttpSession session, HttpServletRequest request)
	{
		String CSRFcodeSes = (String)session.getAttribute("cptchaString");
		//logger.error("CSRFcodeSes :- "+CSRFcodeSes);
		if(csrfCode != null)
		{
			if(!csrfCode.equals(CSRFcodeSes) ){
	  			return false;
	  		}
	  		else
	  		{
	  			session.setAttribute("cptchaString",app.util.Util.getCaptchaSession(request).toString());
	  			return true;
	  		}
		}
		else
		{
  		return false;
		}  		
	}

public static String dateToString(Date date, String format){
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    if(date==null){
        date = new Date();
    }
    String stringAsDate = simpleDateFormat.format(date);
    return stringAsDate;    
}

}
