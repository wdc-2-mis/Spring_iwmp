package app.bean;

import java.math.BigInteger;

public class NRSCWorksBean {

	private String st_name;
	private int st_code;
	private int projects;
	private int n_created;
	private int n_unstarted;
	private int n_ongoing;
	private int n_completed;
	private int n_forclosed;
	private int e_created;
	private int e_unstarted;
	private int e_ongoing;
	private int e_completed;
	private int e_forclosed;
	private int l_created;
	private int l_unstarted;
	private int l_ongoing;
	private int l_completed;
	private int l_forclosed;
	private int p_created;
	private int p_unstarted;
	private int p_ongoing;
	private int p_completed;
	private int p_forclosed;
	private int t_created;
	private int t_unstarted;
	private int t_ongoing;
	private int t_completed;
	private int t_forclosed;
	private String dist_name;
	private int dcode;
	private String proj_name;
	private int proj_id;
	
	private int createdwork;
	private int startedwork;
	private int ongoing;
	private int completed;
	private int forclosed;
	
	
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public int getSt_code() {
		return st_code;
	}
	public void setSt_code(int st_code) {
		this.st_code = st_code;
	}
	public int getProjects() {
		return projects;
	}
	public void setProjects(int projects) {
		this.projects = projects;
	}
	public int getN_created() {
		return n_created;
	}
	public void setN_created(int n_created) {
		this.n_created = n_created;
	}
	public int getN_unstarted() {
		return n_unstarted;
	}
	public void setN_unstarted(int n_unstarted) {
		this.n_unstarted = n_unstarted;
	}
	public int getN_ongoing() {
		return n_ongoing;
	}
	public void setN_ongoing(int n_ongoing) {
		this.n_ongoing = n_ongoing;
	}
	public int getN_completed() {
		return n_completed;
	}
	public void setN_completed(int n_completed) {
		this.n_completed = n_completed;
	}
	public int getN_forclosed() {
		return n_forclosed;
	}
	public void setN_forclosed(int n_forclosed) {
		this.n_forclosed = n_forclosed;
	}
	public int getE_created() {
		return e_created;
	}
	public void setE_created(int e_created) {
		this.e_created = e_created;
	}
	public int getE_unstarted() {
		return e_unstarted;
	}
	public void setE_unstarted(int e_unstarted) {
		this.e_unstarted = e_unstarted;
	}
	public int getE_ongoing() {
		return e_ongoing;
	}
	public void setE_ongoing(int e_ongoing) {
		this.e_ongoing = e_ongoing;
	}
	public int getE_completed() {
		return e_completed;
	}
	public void setE_completed(int e_completed) {
		this.e_completed = e_completed;
	}
	public int getE_forclosed() {
		return e_forclosed;
	}
	public void setE_forclosed(int e_forclosed) {
		this.e_forclosed = e_forclosed;
	}
	public int getL_created() {
		return l_created;
	}
	public void setL_created(int l_created) {
		this.l_created = l_created;
	}
	public int getL_unstarted() {
		return l_unstarted;
	}
	public void setL_unstarted(int l_unstarted) {
		this.l_unstarted = l_unstarted;
	}
	public int getL_ongoing() {
		return l_ongoing;
	}
	public void setL_ongoing(int l_ongoing) {
		this.l_ongoing = l_ongoing;
	}
	public int getL_completed() {
		return l_completed;
	}
	public void setL_completed(int l_completed) {
		this.l_completed = l_completed;
	}
	public int getL_forclosed() {
		return l_forclosed;
	}
	public void setL_forclosed(int l_forclosed) {
		this.l_forclosed = l_forclosed;
	}
	public int getP_created() {
		return p_created;
	}
	public void setP_created(int p_created) {
		this.p_created = p_created;
	}
	public int getP_unstarted() {
		return p_unstarted;
	}
	public void setP_unstarted(int p_unstarted) {
		this.p_unstarted = p_unstarted;
	}
	public int getP_ongoing() {
		return p_ongoing;
	}
	public void setP_ongoing(int p_ongoing) {
		this.p_ongoing = p_ongoing;
	}
	public int getP_completed() {
		return p_completed;
	}
	public void setP_completed(int p_completed) {
		this.p_completed = p_completed;
	}
	public int getP_forclosed() {
		return p_forclosed;
	}
	public void setP_forclosed(int p_forclosed) {
		this.p_forclosed = p_forclosed;
	}
	public String getDist_name() {
		return dist_name;
	}
	public int getT_created() {
		return t_created;
	}
	public void setT_created(int t_created) {
		this.t_created = t_created;
	}
	public int getT_unstarted() {
		return t_unstarted;
	}
	public void setT_unstarted(int t_unstarted) {
		this.t_unstarted = t_unstarted;
	}
	public int getT_ongoing() {
		return t_ongoing;
	}
	public void setT_ongoing(int t_ongoing) {
		this.t_ongoing = t_ongoing;
	}
	public int getT_completed() {
		return t_completed;
	}
	public void setT_completed(int t_completed) {
		this.t_completed = t_completed;
	}
	public int getT_forclosed() {
		return t_forclosed;
	}
	public void setT_forclosed(int t_forclosed) {
		this.t_forclosed = t_forclosed;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}
	public int getDcode() {
		return dcode;
	}
	public void setDcode(int dcode) {
		this.dcode = dcode;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public int getProj_id() {
		return proj_id;
	}
	public void setProj_id(int proj_id) {
		this.proj_id = proj_id;
	}
	public int getCreatedwork() {
		return createdwork;
	}
	public void setCreatedwork(int createdwork) {
		this.createdwork = createdwork;
	}
	public int getStartedwork() {
		return startedwork;
	}
	public void setStartedwork(int startedwork) {
		this.startedwork = startedwork;
	}
	public int getOngoing() {
		return ongoing;
	}
	public void setOngoing(int ongoing) {
		this.ongoing = ongoing;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	public int getForclosed() {
		return forclosed;
	}
	public void setForclosed(int forclosed) {
		this.forclosed = forclosed;
	}
	
	
	
}