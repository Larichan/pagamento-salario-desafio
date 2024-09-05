package net.larichan.pagamento_salario.controllers;

import javax.persistence.PostPersist;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.models.Cargo;
import net.larichan.pagamento_salario.repositories.CargoRepository;

@Scope(value = "request")
@Component(value = "cargoController")
@ELBeanName(value = "cargoController")
@Join(path = "/cargo-form", to = "/pages/cargo/cargo-form.jsf")
public class CargoController {
@Autowired
    private CargoRepository repository;

    private Cargo cargo = Cargo.builder().build();
    private Integer id;

    @PostPersist
    public void init(){
        id = null;
    }

    public void loadData() {
        if(id != null)
            cargo = repository.findById(id).get();
    }

    public String save() {
        cargo.setId(id);
        repository.save(cargo);
        cargo = Cargo.builder().build();
        return "/pages/cargo/cargo-list.xhtml?faces-redirect=true";
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
