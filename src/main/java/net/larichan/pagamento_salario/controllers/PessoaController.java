package net.larichan.pagamento_salario.controllers;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.PostPersist;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.models.Cargo;
import net.larichan.pagamento_salario.models.Pessoa;
import net.larichan.pagamento_salario.repositories.CargoRepository;
import net.larichan.pagamento_salario.repositories.PessoaRepository;

@Scope(value = "request")
@Component(value = "pessoaController")
@ELBeanName(value = "pessoaController")
@Join(path = "/pessoa-form", to = "/pages/pessoa/pessoa-form.jsf")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;
    @Autowired
    private CargoRepository cargoRepository;

    private Pessoa pessoa = Pessoa.builder().build();
    private Integer id;
    private Date dataNascimento;
    private List<Cargo> cargos;
    private Cargo cargoSelecionado;

    @PostPersist
    public void resetValues(){
        id = null;
        pessoa = Pessoa.builder().build();
        cargoSelecionado = null;
        dataNascimento = null;
    }

    @PostConstruct 
    public void init() {
        cargos = cargoRepository.findAll();
    }

    public void loadData() {
        if(id != null) {
            pessoa = repository.findById(id).get();
            cargoSelecionado = pessoa.getCargo();
            dataNascimento = Date.from(pessoa.getDataNascimento().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    public String save() {
        pessoa.setId(id);
        pessoa.setCargo(cargoSelecionado);
        pessoa.setDataNascimento(Instant.ofEpochMilli(dataNascimento.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
        repository.save(pessoa);
        pessoa = Pessoa.builder().build();
        cargoSelecionado = null;
        dataNascimento = null;
        return "/pages/pessoa/pessoa-list.xhtml?faces-redirect=true";
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public Cargo getCargoSelecionado() {
        return cargoSelecionado;
    }

    public void setCargoSelecionado(Cargo cargo) {
        this.cargoSelecionado = cargo;
    }
}
