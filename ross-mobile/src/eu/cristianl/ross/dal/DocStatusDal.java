package eu.cristianl.ross.dal;

import java.sql.SQLException;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.entities.DocStatus;
import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.logging.AppLogger;

public class DocStatusDal extends BaseDal<DocStatus, String> {
	private static DocStatusDal mInstance;

	protected DocStatusDal() throws SQLException {
		super(DocStatus.class);
	}

	public static synchronized DocStatusDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new DocStatusDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<DocStatus, String> where, BaseFilter baseFilter) {
		return 0;
	}

	public DocStatus getStatusNew() {
		return getStatus(DocStatusType.NEW.getCode());
	}

	public DocStatus getStatusSubmitted() {
		return getStatus(DocStatusType.SUBMITTED.getCode());
	}

	public DocStatus getStatus(String code) {
		DocStatus retValue = null;
		try {
			retValue = super.get(code);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return retValue;
	}
}
