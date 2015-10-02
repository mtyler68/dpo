package com.dazlyn.dpo.web.controller;

import javax.enterprise.inject.Model;
import org.primefaces.context.RequestContext;

@Model
public class StudiosController {

    public void addStudio() {
        RequestContext.getCurrentInstance().openDialog("/WEB-INF/dialogs/studioedit.xhtml");
    }

}
