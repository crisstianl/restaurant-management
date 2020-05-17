package eu.cristianl.ross.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import eu.cristianl.ross.entities.tables.CurrencyTable;

@Entity
@DiscriminatorValue(value = CurrencyTable.TABLE_NAME)
public class Currency extends BaseCurrency {
	private static final long serialVersionUID = 1L;

	@Column(name = CurrencyTable.CONV_FACTOR)
	private float mConvFactor = 1.0F;

	public Currency() {
	}

	public float getConvFactor() {
		return mConvFactor;
	}

	public void setConvFactor(float convFactor) {
		mConvFactor = convFactor;
	}

}
