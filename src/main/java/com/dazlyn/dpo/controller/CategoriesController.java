package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.CategoryType;
import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.model.CategoryEntity;
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
    private CategoryRepository categoryManager;

    @Getter
    @Setter
    private CategoryType selectedCategory;

    @Getter
    private List<CategoryEntity> options;

    @Getter
    @Setter
    private CategoryEntity selectedOption;

    @Getter
    @Setter
    private String optionValue;

    public CategoryType[] getCategoryList() {
        return CategoryType.values();
    }

    public void onCategoryChange() {
        selectedOption = null;
        options = categoryManager.findAllForType(super.geStudio(), selectedCategory);
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
        CategoryEntity option = categoryManager.find(selectedOption.getUid());
        categoryManager.remove(option);
        options.remove(selectedOption);
        selectedOption = null;
        updateSortOrders();

        // TODO: Move to archive state and not deleted for record keeping purposes
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
        for (CategoryEntity option : options) {
            option.setSortOrder(index++);
            categoryManager.merge(option);
        }
    }

    @Transactional
    public void save() {
        CategoryEntity option = categoryManager.find(selectedOption.getUid());
        option.setValue(optionValue);
        categoryManager.merge(option);
    }
}
