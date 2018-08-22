/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.connect;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.streampipes.connect.adapter.generic.format.json.object.JsonObjectFormat;
import org.streampipes.connect.adapter.generic.protocol.stream.KafkaProtocol;
import org.streampipes.model.connect.adapter.GenericAdapterStreamDescription;
import org.streampipes.model.connect.grounding.FormatDescription;
import org.streampipes.model.connect.grounding.ProtocolDescription;
import org.streampipes.model.connect.grounding.ProtocolStreamDescription;
import org.streampipes.model.staticproperty.FreeTextStaticProperty;
import org.streampipes.model.staticproperty.StaticProperty;
import org.streampipes.rest.shared.util.JsonLdUtils;

import java.io.IOException;

public class TestMain {

    public static void main(String... args) throws IOException {
        ProtocolDescription protocolDescription = new KafkaProtocol().declareModel();


        // Set broker URL
        for (StaticProperty property: protocolDescription.getConfig()) {
            if (property.getInternalName().equals("broker_url")) {
                ((FreeTextStaticProperty)property).setValue("ipe-koi04.fzi.de:9092");
            }

            if (property.getInternalName().equals("topic")) {
                ((FreeTextStaticProperty)property).setValue("org.streampipes.examples.waterlevel");
            }
        }

        // Set topic

        FormatDescription formatDescription = new JsonObjectFormat().declareModel();

        GenericAdapterStreamDescription genericSetDescription = new GenericAdapterStreamDescription();

        genericSetDescription.setProtocolDescription(protocolDescription);
        genericSetDescription.setFormatDescription(formatDescription);

        String jsonld = JsonLdUtils.toJsonLD(genericSetDescription);


//        String s = Request.Post("http://localhost:8099/api/v1/riemer@fzi.de/master/adapters/")
////                .setHeader("content-type", "applicaito")
//                .bodyString(jsonld, ContentType("application/ld+json", Consts.UTF_8))
//                .connectTimeout(1000)
//                .socketTimeout(100000)
//                .execute().returnContent().asString();

        HttpPost post = new HttpPost("http://localhost:8099/api/v1/riemer@fzi.de/master/adapters/");
        Header headers[] = {
                new BasicHeader("Content-type", "application/ld+json"),
        };
        post.setHeaders(headers);
        post.setEntity(new StringEntity(jsonld));

        HttpClient client = HttpClients.custom().build();
        HttpResponse response = client.execute(post);

        System.out.println(jsonld);
    }
}