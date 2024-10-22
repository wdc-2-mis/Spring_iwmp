package app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StateDistrictCode implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	 private long stCode;
     private long distCode;

    public StateDistrictCode(){}
    public StateDistrictCode(long stCode, long distCode) {
       this.stCode = stCode;
       this.distCode = distCode;
    }
    @Column(name="st_code", nullable=false, precision=10, scale=0)
    public long getStCode() {
        return this.stCode;
    }
    public void setStCode(long stCode) {
        this.stCode = stCode;
    }
    @Column(name="dist_code", nullable=false, precision=10, scale=0)
    public long getDistCode() {
        return this.distCode;
    }
    public void setDistCode(long distCode) {
        this.distCode = distCode;
    }

   public boolean equals(Object other) 
   {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof StateDistrictCode) ) return false;
		 StateDistrictCode castOther = ( StateDistrictCode ) other; 
         
		 return (this.getStCode()==castOther.getStCode())
 && (this.getDistCode()==castOther.getDistCode());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + (int) this.getStCode();
         result = 37 * result + (int) this.getDistCode();
         return result;
   }   
}