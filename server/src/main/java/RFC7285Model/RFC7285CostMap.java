package RFC7285Model;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cost Map: defined in RFC7285 secion 11.2.3
 * */
public class RFC7285CostMap {

    public static class Meta extends Extensible {

        @JsonProperty("dependent-vtags")
        public List<RFC7285VersionTag> netmap_tags = new LinkedList<RFC7285VersionTag>();

        @JsonProperty("cost-type")
        public RFC7285CostType costType;
    }

    /**
     * for filtered-cost-map service, RFC7285 secion 11.3.2
     * */
    public static class Filter {

        @JsonProperty("cost-type")
        public RFC7285CostType costType;

        @JsonProperty("pids")
        public RFC7285QueryPairs pids;
    }

    @JsonProperty("meta")
    public Meta meta = new Meta();

    @JsonProperty("cost-map")
    public Map<String, Map<String, Object>> map
                        = new LinkedHashMap<String, Map<String, Object>>();

}
