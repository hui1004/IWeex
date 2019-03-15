package com.weex.app.view.calendar.format;

import com.appset.weex.costomView.calendar.CalendarDay;

/**
 * Used to formatfor the month/year title
 */
public interface TitleFormatter {

    /**
     * Converts the supplied day to a suitable month/year title
     *
     * @param day the day containing relevant month and year information
     * @return a label to display for the given month/year
     */
    CharSequence format(CalendarDay day);
}
