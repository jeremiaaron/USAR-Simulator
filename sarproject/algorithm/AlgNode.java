package algorithm;

public class AlgNode implements Comparable<AlgNode> {
	
	private Integer id;
	private Integer dist;
	
	public AlgNode(Integer id, Integer dist) {
		super();
		this.id = id;
		this.dist = dist;
	}

	public Integer getId() {
		return id;
	}

	public Integer getDistance() {
		return dist;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDistance(Integer dist) {
		this.dist = dist;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dist == null) ? 0 : dist.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		
		if(obj == null)
			return false;
		
		if(getClass() != obj.getClass())
			return false;
		
		AlgNode other = (AlgNode) obj;
		
		if(dist == null) {
			
			if(dist != null)
				return false;
		}
		
		else if(!dist.equals(other.dist))
			return false;
		
		if(id == null) {
			if(other.id != null)
				return false;
		}
		else if(!id.equals(other.id))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "AlgNode [id=" + id + ", dist=" + dist + "]";
	}

	@Override
	public int compareTo(AlgNode o) {
		
		if(this.dist < o.dist)
			return -1;
		
		else if(this.dist > o.dist)
			return 1;
		
		else
			return this.getId().compareTo(o.getId());
	}
	
}
