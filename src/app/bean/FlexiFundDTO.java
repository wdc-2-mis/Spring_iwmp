package app.bean;

import java.util.List;

public class FlexiFundDTO {
    private Integer actId;
    private String workDesc;
    private Double estCost;
    private Double cost;
    private List<PhotoDTO> photos;
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	public String getWorkDesc() {
		return workDesc;
	}
	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}
	public Double getEstCost() {
		return estCost;
	}
	public void setEstCost(Double estCost) {
		this.estCost = estCost;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public List<PhotoDTO> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoDTO> photos) {
		this.photos = photos;
	}


}
