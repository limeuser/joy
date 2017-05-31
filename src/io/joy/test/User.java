package io.joy.test;

import io.joy.orm.Field;
import io.joy.orm.Schema;

public class User extends Schema {
	public final static String myname = "user_info";
	
	@Override
	public Schema define() {
		// 自定义表名
		this.name = myname;
		
		return
		    field(new Field.String("name").maxLength(500))
		   .field(new Field.Integer("age").max(200))
		   .field(new Field.String("email").maxLength(200))
		   .field(new Field.String("address").maxLength(200))
		   .field(new Field.Date("birthday"))
		   .field(new Field.Binary("img").maxSize(50000));
	}
}