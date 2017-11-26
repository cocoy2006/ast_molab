package molab.main.java.util;

public class Status {
	
	public static final int DEFAULT_ASSETS = 9000;

	public static enum Common {
		ERROR(0), SUCCESS(1), FALSE(0), TRUE(1), ASSETS_NOT_ENOUGH(2);
		private int value;

		private Common(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}

	public static enum UserRole {
		ADMIN(0), USER(1);
		private int value;

		private UserRole(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum UserStatus {
		UNACTIVED(0), NORMAL(1), NOT_EXIST(2), EXIST(3), PASSWORD_NOT_MATCH(4);
		private int value;

		private UserStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}

	public static enum RunningStatus {
		START(0), RUNNING(1), END(2), EXCEPTION(9);
		private int value;

		private RunningStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum ActionType {
		SILENT(0), ACTIVE(1), RETENTION(2);
		private int value;

		private ActionType(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum RetentionDays {
		DAY(1), WEEK(7), MONTH(30), SILENT(99);
		private int value;

		private RetentionDays(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}

}