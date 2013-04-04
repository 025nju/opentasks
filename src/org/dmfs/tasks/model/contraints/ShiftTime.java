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

package org.dmfs.tasks.model.contraints;

import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.adapters.TimeFieldAdapter;

import android.text.format.Time;


/**
 * Shift a reference time by the same amount that value has been shifted.
 * 
 * TODO: this won't work properly until ContentSet supports some kind of transactions.
 * 
 * TODO: use Duration class to get the duration in days and shift without summer/winter time switches
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class ShiftTime extends AbstractConstraint<Time>
{
	private final TimeFieldAdapter mTimeAdapter;


	public ShiftTime(TimeFieldAdapter adapter)
	{
		mTimeAdapter = adapter;
	}


	@Override
	public void apply(ContentSet currentValues, Time oldValue, Time newValue)
	{
		Time timeToShift = mTimeAdapter.get(currentValues);
		if (timeToShift != null && newValue != null && oldValue != null)
		{
			timeToShift.set(timeToShift.toMillis(false) + (newValue.toMillis(false) - oldValue.toMillis(false)));
			mTimeAdapter.set(currentValues, timeToShift);
		}
	}

}
