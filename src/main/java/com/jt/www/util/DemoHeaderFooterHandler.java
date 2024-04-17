package com.jt.www.util;



import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.jt.www.exception.BizException;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Sy
 * @Date: 2023/2/17 18:23
 * @Version 1.0
 */
public class DemoHeaderFooterHandler implements IEventHandler {

    private final String imgLogo;
    private final PdfFont font;

    public DemoHeaderFooterHandler(final String imgLogo, final PdfFont font) {
        this.imgLogo = imgLogo;
        this.font = font;
    }

    @Override
    public void handleEvent(final Event event) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
            final PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            final PdfDocument pdfDoc = docEvent.getDocument();
            final Document doc = new Document(pdfDoc);
            final PdfPage page = docEvent.getPage();
            final int pageNumber = pdfDoc.getPageNumber(page);
            final Rectangle pageSize = page.getPageSize();
            final float pdfWidth = pageSize.getWidth();
            final float pdfHeight = pageSize.getHeight();
            final PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            final Color lineColor = new DeviceRgb(0, 0, 0);
            pdfCanvas.setLineWidth(1.5f).setStrokeColor(lineColor);
            final float tableWidth = pdfWidth - doc.getRightMargin() - doc.getLeftMargin();
            // 页眉
            final float x0 = doc.getRightMargin(), y0 = pdfHeight - doc.getTopMargin();
            //pdfCanvas.moveTo(x0, y0).lineTo(pdfWidth - doc.getRightMargin(), y0).stroke();
            final Table headerTable = new Table(2);
            headerTable.setHeight(30);
            headerTable.setFixedLayout();
            headerTable.setWidth(tableWidth);
            headerTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            final Paragraph pageNameParagraph = new Paragraph();
            // realnameParagraph.setFixedLeading(4f);
            pageNameParagraph.setVerticalAlignment(VerticalAlignment.BOTTOM);
            //左页眉
            pageNameParagraph.add(df.format(new Date()));
            final Cell pageNameCell = new Cell();
            pageNameCell.setBorder(Border.NO_BORDER);
            pageNameCell.add(pageNameParagraph);
            pageNameCell.setFont(this.font);
            pageNameCell.setFontSize(8f);
            pageNameCell.setFontColor(lineColor);
            pageNameCell.setVerticalAlignment(VerticalAlignment.BOTTOM);
            headerTable.addCell(pageNameCell);

            final Paragraph realnameParagraph = new Paragraph();
            // realnameParagraph.setFixedLeading(4f);
            realnameParagraph.setVerticalAlignment(VerticalAlignment.BOTTOM);
            realnameParagraph.add(new Tab()).addTabStops(new TabStop(20, TabAlignment.CENTER));
            realnameParagraph.add("电子行程单打印");
            final Cell realnameCell = new Cell();
            realnameCell.setBorder(Border.NO_BORDER);
            realnameCell.add(realnameParagraph);
            realnameCell.setFont(this.font);
            realnameCell.setFontSize(8f);
            realnameCell.setFontColor(lineColor);
            realnameCell.setVerticalAlignment(VerticalAlignment.BOTTOM);
            headerTable.addCell(realnameCell);
            headerTable.setFixedPosition(doc.getLeftMargin(), pdfHeight - doc.getTopMargin(), tableWidth);
            doc.add(headerTable);

            // 页脚
           // pdfCanvas.moveTo(x0, doc.getBottomMargin()).lineTo(pdfWidth - doc.getRightMargin(), doc.getBottomMargin())
            //        .stroke();

            final Table table = new Table(2);
            table.setHeight(40);
            table.setFixedLayout();
            table.setWidth(tableWidth);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            final Cell imgCell = new Cell();
            imgCell.setBorder(Border.NO_BORDER);
            imgCell.add(new Paragraph("https://www.chinatourins.com").setFontSize(11));
            imgCell.setVerticalAlignment(VerticalAlignment.TOP);
            table.addCell(imgCell);
            final String pageNo = String.format("%d", pageNumber);
            final Paragraph pageNoParagraph = new Paragraph();
            pageNoParagraph.setFontColor(new DeviceRgb(0, 0, 0));
            pageNoParagraph.setFontSize(12f);
            pageNoParagraph.add(new Tab()).addTabStops(new TabStop(1000, TabAlignment.RIGHT));
            pageNoParagraph.add(pageNo);
            table.addCell(new Cell().add(pageNoParagraph).setBorder(Border.NO_BORDER));
            table.setFixedPosition(doc.getLeftMargin(), doc.getBottomMargin() - table.getHeight().getValue(),
                    tableWidth);
            doc.add(table);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private Image getLogoImage() {
        Image image;
        try {
            image = new Image(ImageDataFactory.create(this.imgLogo)).setWidth(60);
            image.scaleToFit(75, 100);
        } catch (final MalformedURLException e) {
            throw new BizException("errorMessage", e);
        }
        return image;
    }

}

