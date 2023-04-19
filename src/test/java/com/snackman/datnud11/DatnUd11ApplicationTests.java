package com.snackman.datnud11;

import com.snackman.datnud11.entity.Count;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.repo.BillDetailsRepository;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@Slf4j
class DatnUd11ApplicationTests {

	@Autowired
	private DateUtils dateUtils;

	@Autowired
	private CustomersRepository customersRepository;
	@Autowired
	private BillDetailsRepository billDetailsRepository;
	@Test
	void contextLoads() {
	}
	@Test
	public void test_hihi(){
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
		char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*")).toCharArray();
		String randomStr = RandomStringUtils.random( 15, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
		System.out.println( randomStr );
	}
	@Test
	public void test_dateToString(){

	}
}
