package com.dazlyn.dpo.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@Named
@ViewScoped
public class DashboardController implements Serializable {

    @Getter
    private DashboardModel model;

    @PostConstruct
    public void init() {
        initDashboardModel();
    }

    private void initDashboardModel() {
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();

        column1.addWidget("myClasses");
        column2.addWidget("chargesVsBalances");

        model = new DefaultDashboardModel();
        model.addColumn(column1);
        model.addColumn(column2);
    }
}
