package RFC7285Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RFC7285VersionTag {

    @JsonProperty("resource-id")
    public String rid;

    @JsonProperty("tag")
    public String tag;

    public RFC7285VersionTag() {
        rid = "";
        tag = "";
    }

    public RFC7285VersionTag(String rid, String tag) {
        this.rid = (rid != null ? rid : "");
        this.tag = (tag != null ? tag : "");
    }

    public boolean incomplete() {
        return (rid == null) || (tag == null) || (rid == "") || (tag == "");
    }

    private static char ILLEGAL = '$';

    @Override
    public int hashCode() {
        return (rid + ILLEGAL + tag).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        RFC7285VersionTag other = (RFC7285VersionTag)obj;
        boolean _rid = (rid == null ? (other.rid == null) : rid.equals(other.rid));
        boolean _tag = (tag == null ? (other.tag == null) : tag.equals(other.tag));
        return (_rid && _tag);
    }
}
