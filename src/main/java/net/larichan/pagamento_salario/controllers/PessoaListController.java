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

import net.larichan.pagamento_salario.models.Pessoa;
import net.larichan.pagamento_salario.repositories.PessoaRepository;

@Scope(value = "session")
@Component(value = "pessoaListController")
@ELBeanName(value = "pessoaListController")
@Join(path = "/pessoa-list", to = "/pages/pessoa/pessoa-list.jsf")
public class PessoaListController {
    @Autowired
    private PessoaRepository repository;

    private List<Pessoa> pessoas;
    
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        pessoas = repository.findAll();
    }

    public String update(Pessoa pessoa) {
        return "/pages/pessoa/pessoa-form.xhtml?faces-redirect=true&id=" + pessoa.getId();
    }

    public String delete(Pessoa pessoa) throws IOException {
        repository.delete(pessoa); 
        return "/pages/pessoa/pessoa-list.xhtml?faces-redirect=true";
    }
    
    public List<Pessoa> getPessoas() {
        return pessoas;
    }
}
