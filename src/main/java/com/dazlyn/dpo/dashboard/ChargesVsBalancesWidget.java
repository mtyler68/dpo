package com.dazlyn.dpo.dashboard;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

@Named
@ViewScoped
public class ChargesVsBalancesWidget implements Serializable {

    @Getter
    private HorizontalBarChartModel model;

    @PostConstruct
    public void init() {
        initModel();
    }

    private void initModel() {
        ChartSeries charges = new ChartSeries("Charges");
        charges.set("Jul '15", 6756);
        charges.set("Aug '15", 5988);
        charges.set("Sep '15", 9773);
        charges.set("Oct '15", 1245);

        ChartSeries balances = new ChartSeries("Payments");
        balances.set("Jul '15", 45);
        balances.set("Aug '15", 223);
        balances.set("Sep '15", 460);
        balances.set("Oct '15", 970);

        model = new HorizontalBarChartModel();
        model.addSeries(charges);
        model.addSeries(balances);

        model.setTitle("Charges vs. Unpaid Balances");
        model.setLegendPosition("e");
        model.setStacked(true);

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Amount $");
        xAxis.setMin(0);
        xAxis.setMax(11000);

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Month");
    }
}
