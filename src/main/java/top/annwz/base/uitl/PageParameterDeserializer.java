package top.annwz.base.uitl;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by xuchun on 16/4/8.
 */

public class PageParameterDeserializer extends JsonDeserializer<PageParameter> {
    @Override
    public PageParameter deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        String str = mapper.readTree(jp).toString();
        PageParameter page = null;
        try {
            page = new PageParameter(str, null, null);
        } catch (Exception e) {
            throw new IOException("PageParameter conver error");
        }
        return page;
    }
}
