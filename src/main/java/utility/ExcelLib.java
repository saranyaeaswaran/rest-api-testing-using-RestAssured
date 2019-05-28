package utility;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelLib {
	
	private Workbook workbook;
	private Sheet worksheet;
	private Row row;
	private Cell cell;
	private int rows;
	private String testCaseName;
	private int testCaseStartRow = 0;
	private int testCaseEndRow=0;
	private int usedColumnsCount = 0;
	private int iterationCount = 0;

	
	public ExcelLib(String workSheetName, String testCaseName) throws Exception{
		//excelLibNew xl = new excelLibNew("Quote_EBGeoRedundantHeadEndOnly", "Quote_EBHeadEndOnly");
		System.out.println("workSheetName name is : "+workSheetName);
		System.out.println("testCaseName name is : "+testCaseName);
//		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\Test_Data\\TestData.xlsx");
		FileInputStream fis = new FileInputStream(FrameworkConstants.DATA_FILE_PATH);
		workbook = new XSSFWorkbook(fis);
		
		worksheet = workbook.getSheet(workSheetName);
		
		System.out.println("Sheet name is : "+ worksheet.getSheetName());
		 this.testCaseName = testCaseName;
		 rows = getRowCount();
		 testCaseStartRow = getTestCaseStartRow();
		 testCaseEndRow = getTestCaseEndRow();
		 usedColumnsCount = getUsedColumnsCount();
		 System.out.println("usedColumnsCount+1 is "+usedColumnsCount);
		 iterationCount = getIterationCount();
	}
	
	/*Gets the total number of row count in the excel sheet*/
	private int getRowCount() {
		
		System.out.println("worksheet.getLastRowNum : "+worksheet.getLastRowNum());
		System.out.println("worksheet.getFirstRowNum : "+worksheet.getFirstRowNum());
		int rowCount = worksheet.getLastRowNum()-worksheet.getFirstRowNum();
		System.out.println("getRowCount is : "+rowCount);
		return rowCount;
	}
	
	
	/*Get TestCase Start Row*/
	private int getTestCaseStartRow(){
		try {
			for(int i = 1; i <= rows; i++){
				
				row = worksheet.getRow(i);
				cell=row.getCell(0);
				System.out.println("cell String is : "+cell.getStringCellValue());
				System.out.println("test case name is : "+testCaseName);
				if(cell.getStringCellValue().equals(testCaseName.trim())){
					testCaseStartRow = i;
					System.out.println("testCaseStartRow is : "+testCaseStartRow);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testCaseStartRow;
	}
	
	/*Get Test Case End Row*/
	private int getTestCaseEndRow(){
		try {
			
			for(int i = testCaseStartRow; i <= rows; i++){
				testCaseEndRow  = i;
				row = worksheet.getRow(i);
				cell=row.getCell(0);
				System.out.println("cell.getStringCellValue() : "+cell.getStringCellValue());
				System.out.println("testCaseName.trim() : "+testCaseName.trim());
				if(!cell.getStringCellValue().equals(testCaseName.trim())){
					testCaseEndRow  = i-1;
					System.out.println("testCaseEndRow is : "+testCaseEndRow);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testCaseEndRow;
	}
	
	/*Get the Columns Count for the referenced test case*/

	private int getUsedColumnsCount(){
		try {
			int count = 0;
			
			row = worksheet.getRow(testCaseStartRow);
			
			while(! row.getCell(count).getStringCellValue().equalsIgnoreCase("EndRow")){
					usedColumnsCount = count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("usedColumnsCount is : "+usedColumnsCount);
		return (usedColumnsCount+1);
	}
	
	
	
//	private int getUsedColumnsCount(){
//		try {
//			int count = 2;
//			row = worksheet.getRow(testCaseStartRow-1);
//			System.out.println("1.Row number is "+row.getRowNum());
//			String rowHeaderValue = row.getCell(count).getStringCellValue().trim();
//			System.out.println(rowHeaderValue);
//			while(rowHeaderValue!=null && !rowHeaderValue.trim().isEmpty()){
//					
//					count++;
//					usedColumnsCount=count;
//					if(row.getCell(count).getStringCellValue()==""){
//						System.out.println("cell value is blank");
//					}
//					rowHeaderValue = row.getCell(count).getStringCellValue().trim();
//					System.out.println("1. Cell count is : "+usedColumnsCount+" and the value is :"+rowHeaderValue);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("usedColumnsCount is : "+usedColumnsCount);
//		return (usedColumnsCount);
//	}
	
	
	private int getIterationCount(){
		try {
			for(int i =testCaseStartRow; i <= testCaseEndRow; i++){
				
				row = worksheet.getRow(i);
				
				if(row.getCell(1).getStringCellValue().equalsIgnoreCase("Yes")){
//					System.out.println("Execute column value is : "+row.getCell(usedColumnsCount).getStringCellValue());
					iterationCount++;
			}
				
		}
			System.out.println("Total no. of test case iteration is : "+iterationCount);
	} catch (Exception e) {
			e.printStackTrace();
		}
		if(iterationCount > 0){
			System.out.println("Total Number of Rows Selected is "+iterationCount);	
		}else{
			System.out.println("*************************************************************************************");
			System.out.println("Total Number of Rows Selected is 0. Please Check Execute Column in TestData.xls file");
			System.out.println("*************************************************************************************");
		}
		
		return iterationCount;
	}
	
	
	/*Return Two Dimensional Array to DataProvider*/	
	public Object[][] getTestdata(){
		int rowNum = 0;
		int colNum = 0;
		String data[][] = new String[iterationCount][usedColumnsCount-1];

		//Get the Test Data
		for(int i =testCaseStartRow; i <= testCaseEndRow; i++){
			colNum = 0;
			boolean flag = false;
			
			row = worksheet.getRow(i);
			
			if(row.getCell(1).getStringCellValue().equalsIgnoreCase("Yes")){
				flag = true;
				for(int j = 2; j < usedColumnsCount; j++){
					System.out.println("Cell content is : "+row.getCell(j).getStringCellValue());
					data[rowNum][colNum] = row.getCell(j).getStringCellValue();
					colNum++;
				}
			}
			if(flag){
				rowNum++;
			}
		}
		
		return data;
	}


}
