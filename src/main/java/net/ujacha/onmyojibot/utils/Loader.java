package net.ujacha.onmyojibot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.ujacha.onmyojibot.entity.SecretLetter;
import net.ujacha.onmyojibot.repository.SecretLetterRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.ujacha.onmyojibot.entity.Location;
import net.ujacha.onmyojibot.entity.Shikigami;
import net.ujacha.onmyojibot.repository.ShikigamiRepository;

public class Loader {

	private static final Logger log = LoggerFactory.getLogger(Loader.class);

	private static final String FILE_NAME = "onmyoji.xlsx";
	
	@Autowired
	private ShikigamiRepository shikigamiRepository;

	@Autowired
	private SecretLetterRepository secretLetterRepository;

	public void init() {
		
		File file = new File(getClass().getClassLoader().getResource(FILE_NAME).getFile());
		
		List<Shikigami> list =  this.loadShikigami(file);
		
		list.forEach(s -> shikigamiRepository.save(s));

		List<SecretLetter> secretLetters = loadSecretLetter(file);

		secretLetters.forEach(sl -> secretLetterRepository.save(sl));

	}
	
	public List<SecretLetter> loadSecretLetter(File file){

		List<SecretLetter> list = new ArrayList<>();

		try {
			if(file == null) {
				file = new File(FILE_NAME);
			}

			FileInputStream excelFile = new FileInputStream(file);

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(5);
			Iterator<Row> rowIterator = datatypeSheet.iterator();

			while (rowIterator.hasNext()) {


				Row currentRow = rowIterator.next();

				Iterator<Cell> cellIterator = currentRow.iterator();

				if (currentRow.getCell(0) != null && currentRow.getCell(1) != null) {

					Cell cellQ = currentRow.getCell(0);
					String q = cellQ.getStringCellValue();

					Cell cellA = currentRow.getCell(1);
					String a = null;
					switch (cellA.getCellTypeEnum()){
						case STRING:
							a = cellA.getStringCellValue();
							break;
						case NUMERIC:
							a = String.valueOf((int) cellA.getNumericCellValue());
							break;
						default:
							a = "";
							break;
					}

					SecretLetter secretLetter = new SecretLetter();

					secretLetter.setQuestion(q);
					secretLetter.setAnswer(a);

					log.debug("SECRET LETTER : {}",secretLetter);
					list.add(secretLetter);
				}

			}

			workbook.close();

		} catch (Exception e) {
			log.error("{}", e.getMessage(), e);
			e.printStackTrace();
		}

		log.debug("SIZE:{}", list.size());

		return list;
	}


	public List<Shikigami> loadShikigami(File file) {
		List<Shikigami> list = new ArrayList<>();

		try {
			if(file == null) {
				file = new File(FILE_NAME);
			}
			
			FileInputStream excelFile = new FileInputStream(file);

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(3);
			Iterator<Row> rowIterator = datatypeSheet.iterator();

			Shikigami shikigami = null;
			boolean isNewShikigami = false;

			List<Location> locations = new ArrayList<>();

			while (rowIterator.hasNext()) {

				Row currentRow = rowIterator.next();

				Location location = new Location();

				Iterator<Cell> cellIterator = currentRow.iterator();

				
				if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellTypeEnum() != CellType.BLANK) {
					if(shikigami != null) {
						Collections.sort(locations);
						shikigami.setLocations((Location[]) locations.toArray(new Location[locations.size()]));						
						log.debug("{}", shikigami);
						list.add(shikigami);
					}
					
					shikigami = new Shikigami();
					locations = new ArrayList<>();

					isNewShikigami = true;
				} else {
					isNewShikigami = false;
				}

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();

//					log.debug("{}:{}\t{}", currentCell.getRowIndex(), currentCell.getColumnIndex(), currentCell);
//					int rowIndex = currentCell.getRowIndex();
					int cellIndex = currentCell.getColumnIndex();

					switch (cellIndex) {
					case 2:
						location.setType(currentCell.getStringCellValue());
						break;
					case 3:
						if(currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							double d = currentCell.getNumericCellValue();
							
							location.setValue(String.valueOf((int) d));
						}else if(currentCell.getCellTypeEnum() == CellType.STRING) {
							location.setValue(currentCell.getStringCellValue());							
						} 
						break;
					case 4:
						if(currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							location.setCount((int) currentCell.getNumericCellValue());
						}
						break;
					
					default:
						break;
					}

					if (isNewShikigami) {
						switch (cellIndex) {
						case 0:
							shikigami.setRarity(currentCell.getStringCellValue());
							break;
						case 1:
							
							String name = currentCell.getStringCellValue();
							
							shikigami.setName(name);
							shikigami.setInitialName(KoreanInitialUtils.initialString(name));
							
							break;
						case 5: // 힌트
							String hintString = currentCell.getStringCellValue();
							String[] hints = hintString.split(",");
							shikigami.setHints(hints);
							break;
						case 6: // URL
							shikigami.setInfoPageUrl(currentCell.getStringCellValue());
							break;
						case 7: // 이미지
							shikigami.setImageUrl(currentCell.getStringCellValue());
							break;
						default:
							break;
						}
					}
				}

				locations.add(location);
			}

			
			// 마지막줄 처리
			if(shikigami != null) {
				Collections.sort(locations);
				shikigami.setLocations((Location[]) locations.toArray(new Location[locations.size()]));						
				log.debug("{}", shikigami);
				list.add(shikigami);
			}

			workbook.close();
			
		} catch (Exception e) {
			log.error("{}", e.getMessage(), e);
			e.printStackTrace();
		}
		
		log.debug("SIZE:{}", list.size());
		
		return list;

	}
	
}
