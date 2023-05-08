package service;

import java.util.List;
import java.util.Map;

import controller.LoginUser;
import dao.RegistrationsDao;
import dao.StudentDao;
import dao.CourseDao;
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
		
		// 과정정보 존재여부 체크하기
		AcademyCourse course = courseDao.getCouserByNo(courseNo);
		if(course == null) {
			throw new RuntimeException("과정정보가 존재하지 않습니다.");
		}
		
		// 과정상태 체크 (모집종료된 강의를 신청하면 어떡하냐고~~~!!)
		if(!"모집중".equals(course.getStatus())) {
			throw new RuntimeException("현재 모집중인 과정이 아닙니다.");
		}
		
		// 중복신청여부 체크하기
		AcademyCourseRegistration reg 
							= registrationsDao.getCourseReg(courseNo, studentId);
		if(reg != null) {
			throw new RuntimeException("이미 수강신청(취소)된 과정입니다.");
		}
		
		// 수강신청정보 저장
		registrationsDao.insertCourseReg(courseNo, studentId);
				
		//개설과정정보 업데이트 (신청자수, 과정상태)
		int courseReqCnt = course.getCourseReqCnt() + 1;
		course.setCourseReqCnt(courseReqCnt);
		course.setStatus(course.getStatus());
		if(courseReqCnt == course.getCourseQuato()) {
			course.setStatus("모집완료");
		}
		
		courseDao.updateCourse(course);
	}

	
	public List<Map<String, Object>> getAllMyRegistrations(String studentId){
		
		return registrationsDao.getCourseReg(studentId);
	}
	
	
	public void caceledCourse(int regNo, String studentId) {
		
		// 등록정보 조회, 존재여부 체크
		AcademyCourseRegistration registration = registrationsDao.getRegistrationByRegNo(regNo);
		if(registration == null) {
			throw new RuntimeException("등록정보가 존재하지 않습니다.");
		}
		
		// 이미 수강취소된 과정인지 체크
		if("Y".equals(registration.getRegCanceled())) {
			throw new RuntimeException("이미 수강취소처리된 과정입니다.");
		}

		// 등록정보가 로그인한 학생의 등록정보인지 체크
		if(!studentId.equals(registration.getStudentId())) {
			throw new RuntimeException("본인이 신청한 과정만 수강취소할 수 있습니다.");
		}
		
		// 등록정보의 취소여부를 'Y'로 변경
		registration.setRegCanceled("Y");
		registrationsDao.updateRegistraion(registration);
		
		// 개설과정의 신청자수와 상태변경
		AcademyCourse course = courseDao.getCouserByNo(registration.getCourseNo());
		course.setCourseReqCnt(course.getCourseReqCnt() - 1);
		if("모집완료".equals(course.getStatus())) {
			course.setStatus("모집중");
		}
		courseDao.updateCourse(course);
	}
	
}
