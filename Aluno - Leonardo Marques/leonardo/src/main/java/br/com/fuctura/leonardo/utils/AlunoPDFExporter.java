package br.com.fuctura.leonardo.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import br.com.fuctura.leonardo.model.Aluno;

public class AlunoPDFExporter {

	private List<Aluno> listAlunos;

	public AlunoPDFExporter(List<Aluno> listAlunos) {
		this.listAlunos = listAlunos;
	}

	
	private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Nome", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Cpf", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Fone", font));
        table.addCell(cell);       
        
        cell.setPhrase(new Phrase("Tipo", font));
        table.addCell(cell);    
    }
	
	private void writeTableData(PdfPTable table) {
        for (Aluno aluno : listAlunos) {
            table.addCell(String.valueOf(aluno.getId()));
            table.addCell(aluno.getNome());
            table.addCell(aluno.getCpf());
            table.addCell(aluno.getEmail());
            table.addCell(aluno.getFone());
            table.addCell(aluno.getTipo());
        }
    }
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("Lista de Alunos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {0.5f, 1.0f, 1.5f, 2.2f, 1.3f, 1.7f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
	
}
