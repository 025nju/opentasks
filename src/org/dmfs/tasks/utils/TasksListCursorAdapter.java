/*
 * Copyright (C) 2014 Marten Gajda <marten@dmfs.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.dmfs.tasks.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.dmfs.provider.tasks.TaskContract;
import org.dmfs.tasks.R;
import org.dmfs.tasks.model.TaskList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * An adapter to adapt a cursor containing task lists to a {@link Spinner}.
 * 
 * @author Tobias Reinsch <tobias@dmfs.org>
 */
public class TasksListCursorAdapter extends android.support.v4.widget.CursorAdapter
{
	LayoutInflater mInflater;

	private int mTaskColorColumn;
	private int mTaskNameColumn;
	private int mAccountNameColumn;
	private int mIdColumn;
	private Map<Long, TaskList> mSelectedLists = new HashMap<Long, TaskList>();

	private SelectionEnabledListener mListener;


	public TasksListCursorAdapter(Context context)
	{
		super(context, null, 0 /* don't register a content observer to avoid a context leak! */);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	public void setSelectionEnabledListener(SelectionEnabledListener listener)
	{
		mListener = listener;
	}


	@Override
	public Cursor swapCursor(Cursor c)
	{
		Cursor result = super.swapCursor(c);
		if (c != null)
		{
			mIdColumn = c.getColumnIndex(TaskContract.TaskListColumns._ID);
			mTaskColorColumn = c.getColumnIndex(TaskContract.TaskListColumns.LIST_COLOR);
			mTaskNameColumn = c.getColumnIndex(TaskContract.TaskListColumns.LIST_NAME);
			mAccountNameColumn = c.getColumnIndex(TaskContract.TaskListSyncColumns.ACCOUNT_NAME);
		}
		return result;
	}


	@Override
	public void bindView(View v, Context context, Cursor c)
	{
		/* Since we override getView and get DropDownView we don't need this method. */
	}


	@Override
	public View newView(Context context, Cursor c, ViewGroup vg)
	{
		/* Since we override getView and get DropDownView we don't need this method. */
		return null;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_item_selection, null);
		}

		TextView tvListName = (TextView) convertView.findViewById(android.R.id.text1);
		TextView tvAccountName = (TextView) convertView.findViewById(android.R.id.text2);
		CheckBox cBox = (CheckBox) convertView.findViewById(android.R.id.checkbox);
		View colorView = convertView.findViewById(R.id.color_view);
		Cursor cursor = (Cursor) getItem(position);

		final String listName = cursor.getString(mTaskNameColumn);
		final String accountName = cursor.getString(mAccountNameColumn);
		final Long id = cursor.getLong(mIdColumn);

		tvListName.setText(listName);
		tvAccountName.setText(accountName);
		int taskListColor = cursor.getInt(mTaskColorColumn);

		((GradientDrawable) colorView.getBackground()).setColor(taskListColor);

		// listen for checkbox
		cBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{

				int oldSize = mSelectedLists.size();

				if (isChecked)
				{
					TaskList taskList = mSelectedLists.get(id);
					if (taskList == null)
					{
						taskList = new TaskList();
						taskList.accountName = accountName;
						taskList.listName = listName;
						mSelectedLists.put(id, taskList);
					}

				}
				else
				{
					mSelectedLists.remove(id);
				}

				if (mListener != null)
				{
					if (oldSize == 0 && mSelectedLists.size() > 0)
					{
						mListener.onSelectionEnabled();
					}
					if (oldSize > 0 && mSelectedLists.size() == 0)
					{
						mListener.onSelectionDisabled();

					}
				}

			}
		});
		return convertView;
	}


	public Collection<TaskList> getSelectedLists()
	{
		return mSelectedLists.values();
	}

	/**
	 * Listener that is used to notify if the select item count is > 0 or equal 0.
	 * 
	 * @author Tobias Reinsch <tobias@dmfs.org>
	 * 
	 */
	public interface SelectionEnabledListener
	{
		public void onSelectionEnabled();


		public void onSelectionDisabled();
	}
}
