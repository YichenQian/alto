package RFC7285Model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RFC7285JSONMapper {

    private ObjectMapper mapper = new ObjectMapper()
                            .setSerializationInclusion(Include.NON_DEFAULT)
                            .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

    public RFC7285Endpoint.AddressGroup asAddressGroup(String json) throws Exception {
        return mapper.readValue(json, RFC7285Endpoint.AddressGroup.class);
    }

    public RFC7285Endpoint.PropertyRequest asPropertyRequest(String json) throws Exception {
        return mapper.readValue(json, RFC7285Endpoint.PropertyRequest.class);
    }

    public RFC7285Endpoint.PropertyResponse asPropertyResponse(String json) throws Exception {
        return mapper.readValue(json, RFC7285Endpoint.PropertyResponse.class);
    }

    public RFC7285Endpoint.CostRequest asCostRequest(String json) throws Exception {
        return mapper.readValue(json, RFC7285Endpoint.CostRequest.class);
    }

    public RFC7285Endpoint.CostResponse asCostResponse(String json) throws Exception {
        return mapper.readValue(json, RFC7285Endpoint.CostResponse.class);
    }

    public RFC7285CostMap asCostMap(String json) throws Exception {
        return mapper.readValue(json, RFC7285CostMap.class);
    }

    public List<RFC7285CostMap> asCostMapList(String json) throws Exception {
      return Arrays.asList(mapper.readValue(json, RFC7285CostMap[].class));
    }

    public RFC7285CostType asCostType(String json) throws Exception {
        return mapper.readValue(json, RFC7285CostType.class);
    }

    public RFC7285Endpoint asEndpoint(String json) throws Exception {
        return mapper.readValue(json, RFC7285Endpoint.class);
    }

    public Extensible asExtensible(String json) throws Exception {
        return mapper.readValue(json, Extensible.class);
    }

    public RFC7285IRD asIRD(String json) throws Exception {
        return mapper.readValue(json, RFC7285IRD.class);
    }

    public RFC7285NetworkMap asNetworkMap(String json) throws Exception {
        return mapper.readValue(json, RFC7285NetworkMap.class);
    }

    public List<RFC7285NetworkMap> asNetworkMapList(String json) throws Exception {
      return Arrays.asList(mapper.readValue(json, RFC7285NetworkMap[].class));
    }

    public RFC7285NetworkMap.Filter asNetworkMapFilter(String json) throws Exception {
        return mapper.readValue(json, RFC7285NetworkMap.Filter.class);
    }

    public RFC7285CostMap.Filter asCostMapFilter(String json) throws Exception {
        return mapper.readValue(json, RFC7285CostMap.Filter.class);
    }

    public RFC7285VersionTag asVersionTag(String json) throws Exception {
        return mapper.readValue(json, RFC7285VersionTag.class);
    }

    public RFC7285EndpointPropertyMap asEndpointPropMap(String json) throws Exception {
      return mapper.readValue(json, RFC7285EndpointPropertyMap.class);
  }

    public String asJSON(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }
}
