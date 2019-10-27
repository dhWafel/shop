package pl.example.shop.service;

import javassist.NotFoundException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.example.shop.domain.ClientOrder;
import pl.example.shop.domain.Invoice;
import pl.example.shop.domain.User;
import pl.example.shop.repository.ClientOrderRepository;
import pl.example.shop.repository.InvoiceRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDateTime.*;


@Service
public class InvoiceService {

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(String orderNumber) throws NotFoundException {
        Invoice invoice = invoiceRepository.findByOrderNumber(orderNumber);
        if (invoice != null) {
            return invoice;
        }

        List<ClientOrder> clientOrderList = clientOrderRepository.findByOrderNumber(orderNumber);

        if (clientOrderList.isEmpty()) {
            throw new NotFoundException("order number is not found");
        }

        User user = clientOrderList.get(0).getUser();
        Double invoiceValue = clientOrderList.get(0).getOrderValue();

        invoice = Invoice.builder()
                .orderNumber(orderNumber)
                .price(invoiceValue)
                .user(user)
                .payingDate(now().plusDays(30))
                .build();
        return invoiceRepository.save(invoice);
    }

    public Page<Invoice> findByUserEmail(@PageableDefault Pageable pageable) {
        String getEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return invoiceRepository.findByUserEmail(getEmail, pageable);
    }

    public HSSFWorkbook generateInvoice(String orderNumber) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("invoice");

        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Nazwa produktu");
        row.createCell(1).setCellValue("Ilość produktu");
        row.createCell(2).setCellValue("Cena jednostkowa");
        row.createCell(3).setCellValue("Wartość towaru");

        List<ClientOrder> orders = clientOrderRepository.findByOrderNumber(orderNumber);

        Double totalInvoiceValue = 0.0;

        for (int i = 0; i < orders.size(); i++) {
            ClientOrder o = orders.get(i);
            row = sheet.createRow(i + 1);

            Double itemPrice;

            row.createCell(0).setCellValue(o.getProduct().getName());
            row.createCell(1).setCellValue(o.getQuantity());
            row.createCell(2).setCellValue(o.getProduct().getPrice());

            itemPrice = o.getProduct().getPrice() * o.getQuantity();
            row.createCell(3).setCellValue(itemPrice);

            totalInvoiceValue += itemPrice;
        }

        Invoice invoice = invoiceRepository.findByOrderNumber(orderNumber);

        row = sheet.createRow(orders.size() + 2);

        row.createCell(0).setCellValue("Sumaryczna wartosc faktury");
        row.createCell(3).setCellValue(totalInvoiceValue);

        row = sheet.createRow(orders.size() + 4);
        row.createCell(0).setCellValue("Dane do faktury :");

        row = sheet.createRow(orders.size() + 6);
        row.createCell(0).setCellValue("Numer faktury :");
        row.createCell(1).setCellValue("FV/" + invoice.getId() + "/2019");
        row = sheet.createRow(orders.size() + 7);
        row.createCell(0).setCellValue("Data wystawienia :");
        row.createCell(1).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        row = sheet.createRow(orders.size() + 8);
        row.createCell(0).setCellValue("Data zakupu :");
        row.createCell(1).setCellValue(invoice.getBuyingDate().toString().replace("T", " "));
        row = sheet.createRow(orders.size() + 9);
        row.createCell(0).setCellValue("Data zapłaty");
        row.createCell(1).setCellValue(invoice.getPayingDate().toString().replace("T", " "));

        row = sheet.createRow(orders.size() + 11);
        row.createCell(0).setCellValue("Dane użytkownika :");

        row = sheet.createRow(orders.size() + 12);
        row.createCell(0).setCellValue("Imie :");
        row.createCell(1).setCellValue(invoice.getUser().getName());
        row = sheet.createRow(orders.size() + 13);
        row.createCell(0).setCellValue("Nazwisko :");
        row.createCell(1).setCellValue(invoice.getUser().getSurname());
        row = sheet.createRow(orders.size() + 14);
        row.createCell(0).setCellValue("E-mail :");
        row.createCell(1).setCellValue(invoice.getUser().getEmail());
        row = sheet.createRow(orders.size() + 15);
        row.createCell(0).setCellValue("Wiek :");
        row.createCell(1).setCellValue(invoice.getUser().getAge());

        return workbook;
    }
}
