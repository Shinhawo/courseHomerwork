package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtils;
import vo.AcademyStudent;

public class StudentDao {

	// 내 어플리케이션이 실행되는 동안 studentDao객체는 하나만 만들어진다 -> new 사용 못함
	// 값을 담는 객체가 아니라 기능이 구현된 객체. 메모리에 하나만 생성된다.
	// = 싱글턴(Singleton)객체
	private static StudentDao instance = new StudentDao();
	private StudentDao () {}
	public static StudentDao getInstance() {
		return instance;
	}
	
	public List<AcademyStudent> getStudentByCourseNo(int courseNo){
		
		String sql = "select S.student_id, S.student_name, S.student_phone, S.student_email "
				   + "from academy_students S, academy_course_registrations R, academy_courses C "
				   + "where S.student_id = R.student_id "
				   + "and R.course_no = C.course_no "
				   + "and C.course_No = ? ";
		
		try {
			List<AcademyStudent> students = new ArrayList<>();
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, courseNo);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				AcademyStudent student = new AcademyStudent();
				
				student.setId(rs.getString("student_id"));;
				student.setName(rs.getString("student_name"));
				student.setPhone(rs.getString("student_phone"));
				student.setEmail(rs.getString("student_email"));
				
				students.add(student);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
			return students;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public AcademyStudent getStudentById (String studentId) {
		
		String sql = "SELECT STUDENT_ID, STUDENT_PASSWORD, STUDENT_NAME, "
				   + "STUDENT_PHONE, STUDENT_EMAIL, STUDENT_DELETED, "
				   + "STUDENT_CREATE_DATE "
				   + "FROM ACADEMY_STUDENTS "
				   + "WHERE STUDENT_ID = ? ";
		
		try {
			AcademyStudent student = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentId);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				student = new AcademyStudent();
				student.setId(rs.getString("student_id"));
				student.setPassword(rs.getString("student_password"));
				student.setName(rs.getString("student_name"));
				student.setPhone(rs.getString("student_phone"));
				student.setEmail(rs.getString("student_email"));
				student.setDeleted(rs.getString("student_deleted"));
				student.setCreateDate(rs.getDate("student_create_date"));
				
			}
			rs.close();
			pstmt.close();
			conn.close();
 			
			return student;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
public AcademyStudent getStudentByEmail (String studentEmail) {
		
		String sql = "SELECT STUDENT_ID, STUDENT_PASSWORD, STUDENT_NAME, "
				   + "STUDENT_PHONE, STUDENT_EMAIL, STUDENT_DELETED, "
				   + "STUDENT_CREATE_DATE "
				   + "FROM ACADEMY_STUDENTS "
				   + "WHERE STUDENT_EMAIL = ? ";
		
		try {
			AcademyStudent student = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentEmail);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				student = new AcademyStudent();
				student.setId(rs.getString("student_id"));
				student.setPassword(rs.getString("student_password"));
				student.setName(rs.getString("student_name"));
				student.setPhone(rs.getString("student_phone"));
				student.setEmail(rs.getString("student_email"));
				student.setDeleted(rs.getString("student_deleted"));
				student.setCreateDate(rs.getDate("student_create_date"));
				
			}
			rs.close();
			pstmt.close();
			conn.close();
 			
			return student;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	public void insertStudent(AcademyStudent student) {
		
		String sql = "INSERT INTO ACADEMY_STUDENTS "
				   + "(STUDENT_ID, STUDENT_PASSWORD, STUDENT_NAME, STUDENT_PHONE, STUDENT_EMAIL) "
				   + "VALUES "
				   + "(?,?,?,?,?) ";
		
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, student.getId());
			pstmt.setString(2, student.getPassword());
			pstmt.setString(3, student.getName());
			pstmt.setString(4, student.getPhone());
			pstmt.setString(5, student.getEmail());
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
}
