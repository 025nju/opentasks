/*
 * BaseTaskView.java
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

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class BaseTaskView extends LinearLayout {

	private Activity mContext;

    public BaseTaskView(Context context) {
	super(context);
    }

    public BaseTaskView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public BaseTaskView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }

    protected Activity getActivity()
    {
    	return mContext;
    }
    
	public void setActivity(Activity context)
	{
		mContext = context;
	}

}
