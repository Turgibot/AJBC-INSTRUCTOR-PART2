package ajbc.learn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"picture"})
public class Category {

	private int categoryId;
	private String categoryName;
	private String description;
	private byte[] picture;
	
	
}
