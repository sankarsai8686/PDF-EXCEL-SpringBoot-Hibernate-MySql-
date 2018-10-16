package com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.Repository.BookRepository;
import com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.model.Book;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> getAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

	@Override
	public boolean createPdf(List<Book> books, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
		Document doc = new Document(PageSize.A4, 15, 15, 45, 30);
		try 
		{
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			boolean exists = new File(filePath).exists();
			System.out.println("exists : "+exists);
			if(!exists)
			{
				new File(filePath).mkdirs();
			}
			
			PdfWriter writer =  PdfWriter.getInstance(doc, new FileOutputStream(file+"/"+"book"+".pdf"));
			doc.open();
			
			Font mainFont = FontFactory.getFont("Arial", 15, BaseColor.BLACK);
			
			Paragraph paragraph = new Paragraph("ALL BOOKS",mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			doc.add(paragraph);
			
			
			
			
			/*PdfPCell author = new PdfPCell(new Paragraph("AUTHOR",tableHeader));
			author.setBorderColor(BaseColor.BLACK);
			author.setPaddingLeft(10);
			author.setHorizontalAlignment(Element.ALIGN_CENTER);
			author.setVerticalAlignment(Element.ALIGN_CENTER);
			author.setBackgroundColor(BaseColor.RED);
			author.setExtraParagraphSpace(5f);
			table.addCell(author);
			
			PdfPCell title = new PdfPCell(new Paragraph("TITLE",tableHeader));
			title.setBorderColor(BaseColor.BLACK);
			title.setPaddingLeft(10);
			title.setHorizontalAlignment(Element.ALIGN_CENTER);
			title.setVerticalAlignment(Element.ALIGN_CENTER);
			title.setBackgroundColor(BaseColor.RED);
			title.setExtraParagraphSpace(5f);
			table.addCell(title);
*/			
			int no_of_records = books.size();
			System.out.println("no_of_records : "+no_of_records);
			if((no_of_records>0))
			{
				System.out.println("Records is there............................................");
				PdfPTable table = new PdfPTable(2);//columns
				table.setWidthPercentage(100);
				table.setSpacingAfter(10);
				table.setSpacingAfter(10);
				
				Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLUE);
				Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
				
				
				float[] columnWidth = {2f,2f};
				table.setWidths(columnWidth);
				
				
				List<String> headers = new ArrayList<>();
				headers.add("Header1");
				headers.add("Header2");
				
				for(String str : headers)
				{
					PdfPCell author = new PdfPCell(new Paragraph(str,tableHeader));
					author.setBorderColor(BaseColor.BLACK);
					author.setPaddingLeft(10);
					author.setHorizontalAlignment(Element.ALIGN_CENTER);
					author.setVerticalAlignment(Element.ALIGN_CENTER);
					author.setBackgroundColor(BaseColor.RED);
					author.setExtraParagraphSpace(5f);
					table.addCell(author);
				}
				for(Book book:books)
				{
					PdfPCell author_value = new PdfPCell(new Paragraph(book.getAuthor(),tableBody));
					author_value.setBorderColor(BaseColor.BLACK);
					author_value.setPaddingLeft(10);
					author_value.setHorizontalAlignment(Element.ALIGN_LEFT);
					author_value.setVerticalAlignment(Element.ALIGN_CENTER);
					author_value.setBackgroundColor(BaseColor.WHITE);
					author_value.setExtraParagraphSpace(5f);
					table.addCell(author_value);
					
					PdfPCell title_value = new PdfPCell(new Paragraph(book.getTitle(),tableBody));
					title_value.setBorderColor(BaseColor.BLACK);
					title_value.setPaddingLeft(10);
					title_value.setHorizontalAlignment(Element.ALIGN_CENTER);
					title_value.setVerticalAlignment(Element.ALIGN_CENTER);
					title_value.setBackgroundColor(BaseColor.WHITE);
					title_value.setExtraParagraphSpace(5f);
					table.addCell(title_value);
				}
				doc.add(table);
			}
			else
			{
				System.out.println("No Records .................................................");
				Font mainFont_No = FontFactory.getFont("Arial", 15, BaseColor.RED);
				Paragraph paragraph_NO = new Paragraph("NO RECOREDS",mainFont_No);
				paragraph_NO.setAlignment(Element.ALIGN_CENTER);
				paragraph_NO.setIndentationLeft(50);
				paragraph_NO.setIndentationRight(50);
				paragraph_NO.setSpacingAfter(10);
				doc.add(paragraph_NO);
			}
			//body here
					
			
			
			doc.close();
			writer.close();
			System.out.println("END OF PDF CREATION");
			return true;
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
			
		}
	
	}

	@Override
	public boolean createExcel(List<Book> books, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
		
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if(!exists)
		{
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file+"/"+"book"+".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet =  workbook.createSheet("BOOKS");
			workSheet.setDefaultColumnWidth(30);
			
			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			HSSFRow headerRow = workSheet.createRow(0);
			
			HSSFCell cell1 = headerRow.createCell(0);
			cell1.setCellValue("CELL 1");
			cell1.setCellStyle(headerCellStyle);
			
			HSSFCell cell2 = headerRow.createCell(1);
			cell2.setCellValue("CELL 2");
			cell2.setCellStyle(headerCellStyle);
			
			int i = 1;
			
			for(Book book : books)
			{
				HSSFRow bodyRow = workSheet.createRow(i);
				
				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
				bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				
				HSSFCell body_cell1 = bodyRow.createCell(0);
				body_cell1.setCellValue(book.getAuthor());
				body_cell1.setCellStyle(bodyCellStyle);
				
				HSSFCell body_cell2 = bodyRow.createCell(1);
				body_cell2.setCellValue(book.getTitle());
				body_cell2.setCellStyle(bodyCellStyle);
				
				i++;
			}
			
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
		
	}

}
