package com.green.bsp.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Boolean;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.green.bsp.pojo.SalaryInfo;

/**
 * Binary Interchange File Format:Biff 二进制文件交换格式
 * @desc 导入读取和导出写入
 * 
 * */
public class RWExcelFile_jxl {
	
	//log
	private static final Logger LOG = LoggerFactory.getLogger(RWExcelFile_jxl.class);
	
	/**
	 * 读取excel工作簿信息.<br>
	 * @param book Excel工作簿
	 * */
	public List<SalaryInfo> readExcel(Workbook book){
		
		List<SalaryInfo> contentList = new LinkedList<SalaryInfo>();
		
		try {
			//获取excel下的sheet页总个数
			int maxSheet = book.getNumberOfSheets();
			LOG.info("Excel工作簿的sheet页总数：" + maxSheet + "页.");
			//获取各个excel，可以控制需要导入那个页
			for(int sheeti = 0; sheeti < maxSheet; sheeti++){
				LOG.info("第" + (sheeti + 1) + "页读取开始……");
				//获取sheet页
				Sheet sheet = book.getSheet(sheeti);
				//获取sheet的名称
				String sheetName = sheet.getName();
				LOG.info("第" + (sheeti + 1) + "页名称：" + sheetName + ".");
				//当前sheet实际行总数
				int realRows = sheet.getRows();
				LOG.info("第" + (sheeti + 1) + "页实际行总数：" + realRows + "行.");
				//当前sheet实际列总数
				int realColumns = sheet.getColumns();
				LOG.info("第" + (sheeti + 1) + "页实际列总数：" + realColumns + "列.");
				//遍历每行每列的单元格 --类似二位数组定位excel单元格读取
				//行
				for(int row = 1; row < realRows; row++){
					
					//列
					for(int column = 0; column < realColumns; column++){
						//定位的单元格
						Cell cell = sheet.getCell(column, row);
						//获取单元格内容 -- 注：数据验证格式/纯数字需对科学计数法转换
						String content = cell.getContents();
						
						if (row == 0)
						{
							LOG.info("读取标题[" + content +"]");
						}
						else {
							LOG.info("读取数据[" + content +"]");
						}
						
					}
					
					contentList.add(buildSalaryInfo(sheet, row));
				}
				
				//释放资源
				sheet = null;
			}
		} catch (IndexOutOfBoundsException e) {
			LOG.info("读取excel工作簿信息数组下标越界：" + e.getMessage(), e);
		}finally{
			//释放资源
			book.close();
		}
		
		return contentList;
	}
	
	private Double getDoubleCell(Sheet sheet, int row, int column) {
		
		Double result = 0.00;
		
		String content = "";
		
		if (column < sheet.getColumns() && row < sheet.getRows()) {
			content = sheet.getCell(column, row).getContents();
		}
		
		try 
		{
			result = Double.valueOf(content);
		} 
		catch(NumberFormatException ex)
		{
			LOG.error("convert cell to double catch exception, exception is {}", ex);
		}
		
		return result;
	}
	
	private SalaryInfo buildSalaryInfo(Sheet sheet, int row) 
	{
		SalaryInfo salaryInfo = new SalaryInfo();
		salaryInfo.setDate(sheet.getCell(ExcelInfoConst.EXC_COL_DATE, row).getContents());
		salaryInfo.setUserName(sheet.getCell(ExcelInfoConst.EXC_COL_USER_NAME, row).getContents());
		salaryInfo.setPostSalary(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_POST_SALARY));
		salaryInfo.setAdvancePerformanceAward(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_ADVANCE_PERFORMANCE_AWARD));
		salaryInfo.setSafetyAward(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_SAFETY_AWARD));
		salaryInfo.setSeniorityWage(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_SENIORITY_WAGE));
		salaryInfo.setSpecialPaste(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_SPECIAL_PASTE));
		salaryInfo.setPerformanceLiquidation(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_PERFORMANCE_LIQUIDATION));
		salaryInfo.setCoolingAndHeatingCosts(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_COOLING_AND_HEATING_COSTS));
		salaryInfo.setReplenishment(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_REPLENISHMENT));
		salaryInfo.setOvertimePay(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_OVERTIME_PAY));
		salaryInfo.setReward(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_REWARD));
		salaryInfo.setSubsidiesForOutgoingCars(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_SUBSIDIES_FOR_OUTGOING_CARS));
		salaryInfo.setLifeSecurity(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_LIFE_SECURITY));
		salaryInfo.setMealCosts(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_MEAL_COSTS));
		salaryInfo.setDeservedWages(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_DESERVED_WAGES));
		salaryInfo.setPension(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_PENSION));
		salaryInfo.setPensionAdjustment(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_PENSION_ADJUSTMENT));
		salaryInfo.setAnnuity(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_ANNUITY));
		salaryInfo.setUnemploymentBenefits(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_UNEMPLOYMENT_BENEFITS));
		salaryInfo.setUnemploymentAdjustment(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_UNEMPLOYMENT_ADJUSTMENT));
		salaryInfo.setMedicalTreatment(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_MEDICAL_TREATMENT));
		salaryInfo.setMedicalTreatmentAdjustment(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_MEDICAL_TREATMENT_ADJUSTMENT));
		salaryInfo.setProvidentFund(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_PROVIDENT_FUND));
		salaryInfo.setSeriousIllness(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_SERIOUS_ILLNESS));
		salaryInfo.setTelephoneSubsidies(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_TELEPHONE_SUBSIDIES));
		salaryInfo.setReserveFund(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_RESERVE_FUND));
		salaryInfo.setPersonalIncomeTax(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_PERSONAL_INCOME_TAX));
		salaryInfo.setRealWages(getDoubleCell(sheet, row, ExcelInfoConst.EXC_COL_REAL_WAGES));
		return salaryInfo;
	}
	
	/**
	 * 写入数据导出excel工作簿 -- 数据格式化
	 * */
	public void writeExcel(){
		WritableWorkbook book = null;
			try {
				//不加路径，导出在项目根目录
				String excelName = "writeExcel.xls";
				File excel = new File(excelName);
				book = Workbook.createWorkbook(excel);
				//参数一：名称，写入名为"batchexport"sheet页,参数二：0表示这是第一页
				WritableSheet sheet = book.createSheet("batchexport", 0);
				//行高和列宽
				//将第一行的高度设为320
				sheet.setRowView(0 , 320);
				//将第4列的宽度设为30
				sheet.setColumnView(3 , 300);
				sheet.setColumnView(1 , 30);
				//构造Label对象，并指定单元格位置是第一列第一行(0,0)--(列,行)
				Label str1 = new Label(0, 0, "卡卡");
				//将定义好的str1添加到工作表中
				sheet.addCell(str1);
				Number num1 = new Number(1, 0, 17400005555.889d);
				sheet.addCell(num1);
				Boolean bool1 = new Boolean(2, 0, true);
				sheet.addCell(bool1);
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
				//---字串格式化
				//WritableFont不同情况下有非常丰富的构造方法，jExcelAPI的java-doc中有详细介绍，也可搜索.
				//字串格式：字体为TIMES，字号16，加粗显示  --WritableFont.createFont("宋体")
				WritableFont font1 =  new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD);
				//WritableCellFormat类，通过它可以指定单元格的各种属性，单元格格式化
				WritableCellFormat format1 = new WritableCellFormat(font1);
				//定数据的对齐方式
				//水平对齐方式指定为居中
				format1.setAlignment(Alignment.CENTRE);
				format1.setVerticalAlignment(VerticalAlignment.CENTRE);
				//垂直对齐方式指定为居中
				//字串被赋予format1格式
				Label date = new Label(3, 0, "时间:" +time,format1);
				sheet.addCell(date);
				//合并第一列，第二行到第六列，第一行的所有单元格
				sheet.mergeCells(0 , 1, 5, 1);
				//写入数据到工作簿
				book.write();
				LOG.info(excelName + "写入文件导出成功.");
			} catch (RowsExceededException e) {
				LOG.info("写入Excel工作簿内容行超过异常：" + e.getMessage(), e);
			} catch (WriteException e) {
				LOG.info("写入Excel工作簿内容写入异常：" + e.getMessage(), e);
			} catch (IOException e) {
				LOG.info("写入Excel工作簿内容输入输出流异常：" + e.getMessage(), e);
			}finally{
				try {
					//关闭文件
					book.close();
				} catch (WriteException e) {
					LOG.info("写入Excel工作簿内容写入异常：" + e.getMessage(), e);
				} catch (IOException e) {
					LOG.info("写入Excel工作簿内容输入输出流异常：" + e.getMessage(), e);
				}
			}
	}
	
	/**
	 * 修改数据导出excel工作簿
	 * */
	public void editExcel(){
		String excelName = "writeExcel.xls";
		Workbook orgBook = null;
		WritableWorkbook reBook = null;
		try {
			File orgExcel = new File(excelName);
			File reExcel = new File(excelName);
			//获得excel文件
			orgBook = Workbook.getWorkbook(orgExcel);
			//打开一个excel文件的副本，并且指定数据写回到原文件
			reBook = Workbook.createWorkbook(reExcel, orgBook);
			//添加一个工作页
			WritableSheet sheet = reBook.createSheet("editSheet", 1);
			sheet.addCell(new Label(0, 0, "编辑excel文件测试数据"));
			reBook.write();
			LOG.info(excelName + "编辑文件导出成功.");
		} catch (RowsExceededException e) {
			LOG.info("编辑Excel工作簿内容异常：" + e.getMessage(), e);
		} catch (BiffException e) {
			LOG.info("编辑Excel工作簿内容异常：" + e.getMessage(), e);
		} catch (WriteException e) {
			LOG.info("编辑Excel工作簿内容异常：" + e.getMessage(), e);
		} catch (IOException e) {
			LOG.info("编辑Excel工作簿内容异常：" + e.getMessage(), e);
		}finally{
			try {
				reBook.close();
				orgBook.close();
			} catch (WriteException e) {
				LOG.info("编辑Excel工作簿内容写入异常：" + e.getMessage(), e);
			} catch (IOException e) {
				LOG.info("编辑Excel工作簿内容输入输出流异常：" + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 文件形式.<br>
	 * 操作Excel工作簿.<br>
	 * @param file Excel文件
	 * */
	public List<SalaryInfo> readExcelOfFile(File file){
		List<SalaryInfo> salaryList= new LinkedList<SalaryInfo>();
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(file);
			salaryList = readExcel(book);
		} catch (BiffException e) {
			LOG.info("读取Excel工作簿文件二进制文件交换格式异常：" + e.getMessage(), e);
		} catch (IOException e) {
			LOG.info("读取Excel工作簿文件输入输出流异常：" + e.getMessage(), e);
		}
		return salaryList;
	}
	
	/**
	 * 输入流形式.<br>
	 * 操作Excel工作簿.<br>
	 * @param is Excel输入流，比如上传
	 * @throws IOException 
	 * */
	public void readExcelOfInputStream(InputStream is) throws IOException{
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(is);
			readExcel(book);
		} catch (BiffException e) {
			LOG.info("读取Excel工作簿文件二进制文件交换格式异常：" + e.getMessage(), e);
		} catch (IOException e) {
			LOG.info("读取Excel工作簿文件输入输出流异常：" + e.getMessage(), e);
		}finally{
			is.close();
		}
	}
	
	//测试
	public static void main(String[] args) {
		RWExcelFile_jxl jxlrw1 = new RWExcelFile_jxl();
		//读取
		//单元格格式--此处都为"文本"
		String excelPath = "D:/RWExcelFile_jxl.xlsx";
		File excel = new File(excelPath);
		jxlrw1.readExcelOfFile(excel);
		//导出
		jxlrw1.writeExcel();
		//编辑
		jxlrw1.editExcel();
	}
}

