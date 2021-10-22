package com.sp.fc.user.service.helper;

import com.sp.fc.user.domain.School;
import com.sp.fc.user.service.SchoolService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SchoolTestHelper {
	private final SchoolService schoolService;

	public SchoolTestHelper(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public static School makeSchool(String name, String city) {
		return School.builder()
				.name("테스트 학교")
				.city("서울")
				.build();
	}

	public School createSchool(String name, String city) {
		return schoolService.save(makeSchool(name, city));
	}

	public static void assertSchool(School school, String name, String city) {
		assertNotNull(school.getSchoolId());
		assertNotNull(school.getCreated());
		assertNotNull(school.getUpdated());

		assertEquals(name, school.getName());
		assertEquals(city, school.getCity());
	}
}
