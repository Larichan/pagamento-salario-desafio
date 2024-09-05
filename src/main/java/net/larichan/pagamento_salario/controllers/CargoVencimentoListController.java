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

import net.larichan.pagamento_salario.models.CargoVencimento;
import net.larichan.pagamento_salario.repositories.CargoVencimentoRepository;

@Scope(value = "session")
@Component(value = "cargoVencimentoListController")
@ELBeanName(value = "cargoVencimentoListController")
@Join(path = "/cargo-vencimento-list", to = "/pages/cargo-vencimento/cargo-vencimento-list.jsf")
public class CargoVencimentoListController {
    @Autowired
    private CargoVencimentoRepository repository;

    private List<CargoVencimento> cargoVencimentos;
    
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        cargoVencimentos = repository.findAll();
    }

    public String update(CargoVencimento cargoVencimento) {
        return "/pages/cargo-vencimento/cargo-vencimento-form.xhtml?faces-redirect=true&id=" + cargoVencimento.getId();
    }

    public String delete(CargoVencimento cargoVencimento) throws IOException {
        repository.delete(cargoVencimento); 
        return "/pages/cargo-vencimento/cargo-vencimento-list.xhtml?faces-redirect=true";
    }
    
    public List<CargoVencimento> getCargoVencimentos() {
        return cargoVencimentos;
    }
}
