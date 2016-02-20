package yaes.framework.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimeLine {
	private class AddComparator implements Comparator<TimePoint> {
		public AddComparator() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int compare(TimePoint o1, TimePoint o2) {
			return Double.compare(o1.timePoint, o2.timePoint);
		}
	}

	private class TimePoint {
		int freeQuantity;
		int timePoint;

		TimePoint(int timePoint, int freeQuantity) {
			this.timePoint = timePoint;
			this.freeQuantity = freeQuantity;
		}

		@Override
		public String toString() {
			final String str = "tp : " + timePoint + " q : " + freeQuantity;
			return str;
		}
	}

	private final AddComparator addComparator = new AddComparator();

	private final ArrayList<TimePoint> allocations = new ArrayList<>();

	private int totalQuantity;

	public TimeLine(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public boolean accept(int startTime, int endTime, int quantity) {
		final TimePoint seekPoint = new TimePoint(startTime, quantity);
		int pos = Collections.binarySearch(allocations, seekPoint,
				addComparator);
		if (pos < 0) {
			pos = -pos - 1;
			if (pos > 0) {
				pos--;
			}
		}
		// System.err.println("\n --> "+startTime+" "+ endTime+" "+ quantity+"
		// pos "+pos);
		TimePoint tp;
		int lastPos = pos;
		if (allocations.size() == 0) {
			tp = new TimePoint(startTime, totalQuantity);
			allocations.add(tp);
		} else {
			if ((pos == 0) && (allocations.get(pos).timePoint > startTime)) {
				if (allocations.get(pos).freeQuantity == totalQuantity) {
					allocations.get(pos).timePoint = startTime;
				} else {
					tp = new TimePoint(startTime, totalQuantity);
					allocations.add(0, tp);
				}
			}
		}
		for (int i = pos; i < allocations.size(); i++) {
			tp = allocations.get(i);
			if (tp.timePoint >= endTime) {
				break;
			}
			// System.err.println(" --> "+i+" -- "+tp);
			if (tp.freeQuantity < quantity) {
				return false;
			}
			lastPos = i;
		}
		// insert first segment if necessary
		tp = allocations.get(pos);
		if (tp.timePoint < startTime) {
			tp = new TimePoint(startTime, tp.freeQuantity);
			allocations.add(pos + 1, tp);
			pos++;
			lastPos++;
		}
		// insert last segment if necessary
		if (lastPos < allocations.size() - 1) {
			if (allocations.get(lastPos + 1).timePoint > endTime) {
				tp = allocations.get(lastPos);
				tp = new TimePoint(endTime, tp.freeQuantity);
				allocations.add(lastPos + 1, tp);
			}
		} else {
			tp = new TimePoint(endTime, totalQuantity);
			allocations.add(tp);
		}
		for (int i = pos; i < allocations.size(); i++) {
			tp = allocations.get(i);
			if (tp.timePoint >= endTime) {
				break;
			}
			tp.freeQuantity -= quantity;
			// System.err.println(" adj --> "+i+" -- "+tp);
		}
		// for(int i = 0; i < allocations.size(); i++)
		// System.err.println(" end --> "+i+" -- "+allocations.get(i));
		return true;
	}

	public void addTotalQuantity(int totalQuantity) {
		this.totalQuantity += totalQuantity;
	}

	public boolean check(int startTime, int endTime, int quantity) {
		final TimePoint seekPoint = new TimePoint(startTime, quantity);
		int pos = Collections.binarySearch(allocations, seekPoint,
				addComparator);
		if (pos < 0) {
			pos = -pos - 1;
			if (pos > 0) {
				pos--;
			}
		}
		// System.err.println("\n --> "+startTime+" "+ endTime+" "+ quantity+"
		// pos "+pos);
		TimePoint tp;
		if (allocations.size() == 0) {
			if (quantity <= totalQuantity) {
				return true;
			}
			return false;
		}
		for (int i = pos; i < allocations.size(); i++) {
			tp = allocations.get(i);
			if ((tp.timePoint >= endTime) && (totalQuantity >= quantity)) {
				return true;
			}
			if (tp.freeQuantity < quantity) {
				return false;
			}
		}
		// for(int i = 0; i < allocations.size(); i++)
		// System.err.println(" end --> "+i+" -- "+allocations.get(i));
		return true;
	}

	public int freeQuantityAtTime(int startTime) {
		if (allocations.size() == 0) {
			return totalQuantity;
		}
		final TimePoint seekPoint = new TimePoint(startTime, 0);
		int pos = Collections.binarySearch(allocations, seekPoint,
				addComparator);
		// System.out.println(" pos " + pos);
		if (pos < 0) {
			pos = -pos - 1;
			if (pos == 0) {
				return totalQuantity;
			}
			if (pos > 0) {
				pos--;
			}
		}
		final TimePoint tp = allocations.get(pos);
		return tp.freeQuantity;
	}

	public List<Integer> getTimePoints() {
		final ArrayList<Integer> retval = new ArrayList<>();
		for (final TimePoint tp : allocations) {
			retval.add(tp.timePoint);
		}
		return retval;
	}

	// FIXME: This is incorrect, because of the case when it is before any
	// allocations...
	// try accept 40, 50, 100 followed by 40, 50, 100.
	public void release(int startTime, int endTime, int quantity) {
		// for(int i = 0; i < allocations.size(); i++)
		// System.err.println(" start --> "+i+" -- "+allocations.get(i));
		final TimePoint seekPoint = new TimePoint(endTime, quantity);
		int pos = Collections.binarySearch(allocations, seekPoint,
				addComparator);
		if (pos < 0) {
			pos = -pos - 1;
			if (pos > 0) {
				pos--;
			}
		}
		// System.err.println(" POS --> "+pos);
		int previousPos = pos;
		pos--;
		TimePoint previousPoint = allocations.get(previousPos);
		TimePoint tp;
		for (; pos >= 0; pos--) {
			tp = allocations.get(pos);
			// System.err.println(" --> "+i+" -- "+tp);
			tp.freeQuantity += quantity;
			if (tp.freeQuantity == previousPoint.freeQuantity) {
				allocations.remove(previousPos);
			}
			previousPos = pos;
			previousPoint = tp;
			if (tp.timePoint == startTime) {
				pos--;
				if (pos >= 0) {
					tp = allocations.get(pos);
					if (tp.freeQuantity == previousPoint.freeQuantity) {
						allocations.remove(previousPos);
						previousPos = pos;
					}
				}
				break;
			}
		}
		// for(int i = 0; i < allocations.size(); i++)
		// System.err.println(" end --> "+i+" -- "+allocations.get(i));
	}
}
