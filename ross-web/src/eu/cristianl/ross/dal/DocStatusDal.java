package eu.cristianl.ross.dal;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.DocStatusFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.DocStatus;
import eu.cristianl.ross.entities.tables.DocStatusTable;

public class DocStatusDal extends BaseDal<DocStatus> {
	private static DocStatusDal mInstance;

	private DocStatusDal() {
		super(DocStatus.class);
	}

	public static synchronized DocStatusDal I() {
		if (mInstance == null) {
			mInstance = new DocStatusDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
		if (baseFilter instanceof DocStatusFilter) {
			DocStatusFilter filter = (DocStatusFilter) baseFilter;

			if (filter.getDocStatusId() != null) {
				query.addWhereClause(filter, DocStatusTable.ID, filter.getDocStatusId());
			}
		}
	}

	public java.util.List<DocStatus> queryAll() {
		return super.query(null, null, false, 0, 100);
	}

}
