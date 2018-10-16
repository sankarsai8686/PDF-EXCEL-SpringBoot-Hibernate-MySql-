package com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.model.Book;
import com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.service.BookService;

@Controller
public class PdfExcelController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ServletContext context;
	
	
	@GetMapping(value="/")
	public String getAllBooks(Model model)
	{
		List<Book> books = bookService.getAllBooks();
		model.addAttribute("books",books);
		System.out.println("getAllBooks");
		return "view/books";
	}
	
	
	
	@GetMapping(value="/createPdf")
	public void createPdf(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("createPdf");
		List<Book> books = bookService.getAllBooks();
		boolean isFlag = bookService.createPdf(books,context,request,response);
		System.out.println("isFlag : "+isFlag);
		if(isFlag)
		{
			String fullPath = request.getServletContext().getRealPath("resources/reports/"+"book"+".pdf");
			filedownload(fullPath,response,"book.pdf");
		}
		
	}

	@GetMapping(value="/createExcel")
	public void createExcel(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("createExcel");
		List<Book> books = bookService.getAllBooks();
		boolean isFlag = bookService.createExcel(books,context,request,response);
		System.out.println("isFlag : "+isFlag);
		if(isFlag)
		{
			String fullPath = request.getServletContext().getRealPath("resources/reports/"+"book"+".xls");
			filedownload(fullPath,response,"book.xls");
		}
		
	}



	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		System.out.println("filedownload : "+file.exists());
		if(file.exists())
		{
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment;filename="+fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = fileInputStream.read(buffer))!=-1)
				{
					outputStream.write(buffer,0,bytesRead);
				}
				fileInputStream.close();
				outputStream.close();
				//file.delete();
				System.out.println("COMPLETED");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
