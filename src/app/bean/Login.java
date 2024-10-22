package app.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class Login extends WebSecurityConfigurerAdapter{
		
	private String user_id;
	private String salt;
	private String encrypted_pass;
	
	private String encrypted_pass_second;
	
	public String getEncrypted_pass_second() {
		return encrypted_pass_second;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setEncrypted_pass_second(String encrypted_pass_second) {
		this.encrypted_pass_second = encrypted_pass_second;
	}

	
	private String captcha; 
	
	
	
	  public String getCaptcha() { return captcha; }
	  
	  public void setCaptcha(String captcha) { this.captcha = captcha; }
	 

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEncrypted_pass() {
		return encrypted_pass;
	}

	public void setEncrypted_pass(String encrypted_pass) {
		this.encrypted_pass = encrypted_pass;
	}

	
}