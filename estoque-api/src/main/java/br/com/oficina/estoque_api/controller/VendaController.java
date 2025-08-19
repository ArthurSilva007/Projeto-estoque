package br.com.oficina.estoque_api.controller;

import br.com.oficina.estoque_api.dto.venda.VendaRequestDTO;
import br.com.oficina.estoque_api.dto.venda.VendaResponseDTO;
import br.com.oficina.estoque_api.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService service;

    @PostMapping
    public ResponseEntity<VendaResponseDTO> registrarVenda(@Valid @RequestBody VendaRequestDTO requestDTO) {
        VendaResponseDTO vendaRealizada = service.registrarVenda(requestDTO);
        return new ResponseEntity<>(vendaRealizada, HttpStatus.CREATED);
    }
    @GetMapping("/{id}/nota-pdf")
    public ResponseEntity<byte[]> gerarNotaPdf(@PathVariable Long id) {
        byte[] pdf = service.gerarPdfVenda(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "nota_venda_" + id + ".pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listarVendas() {
        List<VendaResponseDTO> vendas = service.listarTodas();
        return ResponseEntity.ok(vendas);
    }
}