package net.larichan.pagamento_salario.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.models.CargoVencimento;
import net.larichan.pagamento_salario.models.Pessoa;
import net.larichan.pagamento_salario.models.PessoaSalarioConsolidado;
import net.larichan.pagamento_salario.repositories.CargoVencimentoRepository;
import net.larichan.pagamento_salario.repositories.PessoaRepository;
import net.larichan.pagamento_salario.repositories.PessoaSalarioConsolidadoRepository;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

@Scope(value = "session")
@Component(value = "pagamentoController")
@ELBeanName(value = "pagamentoController")
@Join(path = "/pagamento", to = "/pages/pagamento/pagamento.jsf")
public class PagamentoController {

    @Autowired
    private PessoaSalarioConsolidadoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private CargoVencimentoRepository cargoVencimentoRepository;

    private List<PessoaSalarioConsolidado> resultado = new ArrayList<>();

    public void resetValues() {
        resultado = new ArrayList<>();
    }

    public void calcularSalario() {
        resetValues();
        List<Pessoa> pessoas = pessoaRepository.findAll();
        for(Pessoa pessoa : pessoas) {
            if(pessoa.getCargo() != null) {
                List<CargoVencimento> vencimentosDoCargo = cargoVencimentoRepository.findByCargo(pessoa.getCargo());
                BigDecimal valorSalario = BigDecimal.ZERO;
                for(CargoVencimento cargoVencimento: vencimentosDoCargo) {
                    switch (cargoVencimento.getVencimento().getTipoVencimento()) {
                        case DEBITO:
                            valorSalario = valorSalario.subtract(cargoVencimento.getVencimento().getValor());
                            break;
                    
                        case CREDITO:
                            valorSalario = valorSalario.add(cargoVencimento.getVencimento().getValor());
                            break;
                        default:
                            break;
                    }
                }
                resultado.add(PessoaSalarioConsolidado.builder()
                .pessoa(pessoa)
                .nomePessoa(pessoa.getNome())
                .nomeCargo(pessoa.getCargo().getNome())
                .salario(valorSalario)
                .build());
            }
        }
        repository.saveAllAndFlush(resultado);
    }

    public void imprimir() {

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(new ClassPathResource("/reports/report.jasper").getInputStream(), getParameters(),  new JREmptyDataSource());
            provideFile("pagamentos.pdf", JasperExportManager.exportReportToPdf(jasperPrint));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<PessoaSalarioConsolidado> getResultado() {
        return resultado;
    }

    private static void provideFile(String fileName, byte[] dataFile) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String contentDisposition = ("attachment;filename=\"" + fileName + "\"");

        String contentType = getMimeType(dataFile);
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", contentDisposition);
        response.setContentLength(dataFile.length);

        servletOutputStream.write(dataFile, 0, dataFile.length);
        servletOutputStream.flush();
        servletOutputStream.close();
        FacesContext.getCurrentInstance().renderResponse();
        FacesContext.getCurrentInstance().responseComplete();
    }

    private static String getMimeType(byte[] dataFile) {
        String mimeType = null;
        MagicMatch magic;
        try {
            magic = Magic.getMagicMatch(dataFile);
            mimeType = magic.getMimeType();
        } catch (Exception e) {
            System.out.println(e);
        }

        return mimeType;
    }

    private Map<String, Object> getParameters(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SUB_DATA_SOURCE", resultado);
        return parametros;
    }
}
