package learn.spring.dao;

public class JDBCProductDao implements ProductDao {

	@Override
	public long count() {
		return 2;
	}

	
}
