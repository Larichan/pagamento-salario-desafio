package net.larichan.pagamento_salario.controllers;

import java.io.IOException;
import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.models.Cargo;
import net.larichan.pagamento_salario.repositories.CargoRepository;

@Scope(value = "session")
@Component(value = "cargoListController")
@ELBeanName(value = "cargoListController")
@Join(path = "/cargo-list", to = "/pages/cargo/cargo-list.jsf")
public class CargoListController {
    @Autowired
    private CargoRepository repository;

    private List<Cargo> cargos;
    
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        cargos = repository.findAll();
    }

    public String update(Cargo cargo) {
        return "/pages/cargo/cargo-form.xhtml?faces-redirect=true&id=" + cargo.getId();
    }

    public String delete(Cargo cargo) throws IOException {
        repository.delete(cargo); 
        return "/pages/cargo/cargo-list.xhtml?faces-redirect=true";
    }
    
    public List<Cargo> getCargos() {
        return cargos;
    }
}
