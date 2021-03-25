package models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Songs {
	private int id ;
	private String name;
	private String preview;
	private String detail;
	private String picture;
	private Timestamp datecreate;
	private int counter;
	private Category category;
	public Songs( String name, String preview, String detail, String picture, Timestamp datecreate, int counter,
			Category category) {
		super();
	
		this.name = name;
		this.preview = preview;
		this.detail = detail;
		this.picture = picture;
		this.datecreate = datecreate;
		this.counter = counter;
		this.category = category;
	}
	public Songs(String name, String preview, String detail, String picture, Category category) {
		super();
		this.name = name;
		this.preview = preview;
		this.detail = detail;
		this.picture = picture;
		this.category = category;
	}

	
}
