package com.facturia.security.commons;

public class Constants {

    public static final String CODE_RESPONSE_SUCCESS = "001";
    public static final String MESSAJE_RESPONSE_SAVE_SUCCESS = "Registro Exitoso";
    public static final String MESSAJE_RESPONSE_UPDATE_SUCCESS = "Modificacion Exitosa";
    public static final String MESSAJE_RESPONSE_DELETE_SUCCESS = "Eliminacion Exitosa";

    public static final String CODE_RESPONSE_VALIDATION_BRANCH = "002";
    public static final String MESSAJE_RESPONSE_VALIDATION_BRANCH = "No puede eliminar mas sucursales ya que" +
            " se necesita al menos una sucursal para poder continuar con las demás transacciones";

    public static final String CODE_RESPONSE_NOT_FOUND = "003";
    public static final String MESSAJE_RESPONSE = "No se puso registrar/modificar y/o eliminar en la bd";
    public static final String MESSAJE_RESPONSE_ERROR = "Error de negocio";

    public static final String OPEN_CORCHETE = "[";
    public static final String CLOSE_CORCHETE = "]";

    // Headers
    public static final String TRANSACTION_ID = "Transaccion-Id";
    public static final String APLICATION_ID = "Aplicacion-Id";
    public static final String NAME_APLICACION = "Nombre-Aplicacion";
    public static final String USER_CONSUMER_ID = "Usuario-Consumidor-Id";

    public static final String MESSAGE_FORMAT_INVALID = "No cumple con el formato requerido";

    // Errores Tecnicos, Errores Funcionales
    public static final String FUNCTIONAL_ERROR = "FUNCIONAL";
    public static final String TECHNICAL_ERROR = "TECNICO";

    public static final String CODE_BAD_REQUEST_ERROR = "FN-001";
    public static final String MESSAJE_RESPONSE_BAD_REQUEST = "ERROR DE VALIDACION DE INPUTS: ";

    // Errores de base de datos
    public static final String MESSAGE_RESPONSE_ERROR_DATABASE = "ERROR DE BASE DE DATOS: ";

    public static final String CODE_FUNCTIONAL_ERROR_DATABASE = "FN-002";
    public static final String MESSAGE_FUNCTIONAL_DATABASE = "Error relacionado con la base de datos.";

    public static final String CODE_TECHNICAL_ERROR_ACCESS_DATABASE = "ST-001";
    public static final String MESSAGE_TECHNICAL_ERROR_ACCESS_DATABASE = "Error de acceso a datos. Inténtelo más tarde, o comuniquese con el administrador.";
    // Errores generales
    public static final String CODE_TECHNICAL_ERROR_GENERAL = "ST-002";
    public static final String MESSAGE_RESPONSE_ERROR_GENERAL = "ERROR EN EL MICROSERVICIO";

    public static final int ONE_BRANCH = 1;

}
