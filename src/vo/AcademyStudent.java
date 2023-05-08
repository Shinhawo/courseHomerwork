package vo;

public class AcademyStudent extends Person{

	private String deleted; 
	
	public AcademyStudent () {}

	
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


	@Override
	public String toString() {
		String person = super.toString();
		return "AcademyStudent ["+ person +"deleted=" + deleted + "]";
	}


}
