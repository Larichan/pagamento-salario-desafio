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

import net.larichan.pagamento_salario.models.Vencimento;
import net.larichan.pagamento_salario.repositories.VencimentoRepository;

@Scope(value = "session")
@Component(value = "vencimentoListController")
@ELBeanName(value = "vencimentoListController")
@Join(path = "/vencimento-list", to = "/pages/vencimento/vencimento-list.jsf")
public class VencimentoListController {
    @Autowired
    private VencimentoRepository repository;

    private List<Vencimento> vencimentos;
    
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        vencimentos = repository.findAll();
    }

    public String update(Vencimento vencimento) {
        return "/pages/vencimento/vencimento-form.xhtml?faces-redirect=true&id=" + vencimento.getId();
    }

    public String delete(Vencimento vencimento) throws IOException {
        repository.delete(vencimento); 
        return "/pages/vencimento/vencimento-list.xhtml?faces-redirect=true";
    }
    
    public List<Vencimento> getVencimentos() {
        return vencimentos;
    }
}
