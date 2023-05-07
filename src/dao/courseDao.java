package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import dto.courseDto;
import util.ConnUtils;
import vo.AcademyCourse;

public class courseDao {

	
	
	
	public void updateCourse(AcademyCourse course) {
		
		String sql = "update academy_courses "
				   + "set "
				   + "	course_req_cnt = ?, "
				   + "	course_status = ? "
				   + "where course_no = ?";
		
		try {
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, course.getCourseReqCnt());
			pstmt.setString(2, course.getStatus());
			pstmt.setInt(3, course.getCourseNo());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	
	public List<courseDto> getCourses () {
		
		String sql = "select C.course_no, C.course_quato, C.course_req_cnt, "
				   + "T.teacher_name, C.course_name "
				   + "from academy_courses C, academy_teacher T "
				   + "where C.course_status = '모집중' "
				   + "and C.teacher_id = T.teacher_id "
				   + "order by course_no ";
		
		try {
			List<courseDto> dtos = new ArrayList<>();
			
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				courseDto dto = new courseDto();
				
				dto.setCourseNo(rs.getInt("course_no"));
				dto.setCourseQuato(rs.getInt("course_quato"));
				dto.setCourseReqCnt(rs.getInt("course_req_cnt"));
				dto.setTeacher_name(rs.getString("teacher_name"));
				dto.setCourse_name(rs.getString("course_name"));
				
				dtos.add(dto);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
			return dtos;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	public AcademyCourse getCousersByNo(int courseNo) {
		
		String sql = "select * "
				   + "from academy_courses "
				   + "where course_no = ? ";
		
		try {
			AcademyCourse course = null;
			
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, courseNo);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				course = new AcademyCourse();
				
				course.setCourseNo(rs.getInt("course_no"));
				course.setName(rs.getString("course_name"));
				course.setCourseQuato(rs.getInt("course_quato"));
				course.setCourseReqCnt(rs.getInt("course_req_cnt"));
				course.setStatus(rs.getString("course_status"));
				course.setCourseCreateDate(rs.getDate("course_create_date"));
				course.setTeacherId(rs.getString("teacher_id"));
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
			return course;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	public void insertCourse(AcademyCourse course) {
		
		String sql = "insert into academy_courses "
				   + "(course_no, course_name, course_quato, teacher_id) "
				   + "values "
				   + "(courses_seq.nextval, ?, ?, ?)";
		
		try {
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course.getName());
			pstmt.setInt(2, course.getCourseQuato());
			pstmt.setString(3, course.getTeacherId());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public List<AcademyCourse> getCourseById(String teacherId) {
		
		String sql = "select * "
				   + "from academy_courses "
				   + "where teacher_id = ? ";
				   
		try {
			List<AcademyCourse> courses = new ArrayList<>();
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, teacherId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AcademyCourse course = new AcademyCourse();
				
				course.setCourseNo(rs.getInt("course_no"));
				course.setName(rs.getString("course_name"));
				course.setCourseQuato(rs.getInt("course_quato"));
				course.setCourseReqCnt(rs.getInt("course_req_cnt"));
				course.setStatus(rs.getString("course_status"));
				course.setCourseCreateDate(rs.getDate("course_create_date"));
				course.setTeacherId(rs.getString("teacher_id"));
				
				courses.add(course);
			}
			rs.close();
			pstmt.close();
			con.close();
			
			return courses;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
				   
	}
	
	public AcademyCourse getCourseByNo(int courseNo) {
		
		String sql = "select * "
				   + "from academy_courses "
				   + "where course_no = ? ";
				   
		try {
			AcademyCourse course = null;
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, courseNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				course = new AcademyCourse();
				
				course.setCourseNo(rs.getInt("course_no"));
				course.setName(rs.getString("course_name"));
				course.setCourseQuato(rs.getInt("course_quato"));
				course.setCourseReqCnt(rs.getInt("course_req_cnt"));
				course.setStatus(rs.getString("course_status"));
				course.setCourseCreateDate(rs.getDate("course_create_date"));
				course.setTeacherId(rs.getString("teacher_id"));

			}
			rs.close();
			pstmt.close();
			con.close();
			
			return course;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
				   
	}
	
	
}
