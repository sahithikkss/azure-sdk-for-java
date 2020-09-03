package com.azure.digitaltwins.core;

import com.azure.core.http.HttpClient;
import com.azure.core.http.rest.PagedIterable;
import com.azure.digitaltwins.core.models.ModelData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.azure.digitaltwins.core.TestHelper.DISPLAY_NAME_WITH_ARGUMENTS;
import static com.azure.digitaltwins.core.TestHelper.assertRestException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Sync client implementation of the model tests defined in {@link ModelsTestBase}
 */
public class ModelsTest extends ModelsTestBase {

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void modelLifecycleTest(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsClient client = getClient(httpClient, serviceVersion);

        // Create some models to test the lifecycle of
        final List<ModelData> createdModels = new ArrayList<>();
        createModelsRunner(client, (modelsList) -> {
            PagedIterable<ModelData> createdModelsPagedIterable = client.createModels(modelsList);
            AtomicInteger modelIndex = new AtomicInteger();
            createdModelsPagedIterable.forEach((modelData) -> {
                // When creating models, the service will not return the model itself, so it will be manually
                // assigned here and below to make comparisons easier
                modelData.setModel(modelsList.get(modelIndex.get()));
                modelIndex.getAndIncrement();

                createdModels.add(modelData);
            });
        });

        for (int modelIndex = 0; modelIndex < createdModels.size(); modelIndex++) {
            final ModelData expected = createdModels.get(modelIndex);

            // Get the model
            getModelRunner(expected.getId(), (modelId) -> {
                ModelData actual = client.getModel(modelId);
                assertModelDataAreEqual(expected, actual);
            });

            // Decommission the model
            decommissionModelRunner(expected.getId(), (modelId) -> {
                client.decommissionModel(modelId);
            });

            // Get the model again to see if it was decommissioned as expected
            getModelRunner(expected.getId(), (modelId) -> {
                ModelData actual = client.getModel(modelId);
                assertTrue(actual.isDecommissioned());
            });

            // Delete the model
            deleteModelRunner(expected.getId(), (modelId) -> {
                client.deleteModel(modelId);
            });
        }
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void getModelThrowsIfModelDoesNotExist(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsClient client = getClient(httpClient, serviceVersion);
        final String nonExistantModelId = "urn:doesnotexist:fakemodel:1000";
        getModelRunner(nonExistantModelId, (modelId) -> {
            assertRestException(() -> client.getModel(modelId), HttpURLConnection.HTTP_NOT_FOUND);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void createModelThrowsIfModelAlreadyExists(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsClient client = getClient(httpClient, serviceVersion);

        final List<String> modelsToCreate = new ArrayList<>();
        final String wardModelId = TestAssetsHelper.getUniqueModelId(client, TestAssetDefaults.wardModelId);
        final String wardModelPayload = TestAssetsHelper.getWardModelPayload(wardModelId);
        modelsToCreate.add(wardModelPayload);

        PagedIterable<ModelData> createdModels = client.createModels(modelsToCreate);
        createdModels.forEach((modelData) -> {
            assertNotNull(modelData);
        });

        assertRestException(
            () -> client.createModels(modelsToCreate).forEach((modelData) -> {
            // Don't need to do anything here. Only calling .forEach to force the client to attempt to return the first
            // created model. That should trigger the call to throw a conflict exception
            }),
            HttpURLConnection.HTTP_CONFLICT);
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.digitaltwins.core.TestHelper#getTestParameters")
    @Override
    public void getModelThrowsIfModelIdInvalid(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion) {
        DigitalTwinsClient client = getClient(httpClient, serviceVersion);
        final String malformedModelId = "thisIsNotAValidModelId";
        getModelRunner(malformedModelId, (modelId) -> {
            assertRestException(() -> client.getModel(modelId), HttpURLConnection.HTTP_BAD_REQUEST);
        });
    }

    private static void createModelsRunner(DigitalTwinsClient client, Consumer<List<String>> createModelsTestRunner) {
        String buildingModelId = TestAssetsHelper.getUniqueModelId(client, TestAssetDefaults.buildingModelId);
        String floorModelId = TestAssetsHelper.getUniqueModelId(client, TestAssetDefaults.floorModelId);
        String hvacModelId = TestAssetsHelper.getUniqueModelId(client, TestAssetDefaults.hvacModelId);
        String wardModelId = TestAssetsHelper.getUniqueModelId(client, TestAssetDefaults.wardModelId);

        createModelsRunner(buildingModelId, floorModelId, hvacModelId, wardModelId, createModelsTestRunner);
    }

    private DigitalTwinsClient getClient(HttpClient httpClient, DigitalTwinsServiceVersion serviceVersion)
    {
        return getDigitalTwinsClientBuilder().serviceVersion(serviceVersion).httpClient(httpClient).buildClient();
    }
}
