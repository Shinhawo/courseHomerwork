package controller;

import java.util.List;

import dto.CourseDetailDto;
import dto.RegistrationDto;
import dto.courseDto;
import service.StudentService;
import service.TeacherService;
import util.KeyboardReader;
import vo.AcademyCourse;
import vo.AcademyStudent;
import vo.AcademyTeacher;

public class CourseController {

	private KeyboardReader keyboard = new KeyboardReader();
	private LoginUser loginUser;
	private StudentService studentService = new StudentService();
	private TeacherService teacherService = new TeacherService();
	
	public void menu() {
		
		try {
			if (loginUser == null) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("1.로그인(학생)  2.로그인(강사)  3.가입(학생)  4.가입(강사)  0.종료");
				System.out.println("-------------------------------------------------------------");
			} else {
				if ("학생".equals(loginUser.getType())) {
					System.out.println("-------------------------------------------------------------");				
					System.out.println("1.과정조회  2.과정신청  3.등록취소  4.신청현황  5.로그아웃 0.종료");
					System.out.println("-------------------------------------------------------------");				
				} else if ("강사".equals(loginUser.getType())) {
					System.out.println("-------------------------------------------------------------");				
					System.out.println("1.과정조회  2.과정등록  3.과정취소  4.과정현황  5.로그아웃 0.종료");					
					System.out.println("-------------------------------------------------------------");				
				}
			}
			System.out.println();
			System.out.print("### 메뉴번호: ");
			int menu = keyboard.readInt();
			System.out.println();
			
			if (menu == 0) {
				System.out.println("<< 프로그램 종료 >>");
				System.out.println("### 프로그램을 종료합니다.");
				System.exit(0);
			}
			
			if (loginUser == null) {
				if (menu == 1) {
					학생로그인();
				} else if (menu == 2) {
					강사로그인();
				} else if (menu == 3) {
					학생회원가입();
				} else if (menu == 4) {
					강사회원가입();
				} 
			} else {
				if ("학생".equals(loginUser.getType())) {
					if (menu == 1) {
						학생과정조회();
					} else if (menu == 2) {
						학생과정신청();
					} else if (menu == 3) {
						학생등록취소();
					} else if (menu == 4) {
						학생신청현황조회();
					} else if (menu == 5) {
						로그아웃();
					}
				
				} else if ("강사".equals(loginUser.getType())) {
					if (menu == 1) {
						강사과정조회();
					} else if (menu == 2) {
						강사과정등록();
					} else if (menu == 3) {
						강사과정취소();
					} else if (menu == 4) {
						강사과정현황조회();
					} else if (menu == 5) {
						로그아웃();
					}
				}
				
			}
			
			
			
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		menu();
	}
	
	public void 로그아웃() {
		System.out.println("<< 로그아웃 >>");
		
		loginUser = null;
		System.out.println("### 로그아웃 되었습니다.");
	}
	
	public void 학생로그인() {
		System.out.println("<< 학생로그인 >>");
		System.out.println("### 아이디와 비밀번호를 입력하세요.");
		System.out.println("### 아이디 : ");
		String id = keyboard.readString();
		System.out.println("### 비밀번호 : ");
		String password = keyboard.readString();
		String type = "학생";
		
		loginUser = studentService.login(id,password,type);
		System.out.println("### 로그인이 완료되었습니다.");
	}
	
	public void 강사로그인() {
		System.out.println("<< 강사로그인 >>");
		System.out.println("### 아이디와 비밀번호를 입력하세요.");
		System.out.println("### 아이디 : ");
		String id = keyboard.readString();
		System.out.println("### 비밀번호 : ");
		String password = keyboard.readString();
		String type = "강사";
		
		loginUser = teacherService.login(id, password, type);
		System.out.println("### 로그인이 완료되었습니다.");
	}
	
	public void 학생회원가입() {
		System.out.println("<< 학생회원가입 >>");
		System.out.println("### 이름과 아이디, 비밀번호, 전화번호, 이메일을 입력하세요.");
		System.out.println("### 이름 : ");
		String name = keyboard.readString();
		System.out.println("### 아이디 : ");
		String id = keyboard.readString();
		System.out.println("### 비밀번호 : ");
		String password = keyboard.readString();
		System.out.println("### 전화번호 : ");
		String phone = keyboard.readString();
		System.out.println("### 이메일 : ");
		String email = keyboard.readString();
		
		AcademyStudent student = new AcademyStudent();
		student.setName(name);
		student.setId(id);
		student.setPassword(password);
		student.setPhone(phone);
		student.setEmail(email);
		
		studentService.registerStudent(student);
		
		System.out.println("### 학생 회원가입이 완료되었습니다.");
	}
	
	public void 강사회원가입() {
		System.out.println("<< 강사회원가입 >>");
		System.out.println("### 이름과 아이디, 비밀번호, 전화번호, 이메일, 급여를 입력하세요.");
		System.out.println("### 이름 : ");
		String name = keyboard.readString();
		System.out.println("### 아이디 : ");
		String id = keyboard.readString();
		System.out.println("### 비밀번호 : ");
		String password = keyboard.readString();
		System.out.println("### 전화번호 : ");
		String phone = keyboard.readString();
		System.out.println("### 이메일 : ");
		String email = keyboard.readString();
		System.out.println("### 급여 : ");
		int salary = keyboard.readInt();
		
		AcademyTeacher teacher = new AcademyTeacher();
		teacher.setId(id);
		teacher.setPassword(password);
		teacher.setName(name);
		teacher.setPhone(phone);
		teacher.setEmail(email);
		teacher.setSalary(salary);
		
		teacherService.registerTeacher(teacher);
		
		System.out.println("### 강사 회원가입이 완료되었습니다.");
	}
	
	public void 학생과정조회() {
		System.out.println("<< 과정조회 >>");
		System.out.println("### 모집중인 개설과정을 조회합니다.");
		
		List<courseDto> dtos = studentService.getAllCourses();
		
		System.out.println("----------------------------------------------------");
		System.out.println("과정번호\t모집정원\t신청자수\t담당강사명\t과정명");
		System.out.println("----------------------------------------------------");
		
		for(courseDto dto : dtos) {
			System.out.print(dto.getCourseNo() + "\t");
			System.out.print(dto.getCourseQuato() + "\t");
			System.out.print(dto.getCourseReqCnt() + "\t");
			System.out.print(dto.getTeacher_name() + "\t");
			System.out.println(dto.getCourse_name() );			
		}
		
		System.out.println("----------------------------------------------------");
		
	}
	
	public void 학생과정신청() {
		System.out.println("<< 과정신청 >>");
		System.out.println("### 과정번호를 입력하여 과정신청을 하세요.");
		System.out.println("### 과정번호 : ");
		int courseNo = keyboard.readInt();
		String studentId = loginUser.getId();
		
		studentService.registerCourse(courseNo, studentId);
		System.out.println("### 과정신청이 완료되었습니다.");
	}
	
	public void 학생등록취소() {
		System.out.println("<< 등록취소 >>");
		System.out.println("과정등록번호를 입력하여 등록을 취소하세요.");
		System.out.println("과정등록번호 : ");
		int regNo = keyboard.readInt();
		
		studentService.caceledCourse(regNo, loginUser.getId());
		System.out.println("### 신청이 취소되었습니다.");
	}
	
	public void 학생신청현황조회() {
		System.out.println("<< 신청현황조회>>");
		
		List<RegistrationDto> dtos = 
				studentService.getAllMyRegistrations(loginUser.getId());
		
		System.out.println("----------------------------------------------------");
		System.out.println("등록번호\t등록일자\t\t취소여부\t과정명");
		System.out.println("----------------------------------------------------");
		
		for(RegistrationDto dto : dtos) {
				System.out.print(dto.getRegNo() + "\t");
				System.out.print(dto.getCreateDate() + "\t");
				System.out.print(dto.getRegCanceled() + "\t");
				System.out.println(dto.getCourseName());
		}
		
		System.out.println("----------------------------------------------------");
	}
	
    public void 강사과정조회() {
		System.out.println("<< 과정조회 >>");
		System.out.println("### 개설한 과정을 조회하세요.");
		
		List<AcademyCourse> courses =
				teacherService.getAllmyCourse(loginUser.getId());
		
		System.out.println("----------------------------------------------------");
		System.out.println("과정번호\t모집정원\t신청자수\t과정상태\t과정명");
		System.out.println("----------------------------------------------------");
		
		for(AcademyCourse course : courses) {
			System.out.print(course.getCourseNo() + "\t");
			System.out.print(course.getCourseQuato() + "\t");
			System.out.print(course.getCourseReqCnt() + "\t");
			System.out.print(course.getStatus() + "\t");
			System.out.println(course.getName() );
		}
		System.out.println("----------------------------------------------------");
    
    }
    
	public void 강사과정등록() {
	
		System.out.println("<< 과정등록 >>");
		System.out.println("### 과정명과 모집정원을 입력하세요.");
		System.out.println("### 과정명 : ");
		String courseName = keyboard.readString();
		System.out.println("### 모집정원 :");
		int courseQuato = keyboard.readInt();
		String teacherId = loginUser.getId();
		
		AcademyCourse course = new AcademyCourse();
		course.setName(courseName);
		course.setCourseQuato(courseQuato);
		course.setTeacherId(teacherId);
		teacherService.registerCourse(course);
		
		System.out.println("### 과정 등록이 완료되었습니다.");
		
	}
	
	public void 강사과정취소() {
		System.out.println("<< 과정취소 >>");
		System.out.println("### 취소할 과정의 번호를 입력하세요.");
		int courseNo = keyboard.readInt();
		
		teacherService.courseCancele(courseNo);
		System.out.println("### 과정이 취소되었습니다.");
		
	}
	
	public void 강사과정현황조회() {
		System.out.println("<< 과정현황조회 >>");
		System.out.println("### 과정번호를 입력하세요.");
		int courseNo = keyboard.readInt();
		
		CourseDetailDto dto = teacherService.getCourseDetail(courseNo, loginUser.getId());
		
		AcademyCourse course = dto.getAcademyCourse();
		List<AcademyStudent> students = dto.getStudents();

		System.out.println("### ["+courseNo+"]번 과정의 현황 ");
		System.out.println("----------------------------------------------");
		System.out.println("과정번호 : "+course.getCourseNo());
		System.out.println("과정이름 : "+course.getName());
		System.out.println("모집정원 : "+course.getCourseQuato());
		System.out.println("현재신청자 수 : "+course.getCourseReqCnt());
		System.out.println("과정상태 : "+course.getStatus());
		System.out.println("등록일자 : "+course.getCourseCreateDate());
		System.out.println("----------------------------------------------");
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("학생아이디\t\t학생이름\t전화번호\t\t이메일");
		System.out.println("------------------------------------------------------------------");
	
		for(AcademyStudent student : students) {
			System.out.print(student.getId() + "\t");
			System.out.print(student.getName() + "\t");
			System.out.print(student.getPhone() + "\t");
			System.out.println(student.getEmail());
		}
		System.out.println("----------------------------------------------------");
	}
	
	public static void main(String[] args) {
		CourseController controller = new CourseController();
		controller.menu();
	}
}
