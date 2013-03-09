/*
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

package org.dmfs.tasks.model.adapters;

import java.util.TimeZone;

import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;

import android.database.Cursor;


/**
 * A TimezoneFieldAdapter stores time zones in a certain field of a {@link ContentSet}. A time zone is a String that might be <code>null</code> for floating
 * times (like all-day dates).
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class TimezoneFieldAdapter extends FieldAdapter<String>
{

	/**
	 * The field name this adapter uses to store the time zone.
	 */
	private final String mTzFieldName;

	/**
	 * The field name this adapter uses to store the all-day flag.
	 */
	private final String mAllDayFieldName;


	/**
	 * Constructor for a new IntegerFieldAdapter without default value.
	 * 
	 * @param timezoneFieldName
	 *            The name of the field to use when loading or storing the value.
	 */
	public TimezoneFieldAdapter(String timezoneFieldName, String alldayFieldName)
	{
		if (timezoneFieldName == null)
		{
			throw new IllegalArgumentException("fieldName must not be null");
		}
		mTzFieldName = timezoneFieldName;
		mAllDayFieldName = alldayFieldName;
	}


	@Override
	public String get(ContentSet values)
	{
		String timezone = values.getAsString(mTzFieldName);

		if (mAllDayFieldName != null)
		{
			Integer allday = values.getAsInteger(mAllDayFieldName);
			if (allday != null && allday > 0)
			{
				timezone = null;
			}
		}

		return timezone;
	}


	@Override
	public String get(Cursor cursor)
	{
		int tzColumnIdx = cursor.getColumnIndex(mTzFieldName);

		if (tzColumnIdx < 0)
		{
			throw new IllegalArgumentException("The timezone column is missing in cursor.");
		}

		String timezone = cursor.getString(tzColumnIdx);

		if (mAllDayFieldName != null)
		{
			int allDayColumnIdx = cursor.getColumnIndex(mAllDayFieldName);
			if (allDayColumnIdx < 0)
			{
				throw new IllegalArgumentException("The allday column is missing in cursor.");
			}
			if (!cursor.isNull(allDayColumnIdx) && cursor.getInt(allDayColumnIdx) > 0)
			{
				timezone = null;
			}
		}

		return timezone;
	}


	/**
	 * Returns whether this is an "all-day timezone".
	 * 
	 * @param cursor
	 *            The cursor to read from.
	 * @return <code>true</code> if the cursor points to an all-day date.
	 */
	public boolean isAllDay(ContentSet values)
	{
		if (mAllDayFieldName == null)
		{
			return false;
		}

		Integer allday = values.getAsInteger(mAllDayFieldName);
		return allday != null && allday > 0;
	}


	/**
	 * Returns whether this is an "all-day timezone".
	 * 
	 * @param cursor
	 *            The cursor to read from.
	 * @return <code>true</code> if the cursor points to an all-day date.
	 */
	public boolean isAllDay(Cursor cursor)
	{
		if (mAllDayFieldName == null)
		{
			return false;
		}

		int allDayColumnIdx = cursor.getColumnIndex(mAllDayFieldName);
		if (allDayColumnIdx < 0)
		{
			throw new IllegalArgumentException("The allday column is missing in cursor.");
		}
		return !cursor.isNull(allDayColumnIdx) && cursor.getInt(allDayColumnIdx) > 0;
	}


	/**
	 * Returns the local time zone.
	 * 
	 * @return The id of the current time zone.
	 */
	@Override
	public String getDefault(ContentSet values)
	{
		return TimeZone.getDefault().getID();
	}


	@Override
	public void set(ContentSet values, String value)
	{
		values.put(mTzFieldName, value);
	}


	@Override
	public void registerListener(ContentSet values, OnContentChangeListener listener, boolean initalNotification)
	{
		values.addOnChangeListener(listener, mTzFieldName, initalNotification);
		if (mAllDayFieldName != null)
		{
			values.addOnChangeListener(listener, mAllDayFieldName, initalNotification);
		}
	}


	@Override
	public void unregisterListener(ContentSet values, OnContentChangeListener listener)
	{
		values.removeOnChangeListener(listener, mTzFieldName);
		if (mAllDayFieldName != null)
		{
			values.removeOnChangeListener(listener, mAllDayFieldName);
		}
	}
}
