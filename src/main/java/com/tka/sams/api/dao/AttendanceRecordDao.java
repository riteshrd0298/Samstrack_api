package com.tka.sams.api.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.sams.api.entity.AttendanceRecord;

@Repository
public class AttendanceRecordDao {
	@Autowired
	private SessionFactory factory;

	public List<AttendanceRecord> getAllAttendanceRecords() {
		Session session = null;
		List<AttendanceRecord> list = null;
		try {
			session = factory.openSession();

			Criteria criteria = session.createCriteria(AttendanceRecord.class);
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	 public List<AttendanceRecord> getAllAttendanceRecords(String date, Long subjectId) {
	        List<AttendanceRecord> attendanceRecords = Collections.emptyList();
	        try (Session session = factory.openSession()) {
	            Criteria criteria = session.createCriteria(AttendanceRecord.class);

	            // Add condition for date
	            if (date != null && !date.isEmpty()) {
	                criteria.add(Restrictions.eq("date", date));
	            }

	            // Add condition for subjectId
	            if (subjectId != null) {
	                criteria.add(Restrictions.eq("subject.id", subjectId));
	            }

	            attendanceRecords = criteria.list();
	        } catch (Exception e) {
	            e.printStackTrace(); // Replace with proper logging
	        }
	        return attendanceRecords;
	    }

	public AttendanceRecord saveAttendance(AttendanceRecord attendanceRecord) {
		Session session = null;
		AttendanceRecord record = null;
		try {
			session = factory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(attendanceRecord);
			transaction.commit();
			record = attendanceRecord;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return record;
	}
}
