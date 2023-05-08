package service;

import java.util.List;
import java.util.Map;

import controller.LoginUser;
import dao.RegistrationsDao;
import dao.StudentDao;
import dao.CourseDao;
import dto.RegistrationDto;
import vo.AcademyCourse;
import vo.AcademyCourseRegistration;
import vo.AcademyStudent;

public class StudentService {

	private static StudentService instance = new StudentService();
	private StudentService() {}
	public static StudentService getInstance() {
		return instance;
	}
	
	
	private StudentDao studentDao = StudentDao.getInstance();
	private CourseDao courseDao = CourseDao.getInstance();
	private RegistrationsDao registrationsDao = RegistrationsDao.getInstance();
	
	
	
	public void registerStudent(AcademyStudent student) {
		
		AcademyStudent savedStudent = studentDao.getStudentById(student.getId());
		if(savedStudent != null) {
			throw new RuntimeException("["+student.getId()+"]는 이미 사용중인 아이디 입니다.");
		}
		
		AcademyStudent savedEmail = studentDao.getStudentByEmail(student.getEmail());
		if(savedEmail != null) {
			throw new RuntimeException("["+student.getEmail()+"]는 이미 사용중인 이메일 입니다.");
		}
		
		studentDao.insertStudent(student);
	}
	
	
	public LoginUser login(String id, String password, String type) {
		
		// 수강생 정보고 존재하는지 체크하기
		AcademyStudent savedStudent = studentDao.getStudentById(id);
		if(savedStudent == null) {
			throw new RuntimeException("가입되어 있지 않은 아이디 입니다.");
		}
		
		// 탈퇴한 수강생인지 체크하기
		if("Y".equals(savedStudent.getDeleted())) {
			throw new RuntimeException("삭제된 아이디입니다.");
		}
		
		// 비밀번호 사용자 정보 조회
		if(!savedStudent.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
		
		//
		return new LoginUser(savedStudent.getId(), savedStudent.getPassword(), savedStudent.getName(), type);
	}
	
	
	public List<Map<String, Object>> getAllCourses(){
		
		return courseDao.getCourses("모집중");

	}

	
	public void registerCourse(int courseNo, String studentId) {
		
//		List<AcademyCourseRegistration> savedRegistrations =
//				registrationsDao.getCourseReg(studentId);
//		if(savedRegistrations.isEmpty()) {
//			throw new RuntimeException("등록된 신청정보가 존재하지 않습니다.");
//		}
//		
//		AcademyCourseRegistration savedRegistrations =
//				registrationsDao.getCourseReg(courseNo,studentId);
		
//		if(savedRegistrations.getCourseNo() == courseNo && savedRegistrations.getStudentId() == studentId) {
//			throw new RuntimeException("신청한 강의를 다시 신청하실 수 없습니다.");
//		}
//		
		
		registrationsDao.insertCourseReg(courseNo, studentId);
		
		AcademyCourse course = courseDao.getCousersByNo(courseNo);
		int courseReqCnt = course.getCourseReqCnt() + 1;
		course.setCourseReqCnt(courseReqCnt);
		course.setStatus(course.getStatus());
		if(courseReqCnt == course.getCourseQuato()) {
			course.setStatus("모집완료");
		}

		courseDao.updateCourse(course);

	}

	
	public List<RegistrationDto> getAllMyRegistrations(String studentId){
		
		return registrationsDao.getCourseRegDto(studentId);
	}
	
	
	public void caceledCourse(int regNo, String studentId) {
		
		AcademyCourseRegistration registration = registrationsDao.getCourseReg(regNo, studentId);
		registration.setRegCanceled("Y");
		registrationsDao.updateRegistraion(registration);
		
		AcademyCourse course = new AcademyCourse();
		course.setCourseReqCnt(course.getCourseReqCnt() - 1);
		if(course.getStatus() == "모집완료") {
			course.setStatus("모집중");
		}
		courseDao.updateCourse(course);
	}
	
}
