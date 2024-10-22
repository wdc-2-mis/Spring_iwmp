package app.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionUtility {
	
	public static final Map<String, HttpSession> sessionMap=new HashMap<String, HttpSession>();
		
	public static boolean addSession(String userId,HttpSession session){
		boolean flag=false;
		HttpSession oldSession=null;
		try{
			oldSession=sessionMap.put(userId, session);
			if(oldSession !=null)
				oldSession.invalidate();
			flag=true;
			/*System.out.println(Calendar.getInstance().getTime());
			//before remove display hash map
			System.out.println("----------------------------------Map Start-----------------------------------------------------");
			for (Map.Entry<String, HttpSession> entry : sessionMap.entrySet()) { System.out.println("User Id = " + entry.getKey() + ", Session = " + entry.getValue()); }
			System.out.println("----------------------------------Map End-----------------------------------------------------");
		*/
			String loginid=null;
			for(Iterator<Map.Entry<String, HttpSession>> it=sessionMap.entrySet().iterator();it.hasNext();){
				loginid=null;
				Map.Entry<String, HttpSession> entry=(Map.Entry<String, HttpSession>)it.next();
					try{
						loginid = (String)entry.getValue().getAttribute("loginID");
					}catch (IllegalStateException e) {}
					if(loginid==null){
						sessionMap.remove((String)entry.getKey());
					}
			}
			//after remove display hash map
			/*System.out.println("----------------------------------Map Start-----------------------------------------------------");
			for (Map.Entry<String, HttpSession> entry : sessionMap.entrySet()) { System.out.println("User Id = " + entry.getKey() + ", Session = " + entry.getValue()); }
			System.out.println("----------------------------------Map End-----------------------------------------------------");
			*/
		}catch (Exception e) {
			//Logger.getRootLogger().error(e);
			// TODO: handle exception
		}
		return flag;
	}
	
	public static boolean removeSession(String userId){
		boolean flag=false;
		HttpSession session=null;
		try{
		session=sessionMap.get(userId);
		if(session !=null)
			try{
				session.invalidate();
			}catch (IllegalStateException e) {}
			
		flag=true;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		sessionMap.remove(userId);
		return flag;
	}
	public static boolean isAlreadyLoggedIn(String userId,HttpSession newSession){
		boolean flag=false;
		//boolean checkUser=false;
		HttpSession session=null;
		String loginid=null;
		flag=sessionMap.containsKey(userId);
		try{
			loginid = (String)newSession.getAttribute("loginID");
		}
		catch (IllegalStateException e) 
		{}
		if(loginid!=null)
		{
			flag=true;
			return flag;
		}
		
		if(flag)
		{
			session=sessionMap.get(userId);
			try{
				loginid = (String)session.getAttribute("loginID");
			}
			catch (IllegalStateException e) 
			{}
	        if(loginid == null) 
	        {
	        	sessionMap.remove(userId);
	        	flag=false;
	        }
		}
		return flag;
	}
	public static String  isAlreadyLoggedInOtherTab(String userId,HttpSession newSession){
		String message="no";
		String loginid=null;
		try{
			loginid = (String)newSession.getAttribute("loginID");
			String loginid1 = (String)newSession.getAttribute("loginid");
		}catch (IllegalStateException e) {}
		if(loginid!=null){
			if(userId.equalsIgnoreCase(loginid)){
				message="You are already signed in with the same User Id. Kindly logout first.";
			}else{
				message="You are already signed in with the other User Id. Kindly logout first.";
			}
			return message;
		}
		
		return message;
	}
	public static String  isAlreadyLoggedInOtherWindow(String userId,HttpSession newSession){
		String message="no";
		String loginid=null;
		
		boolean flag=false;
		HttpSession session=null;
		flag=sessionMap.containsKey(userId);
		if(flag){
			session=sessionMap.get(userId);
			try{
				loginid = (String)session.getAttribute("loginID");
			}catch (IllegalStateException e) {}
			if(loginid!=null){
				if(userId.equalsIgnoreCase(loginid)){
					message="You are already signed in with the same User Id. Kindly logout first";
				}else{
					message="You are already signed in with the other User Id. Kindly logout first";
				}
				return message;
			}
		}
		return message;
	}
}