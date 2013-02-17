/*
 * DefaultModel.java
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

package org.dmfs.tasks.model;

import org.dmfs.tasks.R;
import org.dmfs.tasks.model.adapters.IntegerFieldAdapter;
import org.dmfs.tasks.model.adapters.StringFieldAdapter;
import org.dmfs.tasks.model.adapters.TimeFieldAdapter;
import org.dmfs.tasks.model.layout.LayoutDescriptor;
import org.dmfs.provider.tasks.TaskContract.Tasks;

import android.content.Context;
import android.content.res.Resources;


/**
 * The default model for sync adapters that don't provide a model definition.
 * 
 * TODO: support only a very simple task model
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class DefaultModel extends Model
{

	private final Context mContext;
	private final static LayoutDescriptor TEXT_VIEW = new LayoutDescriptor(R.layout.text_field_view);
	private final static LayoutDescriptor TEXT_EDIT = new LayoutDescriptor(R.layout.text_field_editor);
	private final static LayoutDescriptor INTEGER_VIEW = new LayoutDescriptor(R.layout.integer_field_view);
	private final static LayoutDescriptor INTEGER_EDIT = new LayoutDescriptor(R.layout.integer_field_editor);
	private final static LayoutDescriptor TIME_VIEW = new LayoutDescriptor(R.layout.time_field_view);
	private final static LayoutDescriptor TIME_EDIT = new LayoutDescriptor(R.layout.time_field_editor);


	public DefaultModel(Context context)
	{
		mContext = context;
	}


	@Override
	public void inflate()
	{
		if (inflated)
		{
			return;
		}

		mFields
			.add(new FieldDescriptor(mContext, R.string.task_title, new StringFieldAdapter(Tasks.TITLE)).setViewLayout(TEXT_VIEW).setEditorLayout(TEXT_EDIT));

		Resources res = mContext.getResources();

		ArrayChoicesAdapter aca = new ArrayChoicesAdapter();
		aca.addChoice(Tasks.STATUS_NEEDS_ACTION, mContext.getString(R.string.status_needs_action), res.getDrawable(R.drawable.ic_status_needs_action));
		aca.addChoice(Tasks.STATUS_IN_PROCESS, mContext.getString(R.string.status_in_process), res.getDrawable(R.drawable.ic_status_in_process));
		aca.addChoice(Tasks.STATUS_COMPLETED, mContext.getString(R.string.status_completed), res.getDrawable(R.drawable.ic_status_completed));
		aca.addChoice(Tasks.STATUS_CANCELLED, mContext.getString(R.string.status_cancelled), res.getDrawable(R.drawable.ic_status_cancelled));

		mFields.add(new FieldDescriptor(mContext, R.string.task_status, new IntegerFieldAdapter(Tasks.STATUS)).setViewLayout(INTEGER_VIEW)
			.setEditorLayout(INTEGER_EDIT).setChoices(aca));

		mFields.add(new FieldDescriptor(mContext, R.string.task_location, new StringFieldAdapter(Tasks.LOCATION)).setViewLayout(TEXT_VIEW).setEditorLayout(
			TEXT_EDIT));
		mFields.add(new FieldDescriptor(mContext, R.string.task_description, new StringFieldAdapter(Tasks.DESCRIPTION)).setViewLayout(TEXT_VIEW)
			.setEditorLayout(TEXT_EDIT));

		mFields.add(new FieldDescriptor(mContext, R.string.task_start, new TimeFieldAdapter(Tasks.DTSTART, Tasks.TZ, Tasks.IS_ALLDAY)).setViewLayout(TIME_VIEW)
			.setEditorLayout(TIME_EDIT));
		mFields.add(new FieldDescriptor(mContext, R.string.task_due, new TimeFieldAdapter(Tasks.DUE, Tasks.TZ, Tasks.IS_ALLDAY)).setViewLayout(TIME_VIEW)
			.setEditorLayout(TIME_EDIT));
		mFields.add(new FieldDescriptor(mContext, R.string.task_completed, new TimeFieldAdapter(Tasks.COMPLETED, Tasks.TZ, Tasks.COMPLETED_IS_ALLDAY))
			.setViewLayout(TIME_VIEW).setEditorLayout(TIME_EDIT));

		ArrayChoicesAdapter aca2 = new ArrayChoicesAdapter();
		aca2.addChoice(0, mContext.getString(R.string.priority_undefined), null);
		aca2.addChoice(9, mContext.getString(R.string.priority_low), null);
		aca2.addHiddenChoice(8, mContext.getString(R.string.priority_low), null);
		aca2.addHiddenChoice(7, mContext.getString(R.string.priority_low), null);
		aca2.addHiddenChoice(6, mContext.getString(R.string.priority_low), null);
		aca2.addChoice(5, mContext.getString(R.string.priority_medium), null);
		aca2.addHiddenChoice(4, mContext.getString(R.string.priority_high), null);
		aca2.addHiddenChoice(3, mContext.getString(R.string.priority_high), null);
		aca2.addHiddenChoice(2, mContext.getString(R.string.priority_high), null);
		aca2.addChoice(1, mContext.getString(R.string.priority_high), null);

		mFields.add(new FieldDescriptor(mContext, R.string.task_priority, new IntegerFieldAdapter(Tasks.PRIORITY)).setViewLayout(INTEGER_VIEW)
			.setEditorLayout(INTEGER_EDIT).setChoices(aca2));

		ArrayChoicesAdapter aca3 = new ArrayChoicesAdapter();
		aca3.addChoice(Tasks.CLASSIFICATION_PUBLIC, mContext.getString(R.string.classification_public), null);
		aca3.addChoice(Tasks.CLASSIFICATION_PRIVATE, mContext.getString(R.string.classification_private), null);
		aca3.addChoice(Tasks.CLASSIFICATION_CONFIDENTIAL, mContext.getString(R.string.classification_confidential), null);

		mFields.add(new FieldDescriptor(mContext, R.string.task_classification, new IntegerFieldAdapter(Tasks.CLASSIFICATION)).setViewLayout(INTEGER_VIEW)
			.setEditorLayout(INTEGER_EDIT).setChoices(aca3));

		setAllowRecurrence(false);
		setAllowExceptions(false);

		inflated = true;
	}
}
