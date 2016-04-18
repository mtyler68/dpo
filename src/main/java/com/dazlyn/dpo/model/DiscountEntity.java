package com.dazlyn.dpo.model;

import java.io.Serializable;
import java.util.Date;

public class DiscountEntity extends AbstractStudioEntity implements Serializable {

    /**
     * Defines when the rule is applied, either user enabled on the student or
     * family, or enabled and applied based on rules, or both.
     */
    private int applicationTime;
    
    /**
     * Indicates when this rule applies to a student, a family, a course,
     * or none.
     */
    private DiscountAppliesToType appliesToType;
    
    /**
     * When true, the discount shows up as a switch/checkbox option on the
     * entity defined in appliesTo (i.e. military discount). When false, 
     * the rules are applied automatically at billing time.
     */
    private boolean userEnabled;
    
    private Date fromDate;
    
    private Date toDate;
    
    /**
     * This rule can be disabled manually/globally.
     */
    private boolean disabled;
    
    private byte [] ruleDefinition;
}
