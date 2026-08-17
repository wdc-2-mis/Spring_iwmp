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
@Table(name = "iwmp_hit_count")
public class IwmpHitCount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "inserteddate", nullable = false)
    private Timestamp inserteddate;

    public IwmpHitCount() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Timestamp getInserteddate() {
        return inserteddate;
    }

    public void setInserteddate(Timestamp inserteddate) {
        this.inserteddate = inserteddate;
    }
}