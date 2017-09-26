package com.pango.comunicaciones;

import com.pango.comunicaciones.model.TicketModel;

/**
 * Created by NILRAD on 25/09/2017.
 */

public class Utils {

    public static String baseUrl = "https://app.antapaccay.com.pe/proportal/scom_service/api";
    public static String loginDominio = "anyaccess";
    public static String token = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNyc2Etc2hhMjU2IiwidHlwIjoiSldUIn0.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYW50YXBhY2NheSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMDAwMDA3OTU4MCIsImV4cCI6MTUwNjQ2NzM2NiwiaXNzIjoiaHR0cDovL2lzc3Vlci5jb20iLCJhdWQiOiJodHRwOi8vbXlzaXRlLmNvbSJ9.B4fECcgYCAKEUNkrk6JeDV6pWGjOx-E4snHsF3k2UyLMvFKzGlrf9rM6zJQgSoIz_qInWdoaaFgk2uwxg3r_8KSsZoC8f1-7mqKpthtmxcCWyTUy4ZDMxfdaozPq-qpF6ezhz-WxXQ3X_0Fhgh47VBCNkmK_n2GnEirJzRUfUWZXhMcza8SiSoL4KIMUMqLq7SOEDb-ysldFV6vdvcJw58rz5u52x2E4TZFlyAUcA_CVXUTSCUcA-NDtJIlXo_izQb4c1VqErNcBGBRkvqwGHQqWJoBPeZTwmY51Tnljm7DG1cpW9vRuUOMQmH9GVhIJAlANyYPTvhuUoYIiCRdo8g";
    public static Boolean esAdmin = false;

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
}
