package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnUtils;
import vo.AcademyTeacher;

public class TeacherDao {

	public AcademyTeacher getTeacherById(String teacherId) {
		
		String sql = "select teacher_id, teacher_password, teacher_name, "
				   + "teacher_phone, teacher_email, teacher_salary, "
				   + "teacher_retired, teacher_create_date "
				   + "from academy_teacher "
				   + "where teacher_id = ?";
		
		try {
			AcademyTeacher teacher = null;
			
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, teacherId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				teacher = new AcademyTeacher();
				
				teacher.setId(rs.getString("teacher_id"));
				teacher.setPassword(rs.getString("teacher_password"));
				teacher.setName(rs.getString("teacher_name"));
				teacher.setPhone(rs.getString("teacher_phone"));
				teacher.setEmail(rs.getString("teacher_email"));
				teacher.setSalary(rs.getInt("teacher_salary"));
				teacher.setRetired(rs.getString("teacher_retired"));
				teacher.setCreateDate(rs.getDate("teacher_create_date"));
			}
 			
			rs.close();
			pstmt.close();
			con.close();
			
			return teacher;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	public void insertTeacher (AcademyTeacher teacher) {
		
		String sql = "insert into academy_teacher "
				   + "(teacher_id, teacher_password, teacher_name, teacher_phone, teacher_email, teacher_salary) "
                   + "values "
				   + "(?,?,?,?,?,?)";
		
		try {
			Connection con = ConnUtils.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, teacher.getId());
			pstmt.setString(2, teacher.getPassword());
			pstmt.setString(3, teacher.getName());
			pstmt.setString(4, teacher.getPhone());
			pstmt.setString(5, teacher.getEmail());
			pstmt.setInt(6, teacher.getSalary());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
