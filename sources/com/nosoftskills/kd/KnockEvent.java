package com.nosoftskills.kd;

public class KnockEvent {
    public float correction;
    public float rpm;
    public float speed;
    public long timestamp;
    public float timing;

    public String toString() {
        return "KnockEvent [timestamp=" + this.timestamp + ", rpm=" + this.rpm + ", timing=" + this.timing + ", correction=" + this.correction + "]";
    }

    public int hashCode() {
        return ((((((Float.floatToIntBits(this.correction) + 31) * 31) + Float.floatToIntBits(this.rpm)) * 31) + ((int) (this.timestamp ^ (this.timestamp >>> 32)))) * 31) + Float.floatToIntBits(this.timing);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        KnockEvent other = (KnockEvent) obj;
        if (Float.floatToIntBits(this.correction) != Float.floatToIntBits(other.correction)) {
            return false;
        }
        if (Float.floatToIntBits(this.rpm) != Float.floatToIntBits(other.rpm)) {
            return false;
        }
        if (this.timestamp != other.timestamp) {
            return false;
        }
        if (Float.floatToIntBits(this.timing) != Float.floatToIntBits(other.timing)) {
            return false;
        }
        return true;
    }
}
