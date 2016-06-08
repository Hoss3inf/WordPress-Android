package org.wordpress.android.ui.menus.items;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import org.wordpress.android.R;
import org.wordpress.android.models.MenuItemModel;

/**
 */
public class LinkItemEditor extends BaseMenuItemEditor {
    public static final String LINK_URL_KEY = "url";
    public static final String LINK_NEW_TAB_KEY = "open-new-tab";

    private EditText mUrlEditText;
    private CheckBox mOpenNewTabCheckBox;

    @Override
    public int getIconDrawable() {
        return R.drawable.gridicon_link;
    }

    @Override
    public View getEditView(Context context, ViewGroup root) {
        View editView = View.inflate(context, R.layout.link_menu_item_edit_view, root);

        mUrlEditText = (EditText) editView.findViewById(R.id.link_editor_url_input);
        mOpenNewTabCheckBox = (CheckBox) editView.findViewById(R.id.link_editor_open_new_tab_checkbox);

        return editView;
    }

    @Override
    public MenuItemModel getMenuItem() {
        MenuItemModel menuItem = new MenuItemModel();
        fillData(menuItem);
        return menuItem;
    }

    @Override
    public void onSave() {
        // TODO: save to DB
    }

    @Override
    public void onDelete() {
        // TODO: remove from DB
    }

    public void setUrl(String url) {
        if (mUrlEditText != null) {
            mUrlEditText.setText(url);
        }
    }

    public void setOpenInNewTab(boolean shouldOpenNewTab) {
        if (mOpenNewTabCheckBox != null) {
            mOpenNewTabCheckBox.setChecked(shouldOpenNewTab);
        }
    }

    public String getUrl() {
        return mUrlEditText != null ? String.valueOf(mUrlEditText.getText()) : "";
    }

    public boolean shouldOpenNewTab() {
        return mOpenNewTabCheckBox != null && mOpenNewTabCheckBox.isChecked();
    }

    private void fillData(@NonNull MenuItemModel menuItem) {
        if (mUrlEditText != null) {
            menuItem.addData(LINK_URL_KEY, String.valueOf(mUrlEditText.getText()));
        }
        if (mOpenNewTabCheckBox != null) {
            menuItem.addData(LINK_NEW_TAB_KEY, String.valueOf(mOpenNewTabCheckBox.isChecked()));
        }
    }
}
