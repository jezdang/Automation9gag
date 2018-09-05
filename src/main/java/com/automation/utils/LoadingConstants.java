package com.automation.utils;

/**
 * Constants define the expected loading timeout of components. For complex components, we could compare to standard
 * component, then we may multiple the timeout.
 */
public final class LoadingConstants {
	private LoadingConstants() {
		// to hide constructor
	}

	// Please never update FRAME_TIMEOUT for any reason! Some tests are failed randomly is even better than a job
	// timeout. The application may have problem if 1 screen loads > 1 minute. But we should live temporarily with it
	// now. Further investigation will be done whenever it's possible.
	public static final long FRAME_TIMEOUT = System.getProperty("groups") == null ? 300l : 30l;
	public static final long POPUP_TIMEOUT = 120l;
	public static final long PRINTING_TIMEOUT_120 = 300l;
	public static final long POPUP_CLOSED_30 = 30l;
	public static final long RENDER_ELEMENT = 60l;
	public static final long RENDER_ELEMENT_LONG_TIMEOUT = 120l;
	public static final long RENDER_EVENT_CALENDAR = 60l;
	public static final long DWR_MASK_PRESENCE_TIMEOUT = 120l; // s
	public static final long SEARCH_TIMEOUT = 120l; // s
	public static final long PROGRESS_MASK_TIMEOUT = 300l;
	public static final long SEAT_LOADING_TIMEOUT = 120l;
	public static final long UPDATE_BUTTON = 60l;
	public static final long PROCESS_PAYMENT = 90l;

	// mainIndexPage
	public static final long CONTEXT_SET_TIMEOUT = 10l;
	public static final long LOADING_TIMEOUT = 120l;
	public static final long LOADING_POLLING = 2l;
	public static final long MENU_EXPAND_TIMEOUT = 20l;

	// menu entries are loaded slowly at the first time.
	public static final long CLICK_MENU_TIMEOUT = 60l;

	public static final long TICKETS_PAGE_CACHE_TIMEOUT = 100l;
	
	public static final long SELECTED_SEAT_TIMEOUT = 60l;
	
	public static final long RELOADING_TIMEOUT = 120l;
	
	public static final long LOADING_POLLING_COMBO = 3l;
}
