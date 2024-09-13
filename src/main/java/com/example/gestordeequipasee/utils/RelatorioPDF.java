package com.example.gestordeequipasee.utils;

import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Funcionario;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RelatorioPDF {

    public void gerarRelatorio(String nomeArquivo, List<Atividade> atividades, Funcionario funcionario) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            document.open();
            document.add(new Paragraph("Relatório de Atividades do Funcionário: " + funcionario.getNome()));
            document.add(new Paragraph("Email: " + funcionario.getEmail()));
            document.add(new Paragraph(" "));

            for (Atividade atividade : atividades) {
                document.add(new Paragraph("Descrição: " + atividade.getDescricao()));
                document.add(new Paragraph("Status: " + atividade.getStatus()));
                document.add(new Paragraph("Prioridade: " + atividade.getPrioridade()));
                document.add(new Paragraph(" "));
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
