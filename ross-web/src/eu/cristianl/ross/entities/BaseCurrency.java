package eu.cristianl.ross.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import eu.cristianl.ross.entities.tables.BaseCurrencyTable;

@Entity
@Table(name = BaseCurrencyTable.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = BaseCurrencyTable.CURRENCY_TYPE, discriminatorType = DiscriminatorType.STRING, length = 20)
@DiscriminatorValue(value = BaseCurrencyTable.TABLE_NAME)
public class BaseCurrency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = BaseCurrencyTable.ID, length = 3)
	private String mId;

	@Column(name = BaseCurrencyTable.NAME, nullable = false, length = 20)
	private String mName;

	public BaseCurrency() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BaseCurrency) {
			return mId == ((BaseCurrency) obj).getId();
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%1$d-%2$s", mId, mName);
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

}
