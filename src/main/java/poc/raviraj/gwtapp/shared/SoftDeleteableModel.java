package poc.raviraj.gwtapp.shared;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SoftDeleteableModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public SoftDeleteableModel(){
		
	}
	
	private short deleteFlag;

	public short getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
