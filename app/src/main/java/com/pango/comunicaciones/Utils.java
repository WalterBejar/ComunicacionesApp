package com.pango.comunicaciones;

import android.accessibilityservice.GestureDescription;

import com.pango.comunicaciones.model.PasajeroModel;
import com.pango.comunicaciones.model.PersonaPostReservaModel;
import com.pango.comunicaciones.model.TicketModel;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NILRAD on 25/09/2017.
 */

public class Utils {

    public static String baseUrl = "https://app.antapaccay.com.pe/proportal/scom_service/api";
    public static String loginDominio = "anyaccess";
    public static String token = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNyc2Etc2hhMjU2IiwidHlwIjoiSldUIn0.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYW50YXBhY2NheSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMDAwMDA3OTU4MCIsImV4cCI6MTUwNjYzMDUwMSwiaXNzIjoiaHR0cDovL2lzc3Vlci5jb20iLCJhdWQiOiJodHRwOi8vbXlzaXRlLmNvbSJ9.uxSZRiOv-6fkYKD8At_U7-b_ghEQLHlvW_zSaCDzzm1WwPyEDmbL-g6Z8Xns_5ZiaCTl3Pu39pbm3-RIplHuuujLwwLny92mIKDBLYd3kpJDUSOASQp4eUyaeeJMWnUFqyoPP9kBNEMMo9NB_VSrS4MWbl7KXdIZS_XKZoJ0qVvA2SXCgu8FSxXv3JYT5UKsWpd7eEFwkNu6f5z20ro1kK_8YAnrC4TYHlXtlTBAAsu4a0yvRtZzqo1EOJ8FqHUl4b-66HAWcPGue78QDfDfeaAxsrDAR0deiEAhMOCpB04Wfre9b6nvSCJDMIo2FtT6gW-hah12uwfRWOrgxULWVw";

    public static Boolean esAdmin = true;
    public static String nombres = "Quille Sucasaca, Alexander";
    public static String codPersona = "0000079580";

    static String getUrlForReservaTicketsLogin(String username, String password) {
        return baseUrl + "/membership/authenticate?username=" + username + "&password=" + password + "&domain=" + Utils.loginDominio;
    }

    static String getUrlForCheckIfAdmin() {
        return baseUrl + "/persona/Get_Usuario";
    }
    static String getUrlForReservaTicketTerminales() {
        return baseUrl + "/maestro/get_terminal";
    }

    public static String getUrlForBuscarTickets(String origenEscogido, String destinoEscogido, String fechaEscogida, int cantidadTickets) {
        return baseUrl + "/ticket/get_tickets/" + origenEscogido + "/" + destinoEscogido + "/" + fechaEscogida + "/1/" + cantidadTickets;
    }

    public static String getUrlForReservaTicketDetalle(String codigoTicket) {
        return baseUrl + "/ticket/get_ticket/" + codigoTicket;
    }

    public static String getTicketProperty(TicketModel ticket, String s) {
        switch (s){
            case "Nro Programa":
                return ticket.Codigo;
            case "Fecha":
                return ticket.Fecha.substring(0,10);
            case "Hora":
                return ticket.Fecha.substring(11,16);
            case "Origen":
                return ticket.Origen;
            case "Destino":
                return ticket.Destino;
            case "Reservas":
                return "" + ticket.Reservas;
            case "Patente":
                return ticket.Bus.Patente;
            case "Marca":
                return ticket.Bus.Marca;
            case "Modelo":
                return ticket.Bus.Modelo;
            case "Tipo Vehiculo":
                return ticket.Bus.Tipo;
            case "Asientos":
                return "" + ticket.Bus.Asientos;
            default:
                return "";
        }
    }

    public static PersonaPostReservaModel getPersonaPostReservaModel(String codTicket) {
        PersonaPostReservaModel persona = new PersonaPostReservaModel();
        persona.CodPersona = Utils.codPersona;
        persona.CodTicket = codTicket;
        persona.Dni = "";
        persona.Email = "";
        return persona;
    }

    public static String getUrlForAgregarReserva() {
        return baseUrl + "/persona/Reservar";
    }

    public static String getUrlForEliminarReserva() {
        return baseUrl + "/persona/DeleteReserva";
    }


    public static String getUrlForReservaTicketListaPasajeros(String codigoTicket) {
        return baseUrl + "/persona/get_pasajeros/" + codigoTicket;
    }

    public static String getUrlForReservaTicketBuscarPasajeros(String dni, String nombre, String empresa, int cantidad) {
        return baseUrl + "/persona/get_personas/" + dni + "/" + nombre + "/" + empresa + "/1/" + cantidad;
    }

    public static PersonaPostReservaModel fromPasajeroToPersona(PasajeroModel pasajero, String codigoTicket){
        PersonaPostReservaModel persona = new PersonaPostReservaModel();
        persona.CodPersona = pasajero.CodPersona;
        persona.CodTicket= codigoTicket;
        persona.Email = pasajero.Email;
        persona.Dni = pasajero.Dni;
        return persona;
    }

    public static String getFechaHoy() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-YYYY");
        return sd.format(date);
    }
}
