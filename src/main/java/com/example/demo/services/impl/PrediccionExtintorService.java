package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.demo.models.entity.ExtintorPrediccion;
import com.example.demo.models.entity.PrediccionResultado;

import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

@Service
public class PrediccionExtintorService {

    private static final Logger logger = LoggerFactory.getLogger(PrediccionExtintorService.class);

    private Classifier model;
    private Instances dataStructure;
    private final List<PrediccionResultado> prediccionesAlmacenadas = new ArrayList<>();

    public PrediccionExtintorService() {
        try {
            // ✅ Cargar modelo entrenado (.model)
            ClassPathResource resourceModel = new ClassPathResource("modeloextintores.model");
            model = (Classifier) weka.core.SerializationHelper.read(resourceModel.getInputStream());
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
        if (model == null || dataStructure == null) {
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
        double prediccionIndex = model.classifyInstance(instanceToPredict);
        String tipoErrorPredicho = dataStructure.classAttribute().value((int) prediccionIndex);

        // ✅ Obtener la probabilidad de cada clase
        double[] distribution = model.distributionForInstance(instanceToPredict);

        // ✅ Crear resultado
        PrediccionResultado resultado = new PrediccionResultado(extintorPrediccion, tipoErrorPredicho, distribution);
        prediccionesAlmacenadas.add(resultado);

        logger.info("✅ Predicción completada correctamente: {}", tipoErrorPredicho);
        return resultado;
    }

    public List<PrediccionResultado> getPredicciones() {
        return prediccionesAlmacenadas;
    }
}
