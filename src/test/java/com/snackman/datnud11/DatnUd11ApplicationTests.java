package com.snackman.datnud11;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@SpringBootTest
@Slf4j
class DatnUd11ApplicationTests {

	@Autowired
	private DateUtils dateUtils;

	@Autowired
	private CustomersRepository customersRepository;
	@Test
	void contextLoads() {
	}

	@Test
	public void test_setTimeZoneVietNam(){
		Optional<Customers> customers = customersRepository.findById(4L);
		log.info("customer is: {}", customers);
		Date date = customers.get().getDateOfBirth();
		log.info("daybirth is: {}", date);
		String preDate = dateUtils.dateToString(date);
		log.info("daybirth is2: {}", preDate);
		String format = "yyyy-MM-dd'T'HH:mm:ss'Z's";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		String[] availableTimezones = TimeZone.getAvailableIDs();
		for (String timezone : availableTimezones) {
			System.out.println("Timezone ID = " + timezone);
		}
		try {
			Date d = sdf.parse(preDate);
			log.info("date after is: {}", d);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void test_StringToDate(){

	}
	@Test
	public void test_dateToString(){

	}
}
