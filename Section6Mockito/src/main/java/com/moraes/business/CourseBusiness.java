package com.moraes.business;

import java.util.LinkedList;
import java.util.List;

import com.moraes.service.CourseService;

// CourseBusiness = SUT - System Under Test
public class CourseBusiness {

	// CourseService is a Dependency
	private CourseService service;

	public CourseBusiness(CourseService service) {
		this.service = service;
	}
	
	public List<String> retrieveCoursesRelatedToSpring(String student) {
		List<String> filteredCourses = new LinkedList<>();
		final var allCourses = service.retrieveCourses(student);
		for(String course : allCourses) {
			if(course.contains("Spring")) {
				filteredCourses.add(course);
			}
		}
		
		return filteredCourses;
	}

	public void deleteCoursesNotRelatedToSpring(String student) {
		final var allCourses = service.retrieveCourses(student);
		for(String course : allCourses) {
			if(!course.contains("Spring")) {
				service.deleteCourse(course);
			}
		}
	}
}
