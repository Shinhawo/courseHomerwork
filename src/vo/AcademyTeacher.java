package vo;

public class AcademyTeacher extends Person{

	private int salary;
	private String retired;
	
	public AcademyTeacher () {}
	
	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getRetired() {
		return retired;
	}

	public void setRetired(String retired) {
		this.retired = retired;
	}

	@Override
	public String toString() {
		String person = super.toString();
		return "AcademyTeacher ["+ person +"salary=" + salary + ", retired=" + retired + "]";
	}

	
}
