package service;

import java.util.List;

import controller.LoginUser;
import dao.RegistrationsDao;
import dao.StudentDao;
import dao.TeacherDao;
import dao.courseDao;
import dto.CourseDetailDto;
import dto.RegistrationDto;
import vo.AcademyCourse;
import vo.AcademyCourseRegistration;
import vo.AcademyStudent;
import vo.AcademyTeacher;

public class TeacherService {

	private TeacherDao teacherDao = new TeacherDao();
	private courseDao courseDao = new courseDao();
	private RegistrationsDao registrationsDao = new RegistrationsDao();
	private StudentDao studentDao = new StudentDao();
	
	
	public void registerTeacher(AcademyTeacher teacher) {
		
		AcademyTeacher savedTeacher = teacherDao.getTeacherById(teacher.getId());
		
		if(savedTeacher != null) {
			System.out.println("["+savedTeacher.getId()+"]는 이미 사용중인 아이디 입니다.");
		}
		
		teacherDao.insertTeacher(teacher);
	}
	
	
	public LoginUser login(String id, String password, String type) {
		
		AcademyTeacher savedTeacher = teacherDao.getTeacherById(id);
		if(savedTeacher == null) {
			throw new RuntimeException("가입되어 있지 않은 아이디 입니다.");
		}
		
		if(!savedTeacher.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
		
		return new LoginUser(savedTeacher.getId(), savedTeacher.getPassword(), savedTeacher.getName(), type);
	}
	
	
	public void registerCourse(AcademyCourse course) {
		
		courseDao.insertCourse(course);
	}
	
	
	public List<AcademyCourse> getAllmyCourse (String teacherId) {
		
		List<AcademyCourse> courses = courseDao.getCourseById(teacherId);
		if(courses.isEmpty()) {
			throw new RuntimeException("개설된 과정 정보가 존재하지 않습니다.");
		}
		
		return courses;
	} 
	
	
	public void courseCancele(int courseNo) {
		
		AcademyCourse course = courseDao.getCourseByNo(courseNo);
		if(course == null) {
			throw new RuntimeException("["+courseNo+"]번은 존재하지 않는 과정번호입니다.");
		}
		
		course.setStatus("과정취소");
		courseDao.updateCourse(course);
		
		
		List<AcademyCourseRegistration> registrations = 
				registrationsDao.getCourseRegByCourseNo(courseNo);
		for(AcademyCourseRegistration registration : registrations) {
		
			if(registration.getCourseNo() == courseNo) {
				registration.setRegCanceled("Y");
			}
			
			registrationsDao.updateRegistraion(registration);
		}
	}
	
	public CourseDetailDto getCourseDetail(int courseNo, String teacherId) {
		
		AcademyCourse course = courseDao.getCourseByNo(courseNo);
		
		if(course == null) {
			throw new RuntimeException("존재하지 않는 과정입니다.");
		}
		
		if(course.getTeacherId() != teacherId) {
			System.out.println("다른 강사의 개설과정은 조회할 수 없습니다.");
		}
		
		List<AcademyStudent> students = 
				studentDao.getStudentByCourseNo(courseNo);
		
		CourseDetailDto dto = new CourseDetailDto();
		dto.setAcademyCourse(course);
		dto.setStudents(students);
		
		return dto;
	}
	
}
