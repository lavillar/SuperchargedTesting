package com.bk.hotel.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CheckinDatesTest {

	@ParameterizedTest(name ="{0}")
	@MethodSource("testData")
	public void verifyDateValidation(DateValidationBean dateValidation) {
		ReservationServiceImpl service = new ReservationServiceImpl();
		List<String> errorMsgs = service.verifyReservationDates(dateValidation.checkInDate, dateValidation.checkOutDate);
		assertThat(errorMsgs).containsExactlyInAnyOrder(dateValidation.errorMsgs);
	}


	static Stream<DateValidationBean> testData() {
		return Stream.of(new DateValidationBean("Valid booking dates", "03/03/2020", "03/07/2020"),
				new DateValidationBean("Null check-in date", null, "11/27/2018", "Must provide a check-in date."),
				new DateValidationBean("Both dates null", null, null, "Must provide a check-in date.",
						"Must provide a check-out date."),
				new DateValidationBean("Invalid check-in date", "02/30/2020", "03/07/2020",
						"check-in date of: 02/30/2020 is not a valid date or does not match date format of: MM/DD/YYYY"));
	}

	static class DateValidationBean {
		final String testName;
		final String checkInDate;
		final String checkOutDate;
		final String errorMsgs[];

		private DateValidationBean(String testName, String checkInDate, String checkOutDate, String... errorMsgs) {
			this.testName = testName;
			this.checkInDate = checkInDate;

			this.checkOutDate = checkOutDate;
			this.errorMsgs = errorMsgs;
		}

		@Override
		public String toString() {
			return testName;
		}
	}
}