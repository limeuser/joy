package io.joy.orm;

public abstract class Field<T> {
	private T defaultValue;
	private java.lang.String name;
	private boolean nullable = true;
	private boolean isPrimaryKey = false;
	
	public Field(java.lang.String name) {
		this.name = name;
	}
	
	public Field<T> primaryKey() {
		this.isPrimaryKey = true;
		return this;
	}
	
	public Field<T> defaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
	public Field<T> notNullable() {
		this.nullable = false;
		return this;
	}
	
	public java.lang.String name() {
		return this.name;
	}
	
	public boolean isPrimaryKey() {
		return this.isPrimaryKey;
	}
	
	public T defaultValue() {
		return this.defaultValue;
	}
	
	public boolean isNullable() {
		return this.nullable;
	}
	
	public abstract java.lang.String check(T t);
	
	public static class Integer extends Field<Long> {
		private long min = 0;
		private long max = 100000000;
		
		public Integer(java.lang.String name) {
			super(name);
		}
		
		public Integer min(long min) {
			this.min = min;
			return this;
		}
		
		public Integer max(long max) {
			this.max = max;
			return this;
		}
		
		public long min() {
			return this.min;
		}
		
		public long max() {
			return this.max;
		}
		
		@Override
		public java.lang.String check(Long value) {
			if (this.isNullable() == false && value == null) {
				return java.lang.String.format("field %s is not nullable, but value is null", this.name());
			}
			
			if (value != null) {
				if (value > max || value < min) {
					return java.lang.String.format("field %s must in [%d, %d], but value is %d", this.min, this.max, value.longValue());
				}
			}
			
			return Check.ok;
		}
	}
	
	public static class Decimal extends Field<Double> {
		private double min = 0;
		private double max = 100000000;
		
		public Decimal(java.lang.String name) {
			super(name);
		}
		
		public Decimal min(double min) {
			this.min = min;
			return this;
		}
		
		public Decimal max(double max) {
			this.max = max;
			return this;
		}
		
		public double min() {
			return this.min;
		}
		
		public double max() {
			return this.max;
		}

		@Override
		public java.lang.String check(Double t) {
			return null;
		}
	}
	
	public static class String extends Field<java.lang.String> {
		private int maxLength = 255;
		
		public String(java.lang.String name) {
			super(name);
		}
		
		public String maxLength(int max) {
			this.maxLength = max;
			return this;
		}
		
		public int maxLength() {
			return this.maxLength;
		}

		@Override
		public java.lang.String check(java.lang.String t) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class Binary extends Field<byte[]> {
		private int maxSize = (int) Math.pow(2, 20);
		
		public Binary(java.lang.String name) {
			super(name);
		}
		
		public Binary maxSize(int max) {
			this.maxSize = max;
			return this;
		}
		
		public int maxSize() {
			return this.maxSize;
		}

		@Override
		public java.lang.String check(byte[] t) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class Date extends Field<java.util.Date> {
		public Date(java.lang.String name) {
			super(name);
		}

		@Override
		public java.lang.String check(java.util.Date t) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class Time extends Field<java.util.Date> {
		public Time(java.lang.String name) {
			super(name);
		}

		@Override
		public java.lang.String check(java.util.Date t) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class Datetime extends Field<java.util.Date> {
		public Datetime(java.lang.String name) {
			super(name);
		}

		@Override
		public java.lang.String check(java.util.Date t) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}