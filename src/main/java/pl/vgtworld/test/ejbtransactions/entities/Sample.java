package pl.vgtworld.test.ejbtransactions.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "samples")
@NamedQueries(
	  @NamedQuery(name = Sample.QUERY_FIND_BY_TYPE, query = "SELECT s FROM samples s WHERE s.type = :TYPE")
)
public class Sample {

	public static final String QUERY_FIND_BY_TYPE = "Sample.findByType";

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "sample_type", nullable = false)
	private Integer type;

	@Column(name = "sample_value", nullable = false)
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
