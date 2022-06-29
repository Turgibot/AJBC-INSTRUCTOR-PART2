package ajbc.learn.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(exclude = { "picture" })
@NoArgsConstructor
@Getter
@Setter
@Table(name = "categories")
public class Category {

	@Id
//	@Column(insertable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
//	@Column(name="category_name")
	private String categoryName;
	private String description;
	@JsonIgnore
	private byte[] picture;
}
