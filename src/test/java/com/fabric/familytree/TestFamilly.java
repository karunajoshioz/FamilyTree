package com.fabric.familytree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import com.fabric.familytree.service.Family;
import com.fabric.familytree.service.FileProcessor;


@RunWith(SpringRunner.class)
public class TestFamilly {

	private static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
	private static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
	private static final String SATYA = "Satya";

	private static Family family;

	@Before
	public void setUpBeforeClass()  {
		family = new Family();
		
		File pathInitFile=null;
		String data = "";
		try {
			//pathInitFile = ResourceUtils.getFile("classpath:input/initInput.txt");
			
			ClassPathResource resource = new ClassPathResource("input/initInput.txt");
			try {
			    byte[] dataArr = FileCopyUtils.copyToByteArray(resource.getInputStream());
			    data = new String(dataArr, StandardCharsets.UTF_8);
			} catch (IOException e) {
			    // do whatever
			}
		} catch (Exception e) {
			e.printStackTrace();
		}//Paths.get("input/initInput.txt").toAbsolutePath().toString();
		//initFileToProcess(family, pathInitFile, false);
		FileProcessor processor = new FileProcessor();
		processor.processInputFile(family, data, false);
	}

	@Test
	public void addchildAllNullValues() {
		assertEquals(PERSON_NOT_FOUND, family.addchild(null, null, null));
	}

	@Test
	public void addchildNameNullValues() {
		assertEquals(CHILD_ADDITION_FAILED, family.addchild(SATYA, null, null));
	}

	@Test
	public void addchildGenderNullValues() {
		assertEquals(CHILD_ADDITION_FAILED, family.addchild(SATYA, "Ketu", null));
	}

	@Test
	public void addchildThroughFather() {
		assertEquals(CHILD_ADDITION_FAILED, family.addchild("Aras", "Ketu", "Male"));
	}

	@Test
	public void addchildThroughAbsentMember() {
		assertEquals(PERSON_NOT_FOUND, family.addchild("Aries", "Ketu", "Male"));
	}

	@Test
	public void addchildSuccess() {
		assertEquals("CHILD_ADDITION_SUCCEEDED", family.addchild(SATYA, "Ketu", "Male"));
	}

	@Test
	public void getRelationshipAllParamsNull() {
		assertEquals(PERSON_NOT_FOUND, family.getRelationship(null, null));
	}

	@Test
	public void getRelationshipRelationNull() {
		assertEquals("PROVIDE VALID RELATION", family.getRelationship(SATYA, null));
	}

	@ParameterizedTest
	@CsvSource({
		"Satya,WIFE,NOT YET IMPLEMENTED",
		"Satya,Paternal-Uncle,NONE",
		"Kriya,Paternal-Uncle,Asva",
		"Asva,Maternal-Uncle,Chit Ish Vich Aras",
		"Tritha,Paternal-Aunt,Satya",
		"Yodhan,Maternal-Aunt,Tritha",
		"Satvy,Sister-In-Law,Atya",
		"Vyas,Brother-In-Law,Asva",
		"Queen Anga,Son,Chit Ish Vich Aras",
		"Queen Anga,Daughter,Satya",
		"Chit,Siblings,Ish Vich Aras Satya"
	})
	public void getRelationshipValuesFromCsvFile(String memberName, String relation, String expected) {
		String actual = family.getRelationship(memberName, relation);
		assertEquals(expected, actual);
	}
}
