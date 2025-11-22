package com.example.demo.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.demo.models.entity.ExtintorPrediccion;
import com.example.demo.models.entity.PrediccionResultado;

import org.json.JSONObject;
import org.json.JSONArray;

import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

@Service
public class PrediccionExtintorService {

    private static final Logger logger = LoggerFactory.getLogger(PrediccionExtintorService.class);

    // ✅ CAMBIO 1: Usar @Value para cargar desde properties

    private String apiKey;

    @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String apiUrl;

    @Value("${groq.model:llama-3.1-70b-versatile}")
    private String model;

    private Classifier wekaModel;
    private Instances dataStructure;
    private final List<PrediccionResultado> prediccionesAlmacenadas = new ArrayList<>();

    public PrediccionExtintorService() {
        try {
            // ✅ Cargar modelo entrenado (.model)
            ClassPathResource resourceModel = new ClassPathResource("modeloextintores.model");
            wekaModel = (Classifier) weka.core.SerializationHelper.read(resourceModel.getInputStream());
            logger.info("✅ Modelo de predicción cargado correctamente.");

            // ✅ Cargar estructura de datos del .arff
            ClassPathResource resourceData = new ClassPathResource("extintores.arff");
            DataSource source = new DataSource(resourceData.getInputStream());
            dataStructure = source.getDataSet();

            if (dataStructure.classIndex() == -1) {
                dataStructure.setClassIndex(dataStructure.numAttributes() - 1);
            }

            logger.info("✅ Estructura de datos cargada correctamente. Clase: {}",
                    dataStructure.classAttribute().name());

        } catch (Exception e) {
            logger.error("❌ Error al inicializar el modelo o la estructura de datos: {}", e.getMessage(), e);
        }
    }

    public PrediccionResultado predictAndSave(ExtintorPrediccion extintorPrediccion) throws Exception {

        if (wekaModel == null || dataStructure == null) {
            throw new IllegalStateException("El modelo o la estructura de datos no se cargaron correctamente.");
        }

        if (extintorPrediccion == null) {
            throw new IllegalArgumentException("Los datos de entrada no pueden ser nulos.");
        }

        logger.info("📥 Datos recibidos para predicción: {}", extintorPrediccion);

        // ✅ Crear nueva instancia con el mismo número de atributos
        Instance instanceToPredict = new DenseInstance(dataStructure.numAttributes());
        instanceToPredict.setDataset(dataStructure);

        // ✅ Asignar valores en el orden exacto del .arff
        instanceToPredict.setValue(0, extintorPrediccion.getTipoServicio());
        instanceToPredict.setValue(1, extintorPrediccion.getTipoExtintor());
        instanceToPredict.setValue(2, extintorPrediccion.getCantidad());
        instanceToPredict.setValue(3, extintorPrediccion.getDiasHastaEntrega());
        instanceToPredict.setValue(4, extintorPrediccion.getDiaSemana());
        instanceToPredict.setValue(5, extintorPrediccion.getMes());
        instanceToPredict.setValue(6, extintorPrediccion.getRangoMonto());
        instanceToPredict.setValue(7, extintorPrediccion.getClienteFrecuente());
        instanceToPredict.setValue(8, extintorPrediccion.getStockDisponible());

        // ✅ Realizar la predicción
        double prediccionIndex = wekaModel.classifyInstance(instanceToPredict);
        String tipoErrorPredicho = dataStructure.classAttribute().value((int) prediccionIndex);

        // ✅ Obtener la probabilidad de cada clase
        double[] distribution = wekaModel.distributionForInstance(instanceToPredict);

        // ✅ Obtener consejo de IA
        logger.info("🤖 Solicitando consejo de IA para: {}", tipoErrorPredicho);
        String consejo = getAdviceFromGroqAI(tipoErrorPredicho, extintorPrediccion);

        // ✅ Crear resultado
        PrediccionResultado resultado = new PrediccionResultado(extintorPrediccion, tipoErrorPredicho, distribution,
                consejo);
        prediccionesAlmacenadas.add(resultado);

        logger.info("✅ Predicción completada correctamente: {}", tipoErrorPredicho);
        return resultado;
    }

    public List<PrediccionResultado> getPredicciones() {
        return prediccionesAlmacenadas;
    }

    private String getAdviceFromGroqAI(String tipoError, ExtintorPrediccion extintor) {
        try {
            // Crear cliente HTTP con timeout
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    .build();

            // Construir prompt detallado
            String prompt = construirPromptMejorado(tipoError, extintor);

            logger.info("📤 Enviando prompt a Groq AI...");
            logger.debug("Prompt: {}", prompt);

            // Construir body del request
            JSONObject requestBody = new JSONObject();
            JSONArray messages = new JSONArray();

            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content",
                    "Eres un experto técnico en sistemas de prevención de incendios y manejo de extintores. Proporciona consejos prácticos y específicos.");
            messages.put(systemMessage);

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.put(userMessage);

            // ✅ CAMBIO 3: API Key correcta
            requestBody.put("model", model); // llama-3.1-70b-versatile o similar
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);

            RequestBody body = RequestBody.create(
                    requestBody.toString(),
                    MediaType.parse("application/json; charset=utf-8"));

            // ✅ CAMBIO 4: Autorización correcta
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey.replace("Bearer ", "")) // Limpia Bearer si existe
                    .post(body)
                    .build();

            logger.info("🔗 URL: {}", apiUrl);
            logger.info("🔑 API Key (primeros 10 chars): {}...", apiKey.substring(0, Math.min(10, apiKey.length())));

            // Ejecutar request
            Response response = client.newCall(request).execute();

            logger.info("📥 Response Code: {}", response.code());

            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                logger.debug("Response Body: {}", responseBody);

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray choices = jsonObject.getJSONArray("choices");

                if (choices.length() > 0) {
                    JSONObject choice = choices.getJSONObject(0);
                    JSONObject message = choice.getJSONObject("message");
                    String content = message.getString("content");

                    logger.info("✅ Consejo de IA obtenido exitosamente");
                    return content;
                } else {
                    logger.error("❌ No hay choices en la respuesta");
                    return "No se pudo obtener una recomendación (respuesta vacía).";
                }

            } else {
                String errorBody = response.body() != null ? response.body().string() : "Sin cuerpo de respuesta";
                logger.error("❌ Error HTTP {}: {}", response.code(), response.message());
                logger.error("❌ Error Body: {}", errorBody);

                return String.format("Error al conectar con IA (HTTP %d): %s",
                        response.code(), response.message());
            }

        } catch (IOException e) {
            logger.error("❌ Error de conexión con Groq AI: {}", e.getMessage(), e);
            return "Error de conexión con el servicio de IA: " + e.getMessage();

        } catch (Exception e) {
            logger.error("❌ Error inesperado al obtener recomendación: {}", e.getMessage(), e);
            return "Error al procesar la recomendación: " + e.getMessage();
        }
    }

    /**
     * Construye un prompt detallado para la IA
     */
    private String construirPromptMejorado(String tipoError, ExtintorPrediccion extintor) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("ANÁLISIS DE ERROR EN SERVICIO DE EXTINTORES\n\n");
        prompt.append("ERROR PREDICHO: ").append(tipoError).append("\n\n");
        prompt.append("CONTEXTO DEL SERVICIO:\n");
        prompt.append("- Tipo de servicio: ").append(extintor.getTipoServicio()).append("\n");
        prompt.append("- Tipo de extintor: ").append(extintor.getTipoExtintor()).append("\n");
        prompt.append("- Cantidad: ").append(extintor.getCantidad()).append("\n");
        prompt.append("- Días hasta entrega: ").append(extintor.getDiasHastaEntrega()).append("\n");
        prompt.append("- Cliente frecuente: ").append(extintor.getClienteFrecuente()).append("\n");
        prompt.append("- Stock disponible: ").append(extintor.getStockDisponible()).append("\n\n");

        prompt.append("Por favor proporciona:\n");
        prompt.append("1. CAUSA PROBABLE del error\n");
        prompt.append("2. SOLUCIÓN INMEDIATA recomendada\n");
        prompt.append("3. ACCIONES PREVENTIVAS para evitar este error en el futuro\n");
        prompt.append("4. RECOMENDACIONES ADICIONALES específicas para este caso\n\n");
        prompt.append("Sé conciso y práctico. Máximo 300 palabras.");

        return prompt.toString();
    }

    /**
     * Método de prueba para verificar la conexión con Groq
     */
    public String testGroqConnection() {
        try {
            logger.info("🧪 Probando conexión con Groq AI...");

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                    .build();

            JSONObject requestBody = new JSONObject();
            JSONArray messages = new JSONArray();

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", "Di 'Conexión exitosa' si puedes leer este mensaje.");
            messages.put(userMessage);

            requestBody.put("model", model);
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 50);

            RequestBody body = RequestBody.create(
                    requestBody.toString(),
                    MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey.replace("Bearer ", ""))
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray choices = jsonObject.getJSONArray("choices");
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                String content = message.getString("content");

                logger.info("✅ Test exitoso. Respuesta: {}", content);
                return "✅ Conexión exitosa: " + content;
            } else {
                String error = response.body() != null ? response.body().string() : "Sin body";
                logger.error("❌ Test falló. Code: {}, Error: {}", response.code(), error);
                return "❌ Error en conexión: " + response.code() + " - " + error;
            }

        } catch (Exception e) {
            logger.error("❌ Error en test: {}", e.getMessage(), e);
            return "❌ Error: " + e.getMessage();
        }
    }

    /**
     * Información del servicio
     */
    public String getServiceInfo() {
        return String.format(
                "Servicio de Predicción de Extintores\n" +
                        "- API URL: %s\n" +
                        "- Modelo IA: %s\n" +
                        "- API Key configurada: %s\n" +
                        "- Modelo Weka cargado: %s\n" +
                        "- Predicciones almacenadas: %d",
                apiUrl,
                model,
                apiKey != null && !apiKey.isEmpty() ? "Sí" : "No",
                wekaModel != null ? "Sí" : "No",
                prediccionesAlmacenadas.size());
    }
}