package com.evilkissyou.energycontractapp.serializer;

import com.evilkissyou.energycontractapp.entity.Contract;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Contract serializer class for better readability when serializing Contract
 */

public class ContractSerializer extends StdSerializer<Contract> {

    public ContractSerializer() {
        this(null);
    }

    public ContractSerializer(Class<Contract> contract) {
        super(contract);
    }

    @Override
    public void serialize(Contract contract, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", contract.getId());
        jsonGenerator.writeStringField("contractType", contract.getContractType().getName());
        jsonGenerator.writeStringField("createdOn", contract.getCreatedOn().toString());
        jsonGenerator.writeNumberField("userId", contract.getUser().getId());
        jsonGenerator.writeStringField("userName", contract.getUser().getName());

        jsonGenerator.writeEndObject();
    }
}
