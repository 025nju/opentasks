/*
 * TaskView.java
 *
 * Copyright (C) 2012 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.tasks.widget;

import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.FieldDescriptor;
import org.dmfs.tasks.model.Model;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;


public class TaskView extends BaseTaskView
{

	private ContentSet mValues;
	private Model mModel;


	public TaskView(Context context)
	{
		super(context);
	}


	public TaskView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	public TaskView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}


	@Override
	protected void onFinishInflate()
	{
	}


	public void setModel(Model model)
	{
		mModel = model;
	}


	public void setValues(ContentSet values)
	{
		mValues = values;
		updateView();

	}


	private void updateView()
	{
		final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (FieldDescriptor field : mModel.getFields())
		{
			Log.v("------------------------", field.getTitle());
			AbstractFieldView detailView = field.getDetailView(inflater);
			if (detailView != null)
			{
				detailView.setup(field, getActivity());
				detailView.setValue(mValues);
				this.addView(detailView);
			}
		}
	}

}
