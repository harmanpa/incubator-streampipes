package org.streampipes.wrapper.flink.samples.axoom;

import org.streampipes.wrapper.flink.AbstractFlinkAgentDeclarer;
import org.streampipes.wrapper.flink.FlinkDeploymentConfig;
import org.streampipes.wrapper.flink.FlinkSepaRuntime;
import org.streampipes.wrapper.flink.samples.Config;
import org.streampipes.model.impl.EpaType;
import org.streampipes.model.impl.graph.SepaDescription;
import org.streampipes.model.impl.graph.SepaInvocation;
import org.streampipes.model.vocabulary.SO;
import org.streampipes.sdk.builder.ProcessingElementBuilder;
import org.streampipes.sdk.helpers.EpProperties;
import org.streampipes.sdk.helpers.EpRequirements;
import org.streampipes.sdk.helpers.OutputStrategies;
import org.streampipes.sdk.helpers.SupportedFormats;
import org.streampipes.sdk.helpers.SupportedProtocols;

/**
 * Created by riemer on 12.04.2017.
 */
public class MaintenancePredictionController extends AbstractFlinkAgentDeclarer<MaintenancePredictionParameters> {

  @Override
  public SepaDescription declareModel() {
    return ProcessingElementBuilder.create("maintenance-prediction", "Coffee Maintenance " +
            "Prediction (Rule-based)", "Predicts the next maintenance based on coffee orders")
            .category(EpaType.ALGORITHM)
            .iconUrl(Config.getIconUrl("prediction-icon"))
            .requiredPropertyStream1(EpRequirements.anyProperty())
            .requiredPropertyStream2(EpRequirements.anyProperty())
            .outputStrategy(OutputStrategies.fixed(EpProperties.longEp("timestamp", SO.DateTime)
                    , EpProperties.stringEp("machineId", "http://axoom.com/machineId"),
                    EpProperties.longEp("predictedMaintenanceTime", SO.DateTime)))
            .supportedFormats(SupportedFormats.jsonFormat())
            .supportedProtocols(SupportedProtocols.kafka())
            .build();
  }

  @Override
  protected FlinkSepaRuntime<MaintenancePredictionParameters> getRuntime(SepaInvocation graph) {
    MaintenancePredictionParameters params = new MaintenancePredictionParameters(graph);

    return new MaintenancePredictionProgram(params, new FlinkDeploymentConfig(Config.JAR_FILE,
            Config.FLINK_HOST, Config.FLINK_PORT));

    //return new MaintenancePredictionProgram(params);
  }
}