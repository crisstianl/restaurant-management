package eu.cristianl.ross.web.jsf.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import eu.cristianl.ross.dal.JobDal;
import eu.cristianl.ross.entities.Job;
import eu.cristianl.ross.utils.Utils;
import eu.cristianl.ross.web.jsf.utils.FacesUtils;

@ManagedBean(name = JobsBean.NAME)
@ViewScoped
public class JobsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "JobsBean";

	// data
	private long mCount = 0;
	private List<Job> mJobs = null;

	public JobsBean() {
	}

	@PostConstruct
	public void init() {
		mCount = JobDal.I().totalJobs();
		mJobs = JobDal.I().queryAll();
	}

	@PreDestroy
	public void destroy() {
		mJobs.clear();
		mJobs = null;
	}

	public List<Job> getJobs() {
		return mJobs;
	}

	public Job getJobById(Integer id) {
		for (Job job : mJobs) {
			if (id.equals(job.getId())) {
				return job;
			}
		}
		return null;
	}

	public Job getJobByName(String name) {
		for (Job job : mJobs) {
			if (job.getTitle().equals(name)) {
				return job;
			}
		}
		return null;
	}

	@FacesConverter(value = JobConverter.CONVERTER_NAME, forClass = Job.class)
	public static class JobConverter implements Converter {
		public static final String CONVERTER_NAME = "JobConverter";

		@Override
		public Job getAsObject(FacesContext context, UIComponent view, String value) throws ConverterException {
			int id = Utils.tryReadInt(value, -1);
			if (id >= 0) {
				JobsBean bean = FacesUtils.getBean(JobsBean.NAME);
				return bean.getJobById(id);
			}
			return null;
		}

		@Override
		public String getAsString(FacesContext context, UIComponent view, Object value) throws ConverterException {
			if (value instanceof Integer) {
				value.toString();
			}
			return null;
		}
	}
}