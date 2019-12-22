package cn.codemodel.common.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class TestPoi {
    public static void main(String[] args) throws Exception {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("test");
        FileOutputStream fos = new FileOutputStream("/Users/yanqi/Downloads/test.xlsx");
        wb.write(fos);
        fos.close();
    }
}
