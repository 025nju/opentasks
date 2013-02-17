/*
 * ExpandableGroupDescriptor.java
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

package org.dmfs.tasks.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;


/**
 * A descriptor that knows how to load and present grouped data.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class ExpandableGroupDescriptor
{
	private final Uri mUri;
	private final String[] mProjection;
	private final String mSelection;
	private final String[] mSelectionArgs;
	private final String mSortOrder;
	private final ExpandableChildDescriptor mChildDescriptor;
	private ViewDescriptor mGroupViewDescriptor;
	private int mTitle = -1;
	private int mDrawable = -1;


	public ExpandableGroupDescriptor(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,
		ExpandableChildDescriptor childDescriptor)
	{
		mUri = uri;
		mProjection = projection;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mSortOrder = sortOrder;
		mChildDescriptor = childDescriptor;
	}


	public CursorLoader getGroupCursorLoader(Context context)
	{
		return new CursorLoader(context, mUri, mProjection, mSelection, mSelectionArgs, mSortOrder);
	}


	public CursorLoader getChildCursorLoader(Context context, Cursor cursor)
	{
		return mChildDescriptor.getCursorLoader(context, cursor);
	}


	public ExpandableGroupDescriptor setViewDescriptor(ViewDescriptor descriptor)
	{
		mGroupViewDescriptor = descriptor;
		return this;
	}


	public ViewDescriptor getGroupViewDescriptor()
	{
		return mGroupViewDescriptor;
	}


	public ViewDescriptor getElementViewDescriptor()
	{
		return mChildDescriptor.getViewDescriptor();
	}


	public ExpandableGroupDescriptor setTitle(int res)
	{
		mTitle = res;
		return this;
	}


	/**
	 * Get the resource id of a resource that contains a name for this grouping.
	 * 
	 * @return A resource id.
	 */
	public int getTitle()
	{
		return mTitle;
	}


	public ExpandableGroupDescriptor setDrawabe(int res)
	{
		mDrawable = res;
		return this;
	}


	public int getDrawable()
	{
		return mDrawable;
	}

}
