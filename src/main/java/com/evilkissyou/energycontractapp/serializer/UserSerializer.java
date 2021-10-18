package com.evilkissyou.energycontractapp.serializer;

import com.evilkissyou.energycontractapp.entity.Contract;
import com.evilkissyou.energycontractapp.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Set;

/**
 * User serializer class for better readability when serializing User
 */

public class UserSerializer extends StdSerializer<User> {
    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> user) {
        super(user);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        // Serialize user info
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("userName", user.getName());
        jsonGenerator.writeStringField("userType", user.getUserType().getName());

        // Serialize all user contracts info in the array
        Set<Contract> contracts = user.getContracts();
        jsonGenerator.writeFieldName("contracts");
        jsonGenerator.writeStartArray();
        for (Contract contract : contracts) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("contractId", contract.getId());
            jsonGenerator.writeStringField("contractType", contract.getContractType().getName());
            jsonGenerator.writeStringField("createdOn", contract.getCreatedOn().toString());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
