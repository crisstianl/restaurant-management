package eu.cristianl.ross.entities.embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import eu.cristianl.ross.entities.tables.CurriculumVitaeTable;

@Embeddable
public class CurriculumVitae implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = CurriculumVitaeTable.EXPERIENCE, nullable = false)
	@Lob
	private String mExperience;

	@Column(name = CurriculumVitaeTable.EDUCATION, nullable = false)
	@Lob
	private String mEducation;

	@Column(name = CurriculumVitaeTable.LANGUAGES, nullable = false)
	@Lob
	private String mLanguages;

	@Column(name = CurriculumVitaeTable.SKILLS, nullable = false)
	@Lob
	private String mSkills;

	public CurriculumVitae() {
	}

	public String getExperience() {
		return mExperience;
	}

	public void setExperience(String experience) {
		mExperience = experience;
	}

	public String getEducation() {
		return mEducation;
	}

	public void setEducation(String education) {
		mEducation = education;
	}

	public String getLanguages() {
		return mLanguages;
	}

	public void setLanguages(String languages) {
		mLanguages = languages;
	}

	public String getSkills() {
		return mSkills;
	}

	public void setSkills(String skills) {
		mSkills = skills;
	}
}
