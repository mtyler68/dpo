package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.Category;
import com.dazlyn.dpo.model.CategoryManager;
import com.dazlyn.dpo.model.CategoryOption;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@Named
@ViewScoped
@Slf4j
public class CategoriesController extends AbstractSecureController implements Serializable {

    @Inject
    private CategoryManager categoryManager;

    @Getter
    @Setter
    private Category selectedCategory;

    @Getter
    private List<CategoryOption> options;

    @Getter
    @Setter
    private CategoryOption selectedOption;

    @Getter
    @Setter
    private String optionValue;

    public Category[] getCategoryList() {
        return Category.values();
    }

    public void onCategoryChange() {
        selectedOption = null;
        options = categoryManager.findForCategory(super.geStudio(), selectedCategory);
    }

    public void onOptionSelected(SelectEvent evt) {
        log.info("action=onOptionSelected, selectedOption={}", selectedOption);
        log.info("action=onOptionSelected, evt.object={}", evt.getObject());
        optionValue = selectedOption.getValue();
    }

    public void onOptionUnselected(UnselectEvent evt) {
        optionValue = "";
    }

    @Transactional
    public void deleteSelectedOption() {
        CategoryOption option = categoryManager.find(selectedOption.getUid());
        categoryManager.remove(option);
        options.remove(selectedOption);
        selectedOption = null;
        updateSortOrders();
    }

    @Transactional
    public void onOptionReorder(ReorderEvent evt) {
        updateSortOrders();
    }

    public void onPreAddOption() {
        optionValue = "";
    }

    public void addOption() {

    }

    private void updateSortOrders() {
        int index = 1;
        for (CategoryOption option : options) {
            option.setSortOrder(index++);
            categoryManager.merge(option);
        }
    }

    @Transactional
    public void save() {
        CategoryOption option = categoryManager.find(selectedOption.getUid());
        option.setValue(optionValue);
        categoryManager.merge(option);
    }
}
