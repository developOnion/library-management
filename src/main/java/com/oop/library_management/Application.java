package com.oop.library_management;

import com.oop.library_management.config.HibernateUtil;
import org.hibernate.Session;

public class Application {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			System.out.println("Hibernate session opened successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.shutdown();
		}
	}

}
