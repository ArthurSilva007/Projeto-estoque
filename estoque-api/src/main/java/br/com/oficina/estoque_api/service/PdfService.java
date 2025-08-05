package br.com.oficina.estoque_api.service;

import br.com.oficina.estoque_api.domain.ItemVenda;
import br.com.oficina.estoque_api.domain.Venda;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    public byte[] gerarNotaVenda(Venda venda) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // --- CABEÇALHO ---
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Comprovante de Venda e Serviço", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            // --- INFORMAÇÕES DA OFICINA ---
            Paragraph infoOficina = new Paragraph();
            infoOficina.add(new Chunk("INFINIR AUTO AR\n"));
            infoOficina.add(new Chunk("Rua das Peças, 123 - Bairro Motor\n"));
            infoOficina.add(new Chunk("Telefone: (81) 99821-0386\n\n"));
            document.add(infoOficina);

            // --- INFORMAÇÕES DO CLIENTE E VENDA ---
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            Paragraph infoCliente = new Paragraph();
            infoCliente.add(new Chunk("Cliente: " + venda.getCliente().getNome() + "\n"));
            infoCliente.add(new Chunk("Data da Venda: " + venda.getDataVenda().format(formatter) + "\n\n"));
            document.add(infoCliente);

            // --- TABELA DE PRODUTOS ---
            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100);
            tabela.setWidths(new float[]{4f, 1f, 1.5f, 1.5f});

            Font fontCabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            tabela.addCell(new PdfPCell(new Phrase("Descrição do Produto", fontCabecalho)));
            tabela.addCell(new PdfPCell(new Phrase("Qtd.", fontCabecalho)));
            tabela.addCell(new PdfPCell(new Phrase("Valor Unit.", fontCabecalho)));
            tabela.addCell(new PdfPCell(new Phrase("Subtotal", fontCabecalho)));

            for (ItemVenda item : venda.getItens()) {
                tabela.addCell(item.getProduto().getDescricao());
                tabela.addCell(String.valueOf(item.getQuantidade()));
                tabela.addCell(String.format("R$ %.2f", item.getValorUnitario()));
                tabela.addCell(String.format("R$ %.2f", item.getValorUnitario().multiply(new java.math.BigDecimal(item.getQuantidade()))));
            }
            document.add(tabela);
            document.add(new Paragraph("\n"));

            // --- TOTAIS ---
            Paragraph totais = new Paragraph();
            totais.setAlignment(Element.ALIGN_RIGHT);
            totais.add(new Chunk(String.format("Valor Instalação: R$ %.2f\n", venda.getValorInstalacao())));
            totais.add(new Chunk(String.format("TOTAL GERAL: R$ %.2f\n\n", venda.getValorTotal()), fontTitulo));
            document.add(totais);

            // --- GARANTIA ---
            Font fontGarantia = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph garantia = new Paragraph("Garantia de 90 dias a partir da data da venda.", fontGarantia);
            garantia.setAlignment(Element.ALIGN_CENTER);
            document.add(garantia);
            document.add(new Paragraph("\n\n"));

            // --- RODAPÉ ---
            Paragraph rodape = new Paragraph("Volte Sempre! Agradecemos a sua preferência.");
            rodape.setAlignment(Element.ALIGN_CENTER);
            document.add(rodape);

            Font fontDisclaimer = FontFactory.getFont(FontFactory.HELVETICA, 8);
            Paragraph disclaimer = new Paragraph("\n\nEste documento é um comprovante de controle interno e não substitui a nota fiscal oficial.", fontDisclaimer);
            disclaimer.setAlignment(Element.ALIGN_CENTER);
            document.add(disclaimer);

            document.close();
            return outputStream.toByteArray();

        } catch (DocumentException | IOException e) { // <-- A MUDANÇA É AQUI
            // Agora capturamos ambas as exceções possíveis
            throw new RuntimeException("Erro ao gerar o PDF da venda", e);
        }
    }
}