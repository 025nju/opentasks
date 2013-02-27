/*
 * TextFieldEditor.java
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
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.FieldDescriptor;
import org.dmfs.tasks.model.adapters.StringFieldAdapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Editor Field for simple text.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */

public class TextFieldEditor extends AbstractFieldEditor implements TextWatcher
{
	private final static String TAG = "TextFieldEditor";

	private StringFieldAdapter mAdapter;
	private TextView mTitle;
	private EditText mText;


	public TextFieldEditor(Context context)
	{
		super(context);
	}


	public TextFieldEditor(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	public TextFieldEditor(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}


	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		mText = (EditText) findViewById(R.id.text);
		mText.addTextChangedListener(this);
	}


	@Override
	public void setup(FieldDescriptor descriptor, Activity context)
	{
		super.setup(descriptor, context);
		mAdapter = (StringFieldAdapter) descriptor.getFieldAdapter();
		mText.setHint(descriptor.getHint());
	}


	@Override
	public void afterTextChanged(Editable s)
	{
		if (mValues != null)
		{
			mAdapter.set(mValues, mText.getText().toString());
			Log.v(TAG, "updated values");
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


	@Override
	public void onContentChanged(ContentSet contentSet, String key)
	{
		if (key != null)
		{
			if (mValues != null)
			{
				String oldValue = mText.getText().toString();
				String newValue = mAdapter.get(mValues);
				if (!TextUtils.equals(oldValue, newValue))
				{
					mText.setText(newValue);
				}
			}
		}
		else
		{
			if (mValues != null)
			{
				mText.setText(mAdapter.get(mValues));
			}
		}
	}

}
