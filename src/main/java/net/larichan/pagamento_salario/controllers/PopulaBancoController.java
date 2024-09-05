package net.larichan.pagamento_salario.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.enums.TipoVencimento;
import net.larichan.pagamento_salario.models.Cargo;
import net.larichan.pagamento_salario.models.CargoVencimento;
import net.larichan.pagamento_salario.models.Pessoa;
import net.larichan.pagamento_salario.models.Vencimento;
import net.larichan.pagamento_salario.repositories.CargoRepository;
import net.larichan.pagamento_salario.repositories.CargoVencimentoRepository;
import net.larichan.pagamento_salario.repositories.PessoaRepository;
import net.larichan.pagamento_salario.repositories.VencimentoRepository;

@Scope(value = "session")
@Component(value = "populaBancoController")
@ELBeanName(value = "populaBancoController")
@Join(path = "/popula-banco", to = "/pages/popular-banco/popular-banco.jsf")
public class PopulaBancoController {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private VencimentoRepository vencimentoRepository;
    @Autowired
    private CargoVencimentoRepository cargoVencimentoRepository;

    private List<Pessoa> pessoas = new ArrayList<>();
    private List<Cargo> cargos = new ArrayList<>();
    private List<Vencimento> vencimentos = new ArrayList<>();
    private List<CargoVencimento> cargosVencimento = new ArrayList<>();

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        InputStream inputStream = event.getFile().getInputstream();
        
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        handlePessoaSheet(workbook.getSheetAt(0));
        handleCargoSheet(workbook.getSheetAt(1));
        handleVencimentoSheet(workbook.getSheetAt(2));
        handleCargoVencimentoSheet(workbook.getSheetAt(3));

        workbook.close();
    }

    public String save() {
        cargoRepository.saveAllAndFlush(cargos);
        vencimentoRepository.saveAllAndFlush(vencimentos);
        cargoVencimentoRepository.saveAllAndFlush(cargosVencimento);
        pessoaRepository.saveAllAndFlush(pessoas);
        return "/index.xhtml?faces-redirect=true";
    }

    private void handlePessoaSheet(XSSFSheet sheet) {
        try {
            for(int rowNumber = 1; rowNumber < sheet.getPhysicalNumberOfRows(); rowNumber++) {
                XSSFRow row = sheet.getRow(rowNumber);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                LocalDate date = LocalDate.parse(row.getCell(9).getStringCellValue(), formatter);
                pessoas.add(Pessoa.builder()
                .id((int)row.getCell(0).getNumericCellValue())
                .nome(row.getCell(1).getStringCellValue())
                .cidade(row.getCell(2).getStringCellValue())
                .email(row.getCell(3).getStringCellValue())
                .cep(row.getCell(4).getStringCellValue())
                .endereco(row.getCell(5).getStringCellValue())
                .pais(row.getCell(6).getStringCellValue())
                .usuario(row.getCell(7).getStringCellValue())
                .telefone(row.getCell(8).getStringCellValue())
                .dataNascimento(date)
                .cargo(row.getCell(10) == null ? null : Cargo.builder().id((int)row.getCell(10).getNumericCellValue()).build())
                .build());
            }
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private void handleCargoSheet(XSSFSheet sheet) {
        try {
            for(int rowNumber = 1; rowNumber < sheet.getPhysicalNumberOfRows(); rowNumber++) {
                XSSFRow row = sheet.getRow(rowNumber);
                if(row == null) {
                    break;
                }
                cargos.add(Cargo.builder()
                .id((int)row.getCell(0).getNumericCellValue())
                .nome(row.getCell(1).getStringCellValue())
                .build());
            }
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private void handleVencimentoSheet(XSSFSheet sheet) {
        for(int rowNumber = 1; rowNumber < sheet.getPhysicalNumberOfRows(); rowNumber++) {
            XSSFRow row = sheet.getRow(rowNumber);
            if(row == null) {
                break;
            }
            vencimentos.add(Vencimento.builder()
            .id((int)row.getCell(0).getNumericCellValue())
            .descricao(row.getCell(1).getStringCellValue())
            .valor(new BigDecimal(row.getCell(2).getNumericCellValue()))
            .tipoVencimento(TipoVencimento.valueOf(row.getCell(3).getStringCellValue()))
            .build());
        }
    }

    private void handleCargoVencimentoSheet(XSSFSheet sheet) {
        for(int rowNumber = 1; rowNumber < sheet.getPhysicalNumberOfRows(); rowNumber++) {
            XSSFRow row = sheet.getRow(rowNumber);
            if(row == null) {
                break;
            }
            cargosVencimento.add(CargoVencimento.builder()
            .id((int)row.getCell(0).getNumericCellValue())
            .cargo(Cargo.builder().id((int)row.getCell(1).getNumericCellValue()).build())
            .vencimento(Vencimento.builder().id((int)row.getCell(2).getNumericCellValue()).build())
            .build());
        }
    }
}
