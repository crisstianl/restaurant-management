package eu.cristianl.ross.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import eu.cristianl.ross.entities.tables.WorkerTable;

@Entity
@Table(name = WorkerTable.TABLE_NAME)
public class Worker extends Employee {
	private static final long serialVersionUID = 1L;

	public Worker() {
	}
}
