package ch.rallo.mfgt.widget.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.ContactItem;

public class ContactItemsAdapter extends ArrayAdapter<ContactItem> {

	public ContactItemsAdapter(Context context) {
		super(context, android.R.layout.simple_spinner_item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, null);

			holder = new ViewHolder();

			holder.icon = (ImageView) convertView.findViewById(R.id.contactItemIcon);
			holder.label = (TextView) convertView.findViewById(R.id.contactItemLabel);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ContactItem item = getItem(position);

		holder.icon.setImageDrawable(getContext().getResources().getDrawable(item.getIconId()));
		holder.label.setText(item.getLabel());

		if (item.getClickListener() != null) {
			convertView.setOnClickListener(item.getClickListener());
			convertView.setClickable(true);
		}

		return convertView;
	}

	private static class ViewHolder {
		ImageView icon;
		TextView label;
	}
}
