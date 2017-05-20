package sample.persistent.po;

import javax.persistence.Enumerated;
import sample.persistent.enums.PersonSex;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Table;
import io.github.xinyangpan.persistent.dao.po.traceable.TraceablePo;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.EnumType;
import io.github.xinyangpan.persistent.dao.po.id.HasId;
import io.github.xinyangpan.persistent.dao.po.activeable.ActiveablePo;

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

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AGE")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "SEX")
	@Enumerated(EnumType.STRING)
	public PersonSex getSex() {
		return this.sex;
	}

	public void setSex(PersonSex sex) {
		this.sex = sex;
	}

	@Column(name = "CREATE_ID")
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Column(name = "UPDATE_ID")
	public Long getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "ACTIVE_FLAG")
	public Boolean getActiveFlag() {
		return this.activeFlag;
	}

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