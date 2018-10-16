package com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.model.Book;

public interface BookService {

	List<Book> getAllBooks();

	boolean createPdf(List<Book> books, ServletContext context, HttpServletRequest request,HttpServletResponse response);

	boolean createExcel(List<Book> books, ServletContext context, HttpServletRequest request,
			HttpServletResponse response);

}
