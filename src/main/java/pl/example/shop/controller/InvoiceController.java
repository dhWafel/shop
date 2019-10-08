package pl.example.shop.controller;

import javassist.NotFoundException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.Invoice;
import pl.example.shop.service.InvoiceService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/{orderNumber}")
    public Invoice createInvoice(@PathVariable String orderNumber) throws NotFoundException {
        return invoiceService.createInvoice(orderNumber);
    }

    @GetMapping("/file/{orderNumber}")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable String orderNumber) throws IOException {
        HSSFWorkbook workbook = invoiceService.generateInvoice(orderNumber);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
        } finally {
            byteArrayOutputStream.close();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.ms.excel");
        headers.set("Content-Length", Integer.toString(bytes.length));
        headers.set("Content-Disposition", "attachment;filename=invoice" + orderNumber + ".xls");

        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Page<Invoice> showInvoice(@PathVariable Long id, Pageable pageable) {
        return invoiceService.findByIdUser(id, pageable);
    }


}

//favourites product dodawanie(unikalne, usuwanie (domain, service controller, repository)
//pozamieniawszystkie listy na page (tylko gety)