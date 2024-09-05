package net.larichan.pagamento_salario.controllers;

import javax.persistence.PostPersist;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.enums.TipoVencimento;
import net.larichan.pagamento_salario.models.Vencimento;
import net.larichan.pagamento_salario.repositories.VencimentoRepository;

@Scope(value = "request")
@Component(value = "vencimentoController")
@ELBeanName(value = "vencimentoController")
@Join(path = "/vencimento-form", to = "/pages/vencimento/vencimento-form.jsf")
public class VencimentoController {
    @Autowired
    private VencimentoRepository repository;

    private Vencimento vencimento = Vencimento.builder().build();
    private Integer id;

    @PostPersist
    public void init(){
        id = null;
    }

    public void loadData() {
        if(id != null)
            vencimento = repository.findById(id).get();
    }

    public String save() {
        vencimento.setId(id);
        repository.save(vencimento);
        vencimento = Vencimento.builder().build();
        return "/pages/vencimento/vencimento-list.xhtml?faces-redirect=true";
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoVencimento[] getTiposVencimento() {
        return TipoVencimento.values();
    }
}
