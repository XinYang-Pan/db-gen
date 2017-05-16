package sample.persistent.po;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import io.github.xinyangpan.persistent.dao.po.traceable.TraceablePo;
import javax.persistence.Id;
import io.github.xinyangpan.persistent.dao.po.id.HasId;
import javax.persistence.Table;
import io.github.xinyangpan.persistent.dao.po.activeable.ActiveablePo;
import sample.persistent.enums.PersonSex;

@Entity
@Table(name = "TBL_PERSON")
@SuppressWarnings("serial")
public class TblPerson implements Serializable, TraceablePo<Long>, ActiveablePo, HasId<Long> {

	private Long id;

	private String name;

	private Integer age;

	private PersonSex sex;

	private Long createId;

	private Long updateId;

	private Date createTime;

	private Date updateTime;

	private Boolean activeFlag;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID")
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	@Column(name = "NAME")
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AGE")
	public Integer getAge() {
		return this.age;
	}

	@Column(name = "AGE")
	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "SEX")
	@Enumerated(EnumType.STRING)
	public PersonSex getSex() {
		return this.sex;
	}

	@Column(name = "SEX")
	@Enumerated(EnumType.STRING)
	public void setSex(PersonSex sex) {
		this.sex = sex;
	}

	@Column(name = "CREATE_ID")
	public Long getCreateId() {
		return this.createId;
	}

	@Column(name = "CREATE_ID")
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Column(name = "UPDATE_ID")
	public Long getUpdateId() {
		return this.updateId;
	}

	@Column(name = "UPDATE_ID")
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	@Column(name = "CREATE_TIME")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	@Column(name = "UPDATE_TIME")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "ACTIVE_FLAG")
	public Boolean getActiveFlag() {
		return this.activeFlag;
	}

	@Column(name = "ACTIVE_FLAG")
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TblPerson [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", age=");
		builder.append(age);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", createId=");
		builder.append(createId);
		builder.append(", updateId=");
		builder.append(updateId);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", activeFlag=");
		builder.append(activeFlag);
		builder.append("]");
		return builder.toString();
	}

}