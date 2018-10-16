package com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.PdfExcel.SpringBoot_PDF_EXCEL.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
