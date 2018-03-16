package jr.h4ngout;

import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
//import com.codingtrickshub.checkboxlistview.R;
//import com.codingtrickshub.checkboxlistview.model.Category;
//import com.codingtrickshub.checkboxlistview.serverCalls.FavouriteCategoriesJsonParser;

/**
 * Created by jrfif on 19/02/2018.
 */

public class UserAdapter extends ArrayAdapter<User> {
    private final List<User> list;

    public UserAdapter(Context context, int resource, List<User> list) {
        super(context, resource, list);
        this.list = list;
    }

    static class ViewHolder {
        protected TextView categoryName;
        protected CheckBox categoryCheckBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.row_user, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.row_categoryname_textview);
            viewHolder.categoryCheckBox = (CheckBox) convertView.findViewById(R.id.row_category_checkbox);
            viewHolder.categoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    list.get(getPosition).setSelected(buttonView.isChecked());
                    if (buttonView.isChecked()) {
                        if (!UsersJsonParser.selectedCategories.contains(String.valueOf(list.get(getPosition).getCateogry_id()))) {
                            UsersJsonParser.selectedCategories.add(String.valueOf(list.get(getPosition).getCateogry_id()));
                        }
                    } else {
                        if (UsersJsonParser.selectedCategories.contains(String.valueOf(list.get(getPosition).getCateogry_id()))) {
                            UsersJsonParser.selectedCategories.remove(String.valueOf(list.get(getPosition).getCateogry_id()));
                        }
                    }
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.row_categoryname_textview, viewHolder.categoryName);
            convertView.setTag(R.id.row_category_checkbox, viewHolder.categoryCheckBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.categoryCheckBox.setTag(position);
        viewHolder.categoryName.setText(list.get(position).getCategory_Name());
        viewHolder.categoryCheckBox.setChecked(list.get(position).isSelected());

        return convertView;
    }
}

