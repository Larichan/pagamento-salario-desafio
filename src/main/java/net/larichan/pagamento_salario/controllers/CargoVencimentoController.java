package net.larichan.pagamento_salario.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.PostPersist;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.models.Cargo;
import net.larichan.pagamento_salario.models.CargoVencimento;
import net.larichan.pagamento_salario.models.Vencimento;
import net.larichan.pagamento_salario.repositories.CargoRepository;
import net.larichan.pagamento_salario.repositories.CargoVencimentoRepository;
import net.larichan.pagamento_salario.repositories.VencimentoRepository;

@Scope(value = "request")
@Component(value = "cargoVencimentoController")
@ELBeanName(value = "cargoVencimentoController")
@Join(path = "/cargo-vencimento-form", to = "/pages/cargo-vencimento/cargo-vencimento-form.jsf")
public class CargoVencimentoController {
    @Autowired
    private CargoVencimentoRepository repository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private VencimentoRepository vencimentoRepository;

    private CargoVencimento cargoVencimento = CargoVencimento.builder().build();
    private Integer id;
    private List<Cargo> cargos;
    private Cargo cargoSelecionado;
    private List<Vencimento> vencimentos;
    private Vencimento vencimentoSelecionado;

    @PostPersist
    public void resetValues(){
        id = null;
        cargoSelecionado = null;
        vencimentoSelecionado = null;
    }

    @PostConstruct
    public void init() {
        cargos = cargoRepository.findAll();
        vencimentos = vencimentoRepository.findAll();
    }

    public void loadData() {
        if(id != null) {
            cargoVencimento = repository.findById(id).get();
            cargoSelecionado = cargoVencimento.getCargo();
            vencimentoSelecionado = cargoVencimento.getVencimento();
        }
    }

    public String save() {
        cargoVencimento.setId(id);
        cargoVencimento.setCargo(cargoSelecionado);
        cargoVencimento.setVencimento(vencimentoSelecionado);
        repository.save(cargoVencimento);
        cargoVencimento = CargoVencimento.builder().build();
        cargoSelecionado = null;
        vencimentoSelecionado = null;
        return "/pages/cargo-vencimento/cargo-vencimento-list.xhtml?faces-redirect=true";
    }

    public CargoVencimento getCargoVencimento() {
        return cargoVencimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Vencimento> getVencimentos() {
        return vencimentos;
    }

    public Vencimento getVencimentoSelecionado() {
        return vencimentoSelecionado;
    }

    public void setVencimentoSelecionado(Vencimento vencimento) {
        this.vencimentoSelecionado = vencimento;
    }
}
