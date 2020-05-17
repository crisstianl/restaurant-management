package eu.cristianl.ross.dal.iterators;

import java.util.Iterator;

import com.j256.ormlite.dao.CloseableIterator;

public class ResultsIterator<T> implements Iterator<T>, Iterable<T> {

	private CloseableIterator<T> mOrmIterator;

	public ResultsIterator(final CloseableIterator<T> ormIterator) {
		mOrmIterator = ormIterator;
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}

	@Override
	public synchronized boolean hasNext() {
		return mOrmIterator.hasNext();
	}

	@Override
	public synchronized T next() {
		return mOrmIterator.next();
	}

	@Override
	public synchronized void remove() {
		mOrmIterator.remove();

	}

	public synchronized void close() {
		try {
			if (mOrmIterator != null) {
				mOrmIterator.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mOrmIterator = null;
		}
	}
}
