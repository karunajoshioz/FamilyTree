package com.fabric.familytree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import com.fabric.familytree.service.Family;
import com.fabric.familytree.service.FileProcessor;

/**
 * The task of this class is to just start and run the application. Its just
 * playing the role of orchestrator.
 * 
 * @author karunaj
 *
 */
public class MeetFamilyApplication {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		
		System.out.println("Please enter your Initialzation and the inputData FileName<for eg initInput.txt, input1.txt>: ");
		String initFile = scanner.nextLine();
		String initalizationFileName = initFile.split(",")[0].trim();
		String inputDataFile = initFile.split(",")[1].trim();
        
        processRequest(initalizationFileName, inputDataFile);
	}

	private static void processRequest(String initFile, String inputFile) {
		Family family = new Family();

		try {
			initFileToProcess(family, initFile, false);
			initFileToProcess(family, inputFile, true);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Please enter file location(s)!");
		}
	}
	
	/**
	 * Read file to process.
	 * 
	 * @param family
	 * @param filePath
	 * @param isInputFile
	 * @throws FileNotFoundException
	 */
	public static void initFileToProcess(Family family, String filePath, boolean isInputFile) {
		File file = null;
		//file = ResourceUtils.getFile("classpath:input/"+filePath);
		//file = new ClassPathResource("input/"+filePath).getFile();
		String data = "";
		ClassPathResource resource = new ClassPathResource("input/"+filePath);
		try {
		    byte[] dataArr = FileCopyUtils.copyToByteArray(resource.getInputStream());
		    data = new String(dataArr, StandardCharsets.UTF_8);
		} catch (IOException e) {
		    // do whatever
		}
		FileProcessor processor = new FileProcessor();
		processor.processInputFile(family, data, isInputFile);
	}



}
