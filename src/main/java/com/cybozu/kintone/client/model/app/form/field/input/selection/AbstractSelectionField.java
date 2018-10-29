/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import java.util.HashMap;
import java.util.Map;

import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public class AbstractSelectionField extends AbstractInputField {
    protected Map<String, OptionData> options = new HashMap<String, OptionData>();

    /**
     * @return the options
     */
    public Map<String, OptionData> getOptions() {
        return this.options;
    }

    /**
     * @param options
     *            the options to set
     */
    public void setOptions(Map<String, OptionData> options) {
        this.options = options;
    }

    /**
     * @param option
     */
    public void addOption(OptionData option) {
        if (option == null || option.getLabel() == null || option.getLabel().trim().length() == 0) {
            return;
        }

        options.put(option.getLabel(), option);
    }

    /**
     * @param option
     */
    public void removeOption(OptionData option) {
        if (option == null || option.getLabel() == null || option.getLabel().trim().length() == 0) {
            return;
        }

        options.remove(option.getLabel());
    }
}
