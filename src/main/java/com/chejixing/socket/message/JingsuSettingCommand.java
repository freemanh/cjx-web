package com.chejixing.socket.message;

public class JingsuSettingCommand {
	private JingsuSettingType type;
	private Short value;

	public JingsuSettingCommand(JingsuSettingType type, Short value) {
		super();
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return "JingsuSettingCommand [type=" + type + ", value=" + value + "]";
	}

	public JingsuSettingType getType() {
		return type;
	}

	public void setType(JingsuSettingType type) {
		this.type = type;
	}

	public Short getValue() {
		return value;
	}

	public void setValue(Short value) {
		this.value = value;
	}

}
