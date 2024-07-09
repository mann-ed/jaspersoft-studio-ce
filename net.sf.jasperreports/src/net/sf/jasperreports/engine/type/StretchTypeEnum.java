package net.sf.jasperreports.engine.type;

public enum StretchTypeEnum implements JREnum {
  NO_STRETCH((byte)0, "NoStretch"),
  //RELATIVE_TO_TALLEST_OBJECT((byte)1, "RelativeToTallestObject"),
  //RELATIVE_TO_BAND_HEIGHT((byte)2, "RelativeToBandHeight"),
  ELEMENT_GROUP_HEIGHT((byte)3, "ElementGroupHeight"),
  ELEMENT_GROUP_BOTTOM((byte)3, "ElementGroupBottom"),
  CONTAINER_HEIGHT((byte)3, "ContainerHeight"),
  CONTAINER_BOTTOM((byte)4, "ContainerBottom");
  
  public static final String PROPERTY_LEGACY_ELEMENT_STRETCH_ENABLED = "net.sf.jasperreports.legacy.element.stretch.enabled";
  
  private final transient byte value;
  
  private final transient String name;
  
  StretchTypeEnum(byte value, String name) {
    this.value = value;
    this.name = name;
  }
  
  public Byte getValueByte() {
    return Byte.valueOf(this.value);
  }
  
  public final byte getValue() {
    return this.value;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static StretchTypeEnum getByName(String name) {
    return EnumUtil.<StretchTypeEnum>getEnumByName(values(), name);
  }
  
  public static StretchTypeEnum getByValue(Byte value) {
    return (StretchTypeEnum)EnumUtil.getByValue((JREnum[])values(), value);
  }
  
  public static StretchTypeEnum getByValue(byte value) {
    return getByValue(Byte.valueOf(value));
  }
}
