package eu.cristianl.ross.android.listeners;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public abstract class OnSwipeTouchListener implements View.OnTouchListener {
	private final GestureDetector m_GestureListener;

	public OnSwipeTouchListener() {
		m_GestureListener = new GestureDetector(new GestureListener());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return m_GestureListener.onTouchEvent(event);
	}

	public boolean onTouchEvent(MotionEvent event) {
		return m_GestureListener.onTouchEvent(event);
	}

	private final class GestureListener extends SimpleOnGestureListener {
		private static final int SWIPE_THRESHOLD = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float diffY = e2.getY() - e1.getY();
			float diffX = e2.getX() - e1.getX();
			if (Math.abs(diffX) > Math.abs(diffY)) {
				if (Math.abs(diffX) > SWIPE_THRESHOLD
						&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
					if (diffX > 0) {
						return onSwipeLeftRight();
					} else {
						return onSwipeRightLeft();
					}
				}
			} else {
				if (Math.abs(diffY) > SWIPE_THRESHOLD
						&& Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
					if (diffY > 0) {
						return onSwipeTopBottom();
					} else {
						return onSwipeBottomTop();
					}
				}
			}
			return false;
		}
	}

	/** True if the event is consumed, false otherwise */
	public abstract boolean onSwipeLeftRight();

	/** True if the event is consumed, false otherwise */
	public abstract boolean onSwipeRightLeft();

	/** True if the event is consumed, false otherwise */
	public abstract boolean onSwipeBottomTop();

	/** True if the event is consumed, false otherwise */
	public abstract boolean onSwipeTopBottom();

}
