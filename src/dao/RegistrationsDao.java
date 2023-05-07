package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RegistrationDto;
import util.ConnUtils;
import vo.AcademyCourseRegistration;

public class RegistrationsDao {
	
	
	public List<RegistrationDto> getCourseRegDto(String studentId) {
		
		String sql = "select R.reg_no, R.reg_create_date, R.reg_canceled, "
				   + "C.course_name "
				   + "from academy_course_registrations R, academy_courses C "
				   + "where R.student_id = ? "
				   + "and R.course_no = C.course_no ";
		
		try {
			List<RegistrationDto> registrations = new ArrayList<>();
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, studentId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RegistrationDto dto = new RegistrationDto();
				dto.setRegNo(rs.getInt("reg_no"));
				dto.setCreateDate(rs.getDate("reg_create_date"));
				dto.setRegCanceled(rs.getString("reg_canceled"));
				dto.setCourseName(rs.getString("course_name"));
				
				registrations.add(dto);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
				return registrations;
				
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
		
	
//	public List<AcademyCourseRegistration> getCourseReg(String studentId) {
//		
//		String sql = "select * "
//				   + "from academy_course_registrations "
//				   + "where student_id = ? ";
//		
//		try {
//			List<AcademyCourseRegistration> registrations = new ArrayList<>();
//			Connection con = ConnUtils.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, studentId);
//			
//			ResultSet rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				AcademyCourseRegistration registration = new AcademyCourseRegistration();
//				registration.setRegNo(rs.getInt("reg_no"));
//				registration.setStudentId(rs.getString("student_id"));
//				registration.setCourseNo(rs.getInt("course_no"));
//				registration.setRegCanceled(rs.getString("reg_canceled"));
//				registration.setCreateDate(rs.getDate("reg_create_date"));
//				
//				registrations.add(registration);
//			}
//			
//			rs.close();
//			pstmt.close();
//			con.close();
//			
//			return registrations;
//		} catch (SQLException ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//	

	public AcademyCourseRegistration getCourseReg(int regNo, String studentId) {
		
		String sql = "select * "
				   + "from academy_course_registrations "
				   + "where student_id = ? "
				   + "and reg_No = ? ";
		
		try {
			AcademyCourseRegistration registration = null;
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, studentId);
			pstmt.setInt(2, regNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				registration = new AcademyCourseRegistration();
				registration.setRegNo(rs.getInt("reg_no"));
				registration.setStudentId(rs.getString("student_id"));
				registration.setCourseNo(rs.getInt("course_no"));
				registration.setRegCanceled(rs.getString("reg_canceled"));
				registration.setCreateDate(rs.getDate("reg_create_date"));
			
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
			return registration;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void insertCourseReg(int courseNo, String studentId) {
		
		String sql = "insert into academy_course_registrations "
				   + "(reg_no, student_id, course_no) "
				   + "values "
				   + "(reg_seq.nextval, ?, ?)";
		
		try {
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, studentId);
			pstmt.setInt(2, courseNo);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	public void updateRegistraion(AcademyCourseRegistration registration) {
		
		String sql = "update academy_course_registrations "
				   + "set "
				   + "	reg_canceled = ? "
				   + "where reg_no = ? "
				   + "and student_id = ?";
		
		try {
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, registration.getRegCanceled());
			pstmt.setInt(2, registration.getRegNo());
			pstmt.setString(3, registration.getStudentId());

			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	
	public List<AcademyCourseRegistration> getCourseRegByCourseNo(int courseNo){
		String sql = "select * "
				   + "from academy_course_registrations "
				   + "where course_no = ? ";
		
		try {
			List<AcademyCourseRegistration> registrations = new ArrayList<>();
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,courseNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AcademyCourseRegistration registration = new AcademyCourseRegistration();
				registration.setRegNo(rs.getInt("reg_no"));
				registration.setStudentId(rs.getString("student_id"));
				registration.setCourseNo(rs.getInt("course_no"));
				registration.setRegCanceled(rs.getString("reg_canceled"));
				registration.setCreateDate(rs.getDate("reg_create_date"));
			
				registrations.add(registration);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
			return registrations;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
