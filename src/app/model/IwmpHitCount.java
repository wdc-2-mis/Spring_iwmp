package app.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * The persistent class for the iwmp_hit_count database table.
 * 
 */
@Entity
@Table(name="iwmp_hit_count")
@NamedQuery(name="IwmpHitCount.findAll", query="SELECT i FROM IwmpHitCount i")
public class IwmpHitCount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="count")
	private BigInteger count;

	@Column(name="inserteddate")
	private Timestamp inserteddate;
	
	@Column(name="session_id")
	private String sessionId;


	public IwmpHitCount() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getCount() {
		return this.count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}
	
	public Timestamp getInserteddate() {
		return this.inserteddate;
	}

	public void setInserteddate(Timestamp insertDate) {
		this.inserteddate = insertDate;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}