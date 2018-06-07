package cn.ffast.core.redis;

public enum RedisSerializerType {
    FastJSON(0, "FastJSON"),
    JackJson(1, "JackJson"),
    Msgpack(2, "Msgpack");
    private String name;
    private int type;

    RedisSerializerType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
