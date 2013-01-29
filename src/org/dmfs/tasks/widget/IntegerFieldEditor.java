/*
 * IntegerFieldEditor.java
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

import org.dmfs.tasks.R;
import org.dmfs.tasks.model.FieldDescriptor;
import org.dmfs.tasks.model.adapters.IntegerFieldAdapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Widget to edit Integer values
 * 
 * @author Arjun Naik <arjun@arjunnaik.in>
 * 
 */

public class IntegerFieldEditor extends AbstractFieldEditor implements TextWatcher
{
	private IntegerFieldAdapter mAdapter;
	private EditText mText;


	public IntegerFieldEditor(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}


	public IntegerFieldEditor(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	public IntegerFieldEditor(Context context)
	{
		super(context);
	}


	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		mText = (EditText) findViewById(R.id.text);
	}


	@Override
	public void setup(FieldDescriptor descriptor, Activity context)
	{
		super.setup(descriptor, context);
		mAdapter = (IntegerFieldAdapter) descriptor.getFieldAdapter();
		mText.setHint(descriptor.getHint());
	}


	@Override
	protected void updateView()
	{
		if (mValues != null)
		{
			mText.setText(mAdapter.get(mValues).toString());
		}
	}


	@Override
	public void afterTextChanged(Editable s)
	{
		if (mValues != null)
		{
			mAdapter.set(mValues, Integer.parseInt(mText.getText().toString()));
		}
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
	}

}
