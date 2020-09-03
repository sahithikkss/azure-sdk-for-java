package com.azure.digitaltwins.core;

import com.azure.core.http.HttpClient;
import com.azure.digitaltwins.core.models.ModelData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.test.StepVerifier;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.azure.digitaltwins.core.TestHelper.DISPLAY_NAME_WITH_ARGUMENTS;
import static com.azure.digitaltwins.core.TestHelper.assertRestException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Async client implementation of the model tests defined in {@link ModelsTestBase}
 */
public class ModelsAsyncTest extends ModelsTestBase {
    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void modelLifecycleTest(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsAsyncClient asyncClient = getAsyncClient(httpClient, serviceVersion);

        // Create some models to test the lifecycle of
        List<ModelData> createdModels = new ArrayList<>();
        createModelsRunner(asyncClient, (modelsList) -> {
            StepVerifier.create(asyncClient.createModels(modelsList))
                .assertNext(createdModel -> {
                    createdModels.add(createdModel);
                })
                .assertNext(createdModel -> {
                    createdModels.add(createdModel);
                })
                .assertNext(createdModel -> {
                    createdModels.add(createdModel);
                })
                .verifyComplete();
        });

        for (int modelIndex = 0; modelIndex < createdModels.size(); modelIndex++) {
            final ModelData expected = createdModels.get(modelIndex);

            // Get the model
            getModelRunner(expected.getId(), (modelId) -> {
                StepVerifier.create(asyncClient.getModelWithResponse(modelId))
                    .assertNext(retrievedModel -> {
                        assertModelDataAreEqual(expected, retrievedModel.getValue(), false);
                    })
                    .verifyComplete();
            });

            // Decommission the model
            decommissionModelRunner(expected.getId(), (modelId) -> {
                StepVerifier.create(asyncClient.decommissionModel(modelId))
                    .verifyComplete();
            });

            // Get the model again to see if it was decommissioned as expected
            getModelRunner(expected.getId(), (modelId) -> {
                StepVerifier.create(asyncClient.getModel(modelId))
                    .assertNext(retrievedModel -> {
                        assertTrue(retrievedModel.isDecommissioned());
                    })
                    .verifyComplete();
            });

            // Delete the model
            deleteModelRunner(expected.getId(), (modelId) -> {
                StepVerifier.create(asyncClient.deleteModel(modelId))
                    .verifyComplete();
            });
        }
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void getModelThrowsIfModelDoesNotExist(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsAsyncClient asyncClient = getAsyncClient(httpClient, serviceVersion);
        final String nonExistantModelId = "urn:doesnotexist:fakemodel:1000";
        StepVerifier.create(asyncClient.getModel(nonExistantModelId))
            .verifyErrorSatisfies(ex -> assertRestException(ex, HttpURLConnection.HTTP_NOT_FOUND));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void createModelThrowsIfModelAlreadyExists(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsAsyncClient asyncClient = getAsyncClient(httpClient, serviceVersion);

        final List<String> modelsToCreate = new ArrayList<>();
        final String wardModelId = TestAssetsHelper.getUniqueModelId(asyncClient, TestAssetDefaults.wardModelId);
        final String wardModelPayload = TestAssetsHelper.getWardModelPayload(wardModelId);
        modelsToCreate.add(wardModelPayload);

        StepVerifier.create(asyncClient.createModels(modelsToCreate))
            .assertNext((modelData -> {
                assertNotNull(modelData);
            }))
            .verifyComplete();

        StepVerifier.create(asyncClient.createModels(modelsToCreate))
            .verifyErrorSatisfies(ex -> assertRestException(ex, HttpURLConnection.HTTP_CONFLICT));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void getModelThrowsIfModelIdInvalid(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsAsyncClient asyncClient = getAsyncClient(httpClient, serviceVersion);
        final String malformedModelId = "thisIsNotAValidModelId";
        StepVerifier.create(asyncClient.getModel(malformedModelId))
            .verifyErrorSatisfies(ex -> assertRestException(ex, HttpURLConnection.HTTP_BAD_REQUEST));
    }

    private static void createModelsRunner(DigitalTwinsAsyncClient asyncClient, Consumer<List<String>> createModelsTestRunner) {
        String buildingModelId = TestAssetsHelper.getUniqueModelId(asyncClient, TestAssetDefaults.buildingModelId);
        String floorModelId = TestAssetsHelper.getUniqueModelId(asyncClient, TestAssetDefaults.floorModelId);
        String hvacModelId = TestAssetsHelper.getUniqueModelId(asyncClient, TestAssetDefaults.hvacModelId);
        String wardModelId = TestAssetsHelper.getUniqueModelId(asyncClient, TestAssetDefaults.wardModelId);

        createModelsRunner(buildingModelId, floorModelId, hvacModelId, wardModelId, createModelsTestRunner);
    }

    private DigitalTwinsAsyncClient getAsyncClient(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        return getDigitalTwinsClientBuilder().serviceVersion(serviceVersion).httpClient(httpClient).buildAsyncClient();
    }
}
