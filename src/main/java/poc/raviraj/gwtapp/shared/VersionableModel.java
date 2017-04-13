package poc.raviraj.gwtapp.shared;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class VersionableModel extends SoftDeleteableModel {

	private static final long serialVersionUID = 1L;
	
	public VersionableModel() {
	
	}
	
	@Version
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
