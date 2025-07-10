package com.sergio;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/labseq")
public class Rest {

    private static ConcurrentHashMap<Integer, BigInteger> cache = new ConcurrentHashMap<>();

    public BigInteger labseq(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n deve ser >= 0");
        }

        cache.put(0, BigInteger.ZERO);
        cache.put(1, BigInteger.ONE);
        cache.put(2, BigInteger.ZERO);
        cache.put(3, BigInteger.ONE);

        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        int start = 0;
        if (!cache.isEmpty()) {
            start = cache.keySet().stream().max(Integer::compare).orElse(0);
        }

        for (int i = start + 1; i <= n; i++) {
            if (cache.containsKey(i)) {
                continue;
            }

            BigInteger val_4 = cache.get(i - 4);
            BigInteger val_3 = cache.get(i - 3);

            BigInteger currentResult = val_4.add(val_3);
            cache.put(i, currentResult);
        }

        return cache.get(n);
    }

    @GET
    @Path("/{n}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLabseq(@PathParam("n") int n) {
        if (n < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"n deve ser um inteiro não-negativo\"}")
                    .build();
        }

        System.out.println("Teste= " + labseq(n).toString());
        return Response.ok(labseq(n).toString()).build();
    }
}
