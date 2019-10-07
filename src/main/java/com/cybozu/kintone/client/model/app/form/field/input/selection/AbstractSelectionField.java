/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

import java.util.HashMap;

public class AbstractSelectionField extends AbstractInputField {
    protected HashMap<String, OptionData> options;

    /**
     * @return the options
     */
    public HashMap<String, OptionData> getOptions() {
        return this.options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(HashMap<String, OptionData> options) {
        this.options = options;
    }

    /**
     * @param option the option to add
     */
    public void addOption(OptionData option) {
        if (option == null || option.getLabel() == null || option.getLabel().trim().length() == 0) {
            return;
        }

        options.put(option.getLabel(), option);
    }

    /**
     * @param option the option to remove
     */
    public void removeOption(OptionData option) {
        if (option == null || option.getLabel() == null || option.getLabel().trim().length() == 0) {
            return;
        }

        options.remove(option.getLabel());
    }
}
