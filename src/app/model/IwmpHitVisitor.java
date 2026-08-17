package app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "iwmp_hit_visitor",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_hit_visitor_session_date",
            columnNames = {"session_id", "visited_date"}
        )
    }
)
public class IwmpHitVisitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "iwmp_hit_visitor_seq"
    )
    @SequenceGenerator(
        name = "iwmp_hit_visitor_seq",
        sequenceName = "iwmp_hit_visitor_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "hit_count_id", nullable = false)
    private Integer hitCountId;

    @Column(name = "session_id", nullable = false, length = 255)
    private String sessionId;

    @Column(name = "visited_date", nullable = false)
    private java.sql.Date visitedDate;

    public IwmpHitVisitor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHitCountId() {
        return hitCountId;
    }

    public void setHitCountId(Integer hitCountId) {
        this.hitCountId = hitCountId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public java.sql.Date getVisitedDate() {
        return visitedDate;
    }

    public void setVisitedDate(java.sql.Date visitedDate) {
        this.visitedDate = visitedDate;
    }
}
