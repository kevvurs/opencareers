package com.boomerang.careers.binding;

import com.boomerang.careers.data.JobBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.Response;

public abstract class CareerPacket {
    protected static final Gson GSON = new GsonBuilder().serializeNulls().create();

    public abstract JobBean peek();

    public abstract void push(JobBean jobBean);

    public abstract String serialize();

    public abstract CareerPacket deserialize(String str);

    public abstract Response wrap(Response.Status status);

    protected static class ResponseFactory {
        protected static Response create(Response.Status status, CareerPacket packet, String type) {
            return create(status, packet.serialize(), type);
        }

        protected static Response create(Response.Status status, String json, String type) {
            return Response
                    .status(status)
                    .entity(json)
                    .type(type)
                    .build();
        }
    }
}
