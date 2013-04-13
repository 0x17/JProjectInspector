package org.andreschnabel.jprojectinspector.freechart;

import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class FreeChartPdfExporter {

	// Source: http://www.jfree.org/phpBB2/viewtopic.php?t=616
	public static void saveChartToPDF(JFreeChart chart, String fileName, int width, int height) throws Exception {
		if(chart != null) {
			BufferedOutputStream out = null;
			try {
				out = new BufferedOutputStream(new FileOutputStream(fileName));

				com.lowagie.text.Rectangle pagesize = new com.lowagie.text.Rectangle(width, height);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pagesize, 50, 50, 50, 50);

				try {
					PdfWriter writer = PdfWriter.getInstance(document, out);
					document.addAuthor("JFreeChart");
					document.open();

					PdfContentByte cb = writer.getDirectContent();
					PdfTemplate tp = cb.createTemplate(width, height);
					Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());

					Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
					chart.draw(g2, r2D, null);
					g2.dispose();
					cb.addTemplate(tp, 0, 0);
				} finally {
					document.close();
				}
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

}
